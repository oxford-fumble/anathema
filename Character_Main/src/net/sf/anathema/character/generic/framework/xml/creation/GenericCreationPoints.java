package net.sf.anathema.character.generic.framework.xml.creation;

import com.google.common.base.Preconditions;
import net.sf.anathema.character.generic.impl.template.points.AbilityCreationPoints;
import net.sf.anathema.character.generic.impl.template.points.AttributeCreationPoints;
import net.sf.anathema.character.generic.template.creation.ICreationPoints;
import net.sf.anathema.character.generic.template.points.IAbilityCreationPoints;
import net.sf.anathema.character.generic.template.points.IAttributeCreationPoints;
import net.sf.anathema.lib.lang.clone.ReflectionCloneableObject;

public class GenericCreationPoints extends ReflectionCloneableObject<GenericCreationPoints> implements ICreationPoints {

  private IAbilityCreationPoints abilityCreationPoints = new AbilityCreationPoints(0, 0, 0);
  private IAttributeCreationPoints attributeCreationPoints = new AttributeCreationPoints(0, 0, 0);
  private int bonusPointCount = 0;
  private int defaultCreationCharmCount = 0;
  private int favoredCreationCharmCount = 0;
  private int virtueCreationPoints = 0;
  private int specialityCreationPoints = 0;

  @Override
  public IAbilityCreationPoints getAbilityCreationPoints() {
    return abilityCreationPoints;
  }

  @Override
  public IAttributeCreationPoints getAttributeCreationPoints() {
    return attributeCreationPoints;
  }

  @Override
  public int getBonusPointCount() {
    return bonusPointCount;
  }

  @Override
  public int getDefaultCreationCharmCount() {
    return defaultCreationCharmCount;
  }

  @Override
  public int getFavoredCreationCharmCount() {
    return favoredCreationCharmCount;
  }

  @Override
  public int getVirtueCreationPoints() {
    return virtueCreationPoints;
  }

  @Override
  public int getSpecialtyCreationPoints() {
    return specialityCreationPoints;
  }

  public void setAbilityCreationPoints(IAbilityCreationPoints abiltyCreationPoints) {
    Preconditions.checkNotNull(abiltyCreationPoints);
    this.abilityCreationPoints = abiltyCreationPoints;
  }

  public void setAttributeCreationPoints(IAttributeCreationPoints attributeCreationPoints) {
    Preconditions.checkNotNull(attributeCreationPoints);
    this.attributeCreationPoints = attributeCreationPoints;
  }

  public void setBonusPointCount(int bonusPointCount) {
    Preconditions.checkArgument(bonusPointCount >= 0, "Bonus point count must be positive.");
    this.bonusPointCount = bonusPointCount;
  }

  public void setGeneralCreationCharmCount(int charmCount) {
    Preconditions.checkArgument(charmCount >= 0, "Default charm count must be positive.");
    this.defaultCreationCharmCount = charmCount;
  }

  public void setFavoredCreationCharmCount(int charmCount) {
    Preconditions.checkArgument(charmCount >= 0, "Favored charm count must be positive.");
    this.favoredCreationCharmCount = charmCount;
  }

  public void setVirtueCreationPoints(int virtueCreationPoints) {
    Preconditions.checkArgument(virtueCreationPoints >= 0, "Virtue creation points must be positive.");
    this.virtueCreationPoints = virtueCreationPoints;
  }

  public void setSpecialityPoints(int specialityCreationPoints) {
    Preconditions.checkArgument(specialityCreationPoints >= 0, "Speciality creation points must be positive.");
    this.specialityCreationPoints = specialityCreationPoints;
  }

  @Override
  public GenericCreationPoints clone() {
    GenericCreationPoints clone = super.clone();
    clone.attributeCreationPoints = attributeCreationPoints.clone();
    clone.abilityCreationPoints = abilityCreationPoints.clone();
    return clone;
  }
}