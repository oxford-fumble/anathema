package net.sf.anathema.character.generic.traits.types;

import net.sf.anathema.character.generic.traits.TraitType;

public enum VirtueType implements TraitType {
  Compassion, Conviction, Temperance, Valor;

  @Override
  public String getId() {
    return name();
  }

  @Override
  public void accept(ITraitTypeVisitor visitor) {
    visitor.visitVirtue(this);
  }

  @Override
  public String toString() {
    return getId();
  }
}