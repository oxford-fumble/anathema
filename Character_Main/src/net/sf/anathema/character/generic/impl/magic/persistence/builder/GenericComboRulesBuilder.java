package net.sf.anathema.character.generic.impl.magic.persistence.builder;

import net.sf.anathema.character.generic.impl.magic.persistence.IGenericsBuilder;
import net.sf.anathema.character.generic.magic.charms.CharmException;
import net.sf.anathema.character.generic.magic.charms.ComboRestrictions;
import net.sf.anathema.character.generic.traits.TraitType;
import net.sf.anathema.lib.util.Identifier;
import org.dom4j.Element;

public class GenericComboRulesBuilder extends ComboRulesBuilder implements IComboRulesBuilder, IGenericsBuilder {

  private Identifier type;

  @Override
  protected void buildRestrictionList(ComboRestrictions comboRules, Element restrictionElement) throws CharmException {
    super.buildRestrictionList(comboRules, restrictionElement);
    for (String charmId : GenericCharmUtilities.getAllReferencedGenericCharms(restrictionElement, type)) {
      comboRules.addRestrictedCharmId(charmId);
    }
  }

  @Override
  public void setType(TraitType type) {
    this.type = type;
  }
}