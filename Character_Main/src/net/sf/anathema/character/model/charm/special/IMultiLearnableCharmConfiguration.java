package net.sf.anathema.character.model.charm.special;

import net.sf.anathema.character.generic.magic.charms.special.ISpecialCharmConfiguration;
import net.sf.anathema.character.library.trait.Trait;

public interface IMultiLearnableCharmConfiguration extends ISpecialCharmConfiguration {

  Trait getCategory();

  void setCurrentLearnCount(int newValue);
}