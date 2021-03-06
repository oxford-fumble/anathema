package net.sf.anathema.character.generic.impl.magic.charm.special;

import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.charms.special.IPrerequisiteModifyingCharm;
import net.sf.anathema.character.generic.magic.charms.special.ISpecialCharmVisitor;
import net.sf.anathema.character.generic.traits.TraitType;

public class PrerequisiteModifyingCharm implements IPrerequisiteModifyingCharm {
  private final String charmId;
  private final TraitType traitType;
  private final int modifier;

  public PrerequisiteModifyingCharm(String charmId, TraitType traitType, int modifier) {
    this.traitType = traitType;
    this.modifier = modifier;
    this.charmId = charmId;
  }

  @Override
  public int modifyRequiredValue(ICharm charm, int currentlyRequiredValue) {
    TraitType mainTrait = charm.getPrimaryTraitType();
    return modifyRequiredValueIfIsApplicableToCandidate(mainTrait, currentlyRequiredValue);
  }

  @Override
  public int modifyRequiredValueIfIsApplicableToCandidate(TraitType candidateTrait, int currentlyRequiredValue) {
    //Assuming that the limit imposed for Transcendence of Ability transfers to all other Charms.
    if (currentlyRequiredValue == 10) {
      return currentlyRequiredValue;
    }
    try {
      //Assuming modification of all traits in applicable charms by the same value.
      if (candidateTrait == traitType) {
        return currentlyRequiredValue + modifier;
      }
    } catch (Exception ignored) {
    }
    return currentlyRequiredValue;
  }

  @Override
  public void accept(ISpecialCharmVisitor visitor) {
    visitor.visitPrerequisiteModifyingCharm(this);
  }

  @Override
  public String getCharmId() {
    return charmId;
  }
}