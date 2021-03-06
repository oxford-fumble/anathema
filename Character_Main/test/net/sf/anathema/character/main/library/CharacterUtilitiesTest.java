package net.sf.anathema.character.main.library;

import net.sf.anathema.character.generic.equipment.ICharacterStatsModifiers;
import net.sf.anathema.character.generic.impl.CharacterUtilities;
import net.sf.anathema.character.generic.traits.types.AbilityType;
import net.sf.anathema.character.generic.traits.types.AttributeType;
import net.sf.anathema.character.generic.traits.types.OtherTraitType;
import net.sf.anathema.character.main.testing.dummy.DummyExaltCharacterType;
import net.sf.anathema.character.main.testing.dummy.DummyGenericTraitCollection;
import net.sf.anathema.character.main.testing.dummy.DummyMundaneCharacterType;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class CharacterUtilitiesTest {

  private DummyGenericTraitCollection traitCollection = new DummyGenericTraitCollection();
  private ICharacterStatsModifiers modifiers = mock(ICharacterStatsModifiers.class);

  @Test
  public void testDodgeMdvWithEvenAttributeSum() throws Exception {
    traitCollection.setValue(AbilityType.Integrity, 1);
    traitCollection.setValue(OtherTraitType.Essence, 1);
    traitCollection.setValue(OtherTraitType.Willpower, 4);
    Assert.assertEquals(3, CharacterUtilities.getDodgeMdv(traitCollection, modifiers));
  }

  @Test
  public void testDodgeMdvWithOddAttributeSum() throws Exception {
    traitCollection.setValue(AbilityType.Integrity, 1);
    traitCollection.setValue(OtherTraitType.Essence, 1);
    traitCollection.setValue(OtherTraitType.Willpower, 5);
    assertEquals(3, CharacterUtilities.getDodgeMdv(traitCollection, modifiers));
  }

  @Test
  public void testMortalDodgeDvWithEssenceValueOne() throws Exception {
    traitCollection.setValue(AbilityType.Dodge, 1);
    traitCollection.setValue(AttributeType.Dexterity, 2);
    traitCollection.setValue(OtherTraitType.Essence, 1);
    assertEquals(1, CharacterUtilities.getDodgeDv(new DummyMundaneCharacterType(), traitCollection, modifiers));
  }

  @Test
  public void testMortalDodgeDvWithEssenceValueTwo() throws Exception {
    traitCollection.setValue(AbilityType.Dodge, 1);
    traitCollection.setValue(AttributeType.Dexterity, 2);
    traitCollection.setValue(OtherTraitType.Essence, 2);
    assertEquals(2, CharacterUtilities.getDodgeDv(new DummyMundaneCharacterType(), traitCollection, modifiers));
  }

  @Test
  public void testExaltDodgeWithEssenceValueOne() throws Exception {
    traitCollection.setValue(AbilityType.Dodge, 1);
    traitCollection.setValue(AttributeType.Dexterity, 1);
    traitCollection.setValue(OtherTraitType.Essence, 1);
    assertEquals(1, CharacterUtilities.getDodgeDv(new DummyExaltCharacterType(), traitCollection, modifiers));
  }

  @Test
  public void testExaltDodgeWithEssenceValueTwo() throws Exception {
    traitCollection.setValue(AbilityType.Dodge, 1);
    traitCollection.setValue(AttributeType.Dexterity, 1);
    traitCollection.setValue(OtherTraitType.Essence, 2);
    assertEquals(2, CharacterUtilities.getDodgeDv(new DummyExaltCharacterType(), traitCollection, modifiers));
  }

  @Test
  public void testExaltDodgeOddSum() throws Exception {
    traitCollection.setValue(AbilityType.Dodge, 1);
    traitCollection.setValue(AttributeType.Dexterity, 2);
    traitCollection.setValue(OtherTraitType.Essence, 2);
    assertEquals(3, CharacterUtilities.getDodgeDv(new DummyExaltCharacterType(), traitCollection, modifiers));
  }
}