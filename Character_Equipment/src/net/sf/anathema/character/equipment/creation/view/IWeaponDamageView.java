package net.sf.anathema.character.equipment.creation.view;

import net.disy.commons.swing.layout.grid.IDialogComponent;
import net.sf.anathema.character.generic.health.HealthType;
import net.sf.anathema.lib.gui.selection.IObjectSelectionView;
import net.sf.anathema.lib.gui.widgets.IntegerSpinner;

import javax.swing.ListCellRenderer;

public interface IWeaponDamageView extends IDialogComponent, IObjectSelectionView<HealthType> {

  IntegerSpinner getDamageIntegerSpinner();
  
  IntegerSpinner getMinDamageIntegerSpinner();

  void setHealthTypeRenderer(ListCellRenderer renderer);

  void setDamageLabelText(String label);
  
  void setMinDamageLabelText(String label);
}