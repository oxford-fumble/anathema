package net.sf.anathema.character.main.testing.dummy.template;

import net.sf.anathema.character.generic.additionalrules.IAdditionalBonusPointPool;
import net.sf.anathema.character.generic.additionalrules.IAdditionalEssencePool;
import net.sf.anathema.character.generic.additionalrules.IAdditionalMagicLearnPool;
import net.sf.anathema.character.generic.additionalrules.IAdditionalRules;
import net.sf.anathema.character.generic.additionalrules.IAdditionalTraitRules;
import net.sf.anathema.character.generic.traits.GenericTrait;
import net.sf.anathema.character.main.model.traits.TraitMap;

public final class DummyAdditionalRules implements IAdditionalRules, IAdditionalTraitRules {

  @Override
  public IAdditionalBonusPointPool[] getAdditionalBonusPointPools() {
    return new IAdditionalBonusPointPool[0];
  }

  @Override
  public IAdditionalMagicLearnPool[] getAdditionalMagicLearnPools() {
    return new IAdditionalMagicLearnPool[0];
  }

  @Override
  public IAdditionalEssencePool[] getAdditionalEssencePools() {
    return new IAdditionalEssencePool[0];
  }

  @Override
  public boolean isRevisedIntimacies() {
    return false;
  }

  @Override
  public boolean isWillpowerVirtueBased() {
    return true;
  }

  @Override
  public boolean isAllowedTraitValue(GenericTrait trait, TraitMap traitMap) {
    return true;
  }

  @Override
  public String[] getCompulsiveCharmIDs() {
    return new String[0];
  }

  @Override
  public IAdditionalTraitRules getAdditionalTraitRules() {
    return this;
  }
}