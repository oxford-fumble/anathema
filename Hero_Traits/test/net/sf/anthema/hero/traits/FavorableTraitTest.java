package net.sf.anthema.hero.traits;

import net.sf.anathema.character.generic.caste.CasteType;
import net.sf.anathema.character.generic.impl.traits.SimpleTraitTemplate;
import net.sf.anathema.character.generic.traits.ITraitTemplate;
import net.sf.anathema.character.generic.traits.types.AbilityType;
import net.sf.anathema.character.generic.traits.types.OtherTraitType;
import net.sf.anathema.character.library.trait.DefaultTrait;
import net.sf.anathema.character.library.trait.FriendlyValueChangeChecker;
import net.sf.anathema.character.library.trait.favorable.FavorableState;
import net.sf.anathema.character.library.trait.favorable.IFavorableStateChangedListener;
import net.sf.anathema.character.library.trait.favorable.IncrementChecker;
import net.sf.anathema.character.library.trait.rules.FavorableTraitRules;
import net.sf.anathema.character.library.trait.specialties.DefaultTraitReference;
import net.sf.anathema.character.library.trait.specialties.SpecialtiesContainer;
import net.sf.anathema.character.library.trait.specialties.Specialty;
import net.sf.anathema.character.library.trait.subtrait.ISubTraitContainer;
import net.sf.anathema.character.main.testing.dummy.DummyCasteType;
import net.sf.anathema.character.main.testing.dummy.DummyHero;
import net.sf.anathema.character.main.testing.dummy.models.DummyHeroConcept;
import net.sf.anathema.character.main.testing.dummy.models.DummyOtherTraitModel;
import net.sf.anathema.character.main.testing.dummy.models.DummyTraitModel;
import net.sf.anathema.character.model.context.trait.CreationTraitValueStrategy;
import net.sf.anathema.character.model.context.trait.ExperiencedTraitValueStrategy;
import net.sf.anathema.character.model.context.trait.ProxyTraitValueStrategy;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.lib.control.IIntValueChangedListener;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class FavorableTraitTest {

  private IncrementChecker incrementChecker = Mockito.mock(IncrementChecker.class);
  private IFavorableStateChangedListener abilityStateListener = Mockito.mock(IFavorableStateChangedListener.class);
  private ProxyTraitValueStrategy valueStrategy;
  private DefaultTrait trait;
  private DummyHero dummyHero = new DummyHero();

  @Before
  public void createTrait() throws Exception {
    this.valueStrategy = new ProxyTraitValueStrategy(new CreationTraitValueStrategy());
    DummyTraitModel traits = new DummyTraitModel();
    traits.valueStrategy = valueStrategy;
    DummyOtherTraitModel otherTraitModel = new DummyOtherTraitModel();
    dummyHero.addModel(otherTraitModel);
    dummyHero.addModel(new DummyHeroConcept());
    dummyHero.addModel(traits);
    otherTraitModel.getTrait(OtherTraitType.Essence).setCurrentValue(2);
    this.trait = createObjectUnderTest(dummyHero);
  }

  @Test
  public void testSetAbilityToFavored() throws Exception {
    allowOneFavoredIncrement();
    trait.getFavorization().addFavorableStateChangedListener(abilityStateListener);
    assertEquals(0, trait.getCreationValue());
    trait.getFavorization().setFavorableState(FavorableState.Favored);
    verify(abilityStateListener).favorableStateChanged(FavorableState.Favored);
    assertEquals(1, trait.getCreationValue());
  }

  private void allowOneFavoredIncrement() {
    when(incrementChecker.isValidIncrement(1)).thenReturn(true);
  }

  @Test
  public void testSetAbiltyToFavoredUnallowed() throws Exception {
    when(incrementChecker.isValidIncrement(1)).thenReturn(false);
    trait.getFavorization().setFavorableState(FavorableState.Favored);
    assertSame(FavorableState.Default, trait.getFavorization().getFavorableState());
    assertEquals(0, trait.getCreationValue());
  }

  @Test
  public void testSetFavoredAbiltyCreationValueBelow1() throws Exception {
    allowOneFavoredIncrement();
    trait.getFavorization().setFavorableState(FavorableState.Favored);
    assertTrue(trait.getFavorization().isFavored());
    trait.setCurrentValue(0);
    assertEquals(1, trait.getCreationValue());
  }

  @Test
  public void testCasteAbilityNotSetToFavored() throws Exception {
    trait.getFavorization().setFavorableState(FavorableState.Caste);
    trait.getFavorization().addFavorableStateChangedListener(abilityStateListener);
    trait.getFavorization().setFavorableState(FavorableState.Favored);
    assertSame(FavorableState.Caste, trait.getFavorization().getFavorableState());
    verifyZeroInteractions(abilityStateListener);
  }

  @Test
  public void testExceedCreationValueMaximum() throws Exception {
    trait.setCurrentValue(6);
    assertEquals(5, trait.getCreationValue());
  }

  @Test
  public void testUnderrunCreationValueMinimum() throws Exception {
    trait.setCurrentValue(-1);
    assertEquals(0, trait.getCreationValue());
  }

  private DefaultTrait createObjectUnderTest(Hero hero) {
    ITraitTemplate archeryTemplate = SimpleTraitTemplate.createEssenceLimitedTemplate(0);
    FavorableTraitRules rules = new FavorableTraitRules(AbilityType.Archery, archeryTemplate, hero);
    return new DefaultTrait(hero, rules, new CasteType[]{new DummyCasteType()}, new FriendlyValueChangeChecker(), incrementChecker);
  }

  @Test
  public void creationValueIsLowerBoundForExperiencedValue() throws Exception {
    trait.setCurrentValue(2);
    valueStrategy.setStrategy(new ExperiencedTraitValueStrategy());
    trait.setCurrentValue(3);
    final int[] holder = new int[1];
    trait.addCurrentValueListener(new IIntValueChangedListener() {
      @Override
      public void valueChanged(int newValue) {
        holder[0] = newValue;
      }
    });
    trait.setCurrentValue(0);
    assertEquals(2, holder[0]);
  }

  @Test
  public void testSetValueTo6OnExperiencedCharacterWithoutHighEssence() throws Exception {
    valueStrategy.setStrategy(new ExperiencedTraitValueStrategy());
    trait.setCurrentValue(6);
    assertEquals(5, trait.getCurrentValue());
  }

  @Test
  public void testExperienceSpecialtyCount() throws Exception {
    ISubTraitContainer container = new SpecialtiesContainer(new DefaultTraitReference(trait), dummyHero);
    Specialty specialty = container.addSubTrait("TestSpecialty");
    specialty.setCreationValue(1);
    valueStrategy.setStrategy(new ExperiencedTraitValueStrategy());
    specialty.setExperiencedValue(2);
    assertEquals(2, specialty.getCurrentValue());
    assertEquals(1, container.getCreationDotTotal());
    assertEquals(1, container.getExperienceDotTotal());
  }

  @Test
  public void testCreationSpecialtyDuringExperienced() throws Exception {
    ISubTraitContainer container = new SpecialtiesContainer(new DefaultTraitReference(trait), dummyHero);
    Specialty specialty = container.addSubTrait("TestSpecialty");
    specialty.setCreationValue(2);
    assertEquals(2, specialty.getCreationValue());
    assertEquals(-1, specialty.getExperiencedValue());
    assertEquals(2, specialty.getCurrentValue());
    assertEquals(2, container.getCreationDotTotal());
    assertEquals(0, container.getExperienceDotTotal());
  }
}