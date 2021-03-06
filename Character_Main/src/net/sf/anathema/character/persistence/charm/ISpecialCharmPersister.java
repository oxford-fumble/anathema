package net.sf.anathema.character.persistence.charm;

import net.sf.anathema.character.generic.magic.charms.special.ISpecialCharmConfiguration;
import net.sf.anathema.lib.exception.PersistenceException;
import org.dom4j.Element;

public interface ISpecialCharmPersister {

  void saveConfiguration(Element specialElement, ISpecialCharmConfiguration specialCharmConfiguration);

  void loadConfiguration(Element specialElement, ISpecialCharmConfiguration specialCharmConfiguration) throws PersistenceException;
}