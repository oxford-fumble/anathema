package net.sf.anathema.character.main.testing.dummy.template;

import net.sf.anathema.character.generic.additionalrules.IAdditionalRules;
import net.sf.anathema.character.generic.caste.ICasteCollection;
import net.sf.anathema.character.generic.impl.additional.NullAdditionalRules;
import net.sf.anathema.character.generic.impl.template.essence.NullEssenceTemplate;
import net.sf.anathema.character.generic.template.HeroTemplate;
import net.sf.anathema.character.generic.template.ITemplateType;
import net.sf.anathema.character.generic.template.ITraitTemplateCollection;
import net.sf.anathema.character.generic.template.TemplateType;
import net.sf.anathema.character.generic.template.abilities.GroupedTraitType;
import net.sf.anathema.character.generic.template.creation.BonusPointCosts;
import net.sf.anathema.character.generic.template.creation.ICreationPoints;
import net.sf.anathema.character.generic.template.essence.IEssenceTemplate;
import net.sf.anathema.character.generic.template.experience.IExperiencePointCosts;
import net.sf.anathema.character.generic.template.magic.ICharmTemplate;
import net.sf.anathema.character.generic.template.magic.IMagicTemplate;
import net.sf.anathema.character.generic.template.presentation.IPresentationProperties;
import net.sf.anathema.character.generic.traits.TraitType;
import net.sf.anathema.character.generic.type.ICharacterType;
import net.sf.anathema.lib.util.SimpleIdentifier;

import java.util.ArrayList;
import java.util.List;

public class SimpleDummyCharacterTemplate implements HeroTemplate {

  private final String subtype;
  private final ICharacterType type;

  public SimpleDummyCharacterTemplate(ICharacterType type, String subtype) {
    this.type = type;
    this.subtype = subtype;
  }

  @Override
  public GroupedTraitType[] getAbilityGroups() {
    return new GroupedTraitType[0];
  }

  @Override
  public GroupedTraitType[] getAttributeGroups() {
    return new GroupedTraitType[0];
  }

  @Override
  public IAdditionalRules getAdditionalRules() {
    return new NullAdditionalRules();
  }

  @Override
  public BonusPointCosts getBonusPointCosts() {
    return null;
  }

  @Override
  public ICasteCollection getCasteCollection() {
    return null;
  }

  @Override
  public ICreationPoints getCreationPoints() {
    return new TestCreationPoints();
  }

  @Override
  public IEssenceTemplate getEssenceTemplate() {
    return new NullEssenceTemplate();
  }

  @Override
  public IExperiencePointCosts getExperienceCost() {
    return null;
  }

  @Override
  public IPresentationProperties getPresentationProperties() {
    return null;
  }

  @Override
  public ITemplateType getTemplateType() {
    if (subtype == null) {
      return new TemplateType(type);
    }
    return new TemplateType(type, new SimpleIdentifier(subtype));
  }

  @Override
  public ITraitTemplateCollection getTraitTemplateCollection() {
    return new DummyTraitTemplateCollection();
  }

  @Override
  public TraitType[] getToughnessControllingTraitTypes() {
    return new TraitType[0];
  }

  @Override
  public List<String> getModels() {
    return new ArrayList<>();
  }

  @Override
  public IMagicTemplate getMagicTemplate() {
    ICharmTemplate charmTemplate = new DummyCharmTemplate();
    return new DummyMagicTemplate(null, charmTemplate, null);
  }

  @Override
  public String[] getBaseHealthProviders() {
    return new String[0];
  }

  @Override
  public boolean isCustomTemplate() {
    return false;
  }
}