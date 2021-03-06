package net.sf.anathema.character.generic.framework.xml.essence;

import net.sf.anathema.character.generic.template.essence.FactorizedTrait;
import net.sf.anathema.character.generic.template.essence.IEssenceTemplate;
import net.sf.anathema.character.generic.traits.GenericTrait;
import net.sf.anathema.lib.lang.clone.ReflectionCloneableObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenericEssenceTemplate extends ReflectionCloneableObject<GenericEssenceTemplate> implements IEssenceTemplate {

  private boolean isEssenceUser = false;
  private IEssencePoolConfiguration personalPoolConfiguration;
  private IEssencePoolConfiguration peripheralPoolConfiguration;

  private FactorizedTrait[] createFactorizedTraits(IEssencePoolConfiguration poolConfiguration, GenericTrait willpower, GenericTrait[] virtues,
                                                   GenericTrait essence) {
    if (poolConfiguration == null) {
      return new FactorizedTrait[0];
    }
    List<FactorizedTrait> traits = new ArrayList<>();
    traits.add(new FactorizedTrait(essence, poolConfiguration.getEssenceMultiplier()));
    traits.add(new FactorizedTrait(willpower, poolConfiguration.getWillpowerMultiplier()));
    Collections.addAll(traits, poolConfiguration.createVirtueFactorizedTrait(virtues));
    return traits.toArray(new FactorizedTrait[traits.size()]);
  }

  @Override
  public FactorizedTrait[] getPersonalTraits(GenericTrait willpower, GenericTrait[] virtues, GenericTrait essence) {
    return createFactorizedTraits(personalPoolConfiguration, willpower, virtues, essence);
  }

  @Override
  public FactorizedTrait[] getPeripheralTraits(GenericTrait willpower, GenericTrait[] virtues, GenericTrait essence) {
    return createFactorizedTraits(peripheralPoolConfiguration, willpower, virtues, essence);
  }

  @Override
  public boolean isEssenceUser() {
    return isEssenceUser;
  }

  public void setPersonalPoolConfiguration(IEssencePoolConfiguration configuration) {
    this.personalPoolConfiguration = configuration;
  }

  public void setPeripheralPoolConfiguration(IEssencePoolConfiguration configuration) {
    this.peripheralPoolConfiguration = configuration;
  }

  public void setEssenceUser(boolean isEssenceUser) {
    this.isEssenceUser = isEssenceUser;
  }
}