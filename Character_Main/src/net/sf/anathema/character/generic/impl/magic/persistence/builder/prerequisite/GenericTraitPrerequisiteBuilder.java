package net.sf.anathema.character.generic.impl.magic.persistence.builder.prerequisite;

import net.sf.anathema.character.generic.traits.GenericTrait;
import net.sf.anathema.character.generic.traits.TraitType;
import net.sf.anathema.character.generic.traits.types.ValuedTraitType;
import net.sf.anathema.lib.exception.PersistenceException;
import net.sf.anathema.lib.xml.ElementUtilities;
import org.dom4j.Element;

import static net.sf.anathema.character.generic.impl.magic.ICharmXMLConstants.ATTRIB_VALUE;

public class GenericTraitPrerequisiteBuilder implements ITraitPrerequisiteBuilder {

  private TraitType type;

  @Override
  public GenericTrait build(Element element) throws PersistenceException {
    int minValue = ElementUtilities.getRequiredIntAttrib(element, ATTRIB_VALUE);
    return new ValuedTraitType(type, minValue);
  }

  public void setType(TraitType type) {
    this.type = type;
  }
}