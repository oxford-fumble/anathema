package net.sf.anathema.hero.attributes;

import net.sf.anathema.character.generic.impl.template.points.AttributeCreationPoints;
import net.sf.anathema.character.generic.impl.template.points.DefaultBonusPointCosts;
import net.sf.anathema.character.generic.template.points.IAttributeCreationPoints;
import net.sf.anathema.character.generic.traits.types.AttributeGroupType;
import net.sf.anathema.character.library.trait.Trait;
import net.sf.anathema.character.main.testing.dummy.DummyAdditionalBonusPointManagement;
import net.sf.anathema.character.main.testing.dummy.DummyHero;
import net.sf.anathema.character.main.testing.dummy.models.DummyHeroConcept;
import net.sf.anathema.character.main.testing.dummy.models.DummyOtherTraitModel;
import net.sf.anathema.character.main.testing.dummy.models.DummyTraitModel;
import net.sf.anathema.character.model.creation.bonus.attribute.AttributeCostCalculator;
import net.sf.anathema.character.model.creation.bonus.util.TraitGroupCost;
import net.sf.anathema.hero.attributes.model.AttributeModelImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static net.sf.anathema.character.generic.traits.types.AttributeGroupType.Mental;
import static net.sf.anathema.character.generic.traits.types.AttributeGroupType.Physical;
import static net.sf.anathema.character.generic.traits.types.AttributeGroupType.Social;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AttributeCostCalculatorTest {

  private static final int ATTRIBUTE_BONUS_POINT_COST = 4;

  private void assertEmptyPoints(AttributeGroupType groupType) {
    TraitGroupCost costs = calculator.getAttributePoints(groupType);
    assertEquals(0, costs.getBonusPointsSpent());
    assertEquals(0, costs.getDotsSpent());
  }

  private void assertFullyLearned(AttributeGroupType groupType, int allowedDotCount) {
    TraitGroupCost costs = calculator.getAttributePoints(groupType);
    assertEquals(0, costs.getBonusPointsSpent());
    assertEquals(allowedDotCount, costs.getDotsSpent());
    assertEquals(allowedDotCount, costs.getPointsToSpend());
  }

  private void assertOverlearned(AttributeGroupType groupType, int allowedDotCount, int bonusPoints) {
    TraitGroupCost costs = calculator.getAttributePoints(groupType);
    assertEquals(allowedDotCount, costs.getDotsSpent());
    assertEquals(allowedDotCount, costs.getPointsToSpend());
    assertEquals(bonusPoints, costs.getBonusPointsSpent());
  }

  public void spendPoints(AttributeGroupType groupType, int pointsToSpent) {
    Trait[] groupAttributes = attributeModel.getAll(groupType);
    int perAttribute = (int) Math.ceil((double) pointsToSpent / groupAttributes.length);
    int remainingPointsToSpent = pointsToSpent;
    for (Trait attribute : groupAttributes) {
      int toSpent = Math.min(perAttribute, remainingPointsToSpent);
      attribute.setCreationValue(1 + toSpent);
      remainingPointsToSpent -= toSpent;
    }
  }

  private void assertAllPointsToSpendUsed(int[] dotsToSpend) {
    List<Integer> dotsToSpendList = new ArrayList<>();
    for (int dot : dotsToSpend) {
      dotsToSpendList.add(dot);
    }
    for (AttributeGroupType attributeGroupType : AttributeGroupType.values()) {
      TraitGroupCost attributePoints = calculator.getAttributePoints(attributeGroupType);
      Integer groupDotsToSpend = attributePoints.getPointsToSpend();
      assertTrue("Not contains dotCount " + groupDotsToSpend, dotsToSpendList.contains(groupDotsToSpend));
      dotsToSpendList.remove(groupDotsToSpend);
    }
    assertTrue(dotsToSpendList.isEmpty());
  }

  private IAttributeCreationPoints creationPoint;
  private AttributeCostCalculator calculator;
  private AttributeModelImpl attributeModel = new AttributeModelImpl();
  private DummyHero dummyHero = new DummyHero();

  @Before
  public void setUp() throws Exception {
    dummyHero.addModel(attributeModel);
    dummyHero.addModel(new DummyTraitModel());
    dummyHero.addModel(new DummyHeroConcept());
    dummyHero.addModel(new DummyOtherTraitModel());
    attributeModel.initialize(null, dummyHero);
    this.creationPoint = new AttributeCreationPoints(6, 4, 2);
    DummyAdditionalBonusPointManagement additionalBonusPointManagement = new DummyAdditionalBonusPointManagement();
    DefaultBonusPointCosts cost = new DefaultBonusPointCosts();
    this.calculator = new AttributeCostCalculator(attributeModel, creationPoint, cost, additionalBonusPointManagement);
  }

  @Test
  public void testNoAttributesLearned() throws Exception {
    calculator.calculateAttributeCosts();
    assertEmptyPoints(Physical);
    assertEmptyPoints(Social);
    assertEmptyPoints(Mental);
    assertAllPointsToSpendUsed(creationPoint.getCounts());
  }

  @Test
  public void testFirstGroupIsPrimaryGroup() throws Exception {
    spendPoints(Physical, creationPoint.getPrimaryCount());
    calculator.calculateAttributeCosts();
    assertEmptyPoints(Social);
    assertEmptyPoints(Mental);
    assertFullyLearned(Physical, creationPoint.getPrimaryCount());
    assertAllPointsToSpendUsed(creationPoint.getCounts());
  }

  @Test
  public void testLastGroupIsPrimaryGroup() throws Exception {
    spendPoints(Mental, creationPoint.getPrimaryCount());
    calculator.calculateAttributeCosts();
    assertEmptyPoints(Physical);
    assertEmptyPoints(Social);
    assertFullyLearned(Mental, creationPoint.getPrimaryCount());
    assertAllPointsToSpendUsed(creationPoint.getCounts());
  }

  @Test
  public void testAllDotsSpentWithoutBonusPoints() throws Exception {
    spendPoints(Physical, creationPoint.getPrimaryCount());
    spendPoints(Mental, creationPoint.getSecondaryCount());
    spendPoints(Social, creationPoint.getTertiaryCount());
    calculator.calculateAttributeCosts();
    assertFullyLearned(Physical, creationPoint.getPrimaryCount());
    assertFullyLearned(Mental, creationPoint.getSecondaryCount());
    assertFullyLearned(Social, creationPoint.getTertiaryCount());
  }

  @Test
  public void testPrimaryGroupLearnedOneWithBonusPoints() throws Exception {
    spendPoints(Physical, creationPoint.getPrimaryCount() + 1);
    spendPoints(Mental, creationPoint.getSecondaryCount());
    spendPoints(Social, creationPoint.getTertiaryCount());
    calculator.calculateAttributeCosts();
    assertOverlearned(Physical, creationPoint.getPrimaryCount(), ATTRIBUTE_BONUS_POINT_COST);
    assertFullyLearned(Mental, creationPoint.getSecondaryCount());
    assertFullyLearned(Social, creationPoint.getTertiaryCount());
  }

  @Test
  public void testAllGroupsLearnedOneWithBonusPoints() throws Exception {
    spendPoints(Physical, creationPoint.getPrimaryCount() + 1);
    spendPoints(Mental, creationPoint.getSecondaryCount() + 1);
    spendPoints(Social, creationPoint.getTertiaryCount() + 1);
    calculator.calculateAttributeCosts();
    assertOverlearned(Physical, creationPoint.getPrimaryCount(), ATTRIBUTE_BONUS_POINT_COST);
    assertOverlearned(Mental, creationPoint.getSecondaryCount(), ATTRIBUTE_BONUS_POINT_COST);
    assertOverlearned(Social, creationPoint.getTertiaryCount(), ATTRIBUTE_BONUS_POINT_COST);
  }
}