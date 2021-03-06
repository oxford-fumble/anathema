package net.sf.anathema.character.main.testing.dummy.template;

import net.sf.anathema.character.generic.caste.CasteType;
import net.sf.anathema.character.generic.impl.template.magic.DefaultMartialArtsRules;
import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.charms.MartialArtsLevel;
import net.sf.anathema.character.generic.template.magic.ICharmTemplate;
import net.sf.anathema.character.generic.template.magic.MartialArtsRules;

public class DummyCharmTemplate implements ICharmTemplate {

  @Override
  public ICharm[] getCharms() {
    return new ICharm[0];
  }

  @Override
  public ICharm[] getMartialArtsCharms() {
    return new ICharm[0];
  }

  @Override
  public boolean canLearnCharms() {
    return false;
  }

  @Override
  public MartialArtsRules getMartialArtsRules() {
    DefaultMartialArtsRules defaultMartialArtsRules = new DefaultMartialArtsRules(MartialArtsLevel.Mortal);
    defaultMartialArtsRules.setHighLevelAtCreation(true);
    return defaultMartialArtsRules;
  }

  @Override
  public boolean isAllowedAlienCharms(CasteType caste) {
    return false;
  }
}