package net.sf.anathema.character.main.model.abilities;

import net.sf.anathema.hero.model.Hero;

public class AbilityModelFetcher {

  public static AbilitiesModel fetch(Hero hero) {
    return (AbilitiesModel) hero.getModel(AbilitiesModel.ID);
  }
}
