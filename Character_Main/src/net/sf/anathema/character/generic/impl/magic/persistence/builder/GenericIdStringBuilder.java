package net.sf.anathema.character.generic.impl.magic.persistence.builder;

import net.sf.anathema.character.generic.impl.magic.persistence.IGenericsBuilder;
import net.sf.anathema.character.generic.magic.charms.CharmException;
import net.sf.anathema.character.generic.traits.TraitType;
import net.sf.anathema.lib.util.Identifier;
import org.dom4j.Element;

public class GenericIdStringBuilder extends IdStringBuilder implements IIdStringBuilder, IGenericsBuilder {

  private Identifier type;

  @Override
  public String build(Element element) throws CharmException {
    if (type == null) {
      throw new IllegalStateException("Type not set.");
    }
    return super.build(element) + "." + type.getId();
  }

  @Override
  public void setType(TraitType type) {
    this.type = type;
  }
}