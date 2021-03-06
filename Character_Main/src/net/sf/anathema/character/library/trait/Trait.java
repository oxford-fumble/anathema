package net.sf.anathema.character.library.trait;

import net.sf.anathema.character.generic.traits.GenericTrait;
import net.sf.anathema.character.library.ITraitFavorization;
import net.sf.anathema.lib.control.IIntValueChangedListener;

public interface Trait extends GenericTrait {

  int getCreationValue();

  int getExperiencedValue();

  int getAbsoluteMinValue();

  int getCreationCalculationValue();

  int getExperiencedCalculationValue();

  boolean isLowerable();

  int getCalculationValue();

  int getZeroCalculationValue();

  void resetCreationValue();

  void resetExperiencedValue();

  void setCreationValue(int value);

  void setExperiencedValue(int value);

  ITraitFavorization getFavorization();

  int getInitialValue();

  int getMaximalValue();

  void addCreationPointListener(IIntValueChangedListener listener);

  void removeCreationPointListener(IIntValueChangedListener listener);

  void addCurrentValueListener(IIntValueChangedListener listener);

  void applyCapModifier(int modifier);

  int getModifiedMaximalValue();

  int getUnmodifiedMaximalValue();

  void setUncheckedCreationValue(int value);

  void setUncheckedExperiencedValue(int value);

  void setCurrentValue(int value);

  int getMinimalValue();

  int getCalculationMinValue();

  void resetCurrentValue();

  void setModifiedCreationRange(int newInitialValue, int newUpperValue);
}