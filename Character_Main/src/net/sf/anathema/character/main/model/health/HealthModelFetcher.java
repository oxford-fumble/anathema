package net.sf.anathema.character.main.model.health;

import net.sf.anathema.character.main.hero.Hero;

public class HealthModelFetcher {

  public static HealthModel fetch(Hero hero) {
    return (HealthModel) hero.getModel(HealthModel.ID);
  }
}
