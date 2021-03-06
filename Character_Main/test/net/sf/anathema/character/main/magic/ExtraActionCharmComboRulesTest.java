package net.sf.anathema.character.main.magic;

import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.charms.type.CharmType;
import net.sf.anathema.character.magic.dummy.DummyCharmUtilities;
import net.sf.anathema.character.model.charm.combo.ExtraActionCharmComboRules;
import net.sf.anathema.character.model.charm.combo.IComboRules;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExtraActionCharmComboRulesTest extends AbstractComboRulesTestCase {

  private IComboRules rules = new ExtraActionCharmComboRules();

  @Test
  public void testCharmComboTwoExtraAction() throws Exception {
    ICharm charm1 = DummyCharmUtilities.createCharm(CharmType.ExtraAction);
    ICharm charm2 = DummyCharmUtilities.createCharm(CharmType.ExtraAction);
    assertFalse(rules.isComboLegal(charm1, charm2));
  }

  @Test
  public void testCharmComboExtraActionReflexive() throws Exception {
    ICharm charm1 = DummyCharmUtilities.createCharm(CharmType.ExtraAction);
    ICharm charm2 = DummyCharmUtilities.createCharm(CharmType.Reflexive);
    assertTrue(rules.isComboLegal(charm1, charm2));
  }

  @Test
  public void testCharmComboExtraActionCharmWithSupplementalOfSameAbility() throws Exception {
    assertTrue(comboSameAbilityCharms(CharmType.ExtraAction, CharmType.Supplemental));
  }

  @Test
  public void testCharmComboExtraActionCharmWithSupplementalOfDifferentAbility() throws Exception {
    assertFalse(comboDifferentAbilityCharms(CharmType.ExtraAction, CharmType.Supplemental));
  }

  @Test
  public void testCharmComboExtraActionCharmWithSupplementalOfDifferentAttribute() throws Exception {
    assertTrue(comboDifferentAttributeCharms(CharmType.ExtraAction, CharmType.Supplemental));
  }

  @Test
  public void testCharmComboAbilityExtraActionCharmCombosWithAttributeSupplementalAllowed() throws Exception {
    getRules().setCrossPrerequisiteTypeComboAllowed(true);
    assertTrue(comboAbilityAttributeCharms(CharmType.ExtraAction, CharmType.Supplemental));
  }

  @Test
  public void testCharmComboAbilityExtraActionCharmCombosWithAttributeSupplementalForbidden() throws Exception {
    getRules().setCrossPrerequisiteTypeComboAllowed(false);
    assertFalse(comboAbilityAttributeCharms(CharmType.ExtraAction, CharmType.Supplemental));
  }

  @Test
  public void testCharmComboAbilityExtraActionCharmCombosWithAttributeSimpleAllowed() throws Exception {
    getRules().setCrossPrerequisiteTypeComboAllowed(true);
    assertTrue(comboAbilityAttributeCharms(CharmType.ExtraAction, CharmType.Simple));
  }

  @Test
  public void testCharmComboAbilityExtraActionCharmCombosWithAttributeSimpleForbidden() throws Exception {
    getRules().setCrossPrerequisiteTypeComboAllowed(false);
    assertFalse(comboAbilityAttributeCharms(CharmType.ExtraAction, CharmType.Simple));
  }

  @Test
  public void testCharmComboExtraActionCharmWithSimpleOfSameAbility() throws Exception {
    assertTrue(comboSameAbilityCharms(CharmType.ExtraAction, CharmType.Simple));
  }

  @Test
  public void testCharmComboExtraActionCharmWithSimpleOfDifferentAbility() throws Exception {
    assertFalse(comboDifferentAbilityCharms(CharmType.ExtraAction, CharmType.Simple));
  }

  @Test
  public void testCharmComboExtraActionCharmWithSimpleOfDifferentAttribute() throws Exception {
    assertTrue(comboDifferentAttributeCharms(CharmType.ExtraAction, CharmType.Simple));
  }

  @Test
  public void testCharmComboRestrictionAllAbilitiesWithSimpleCharm() throws Exception {
    assertTrue(comboAllAbilitiesCharmWithAbility(CharmType.ExtraAction, CharmType.Simple));
  }

  @Test
  public void testCharmComboRestrictionAllAbilitiesWithSupplementalCharm() throws Exception {
    assertTrue(comboAllAbilitiesCharmWithAbility(CharmType.ExtraAction, CharmType.Supplemental));
  }
}