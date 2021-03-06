package net.sf.anathema.character.generic.impl.magic.charm.type;

import net.sf.anathema.character.generic.magic.charms.type.IReflexiveSpecialsModel;

public class ReflexiveSpecialsModel implements IReflexiveSpecialsModel {

  private final Integer primaryStep;
  private final Integer secondaryStep;

  public ReflexiveSpecialsModel(Integer primaryStep, Integer secondaryStep) {
    this.primaryStep = primaryStep;
    this.secondaryStep = secondaryStep;
  }

  @Override
  public Integer getPrimaryStep() {
    return primaryStep;
  }

  @Override
  public Integer getSecondaryStep() {
    return secondaryStep;
  }

  @Override
  public boolean isSplitEnabled() {
    return secondaryStep != null;
  }
}