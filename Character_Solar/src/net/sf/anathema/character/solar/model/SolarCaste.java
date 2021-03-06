package net.sf.anathema.character.solar.model;

import net.sf.anathema.character.generic.caste.CasteType;

public enum SolarCaste implements CasteType {

  Dawn, Zenith, Twilight, Night, Eclipse;

  @Override
  public String getId() {
    return name();
  }
}