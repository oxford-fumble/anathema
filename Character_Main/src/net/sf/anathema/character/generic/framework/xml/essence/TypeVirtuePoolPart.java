package net.sf.anathema.character.generic.framework.xml.essence;

import net.sf.anathema.character.generic.template.essence.FactorizedTrait;
import net.sf.anathema.character.generic.traits.GenericTrait;
import net.sf.anathema.character.generic.traits.types.VirtueType;

public class TypeVirtuePoolPart implements IVirtuePoolPart {

  private final VirtueType virtueType;
  private final int multiplier;

  public TypeVirtuePoolPart(VirtueType virtueType, int multiplier) {
    this.virtueType = virtueType;
    this.multiplier = multiplier;
  }

  @Override
  public FactorizedTrait createFactorizedTrait(GenericTrait[] virtues) {
    return new FactorizedTrait(getVirtue(virtues), multiplier);
  }

  private GenericTrait getVirtue(GenericTrait[] traits) {
    for (GenericTrait trait : traits) {
      if (trait.getType() == virtueType) {
        return trait;
      }
    }
    throw new IllegalArgumentException("No trait given for type " + virtueType);
  }
}