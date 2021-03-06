package net.sf.anathema.character.presenter;

import net.sf.anathema.character.generic.traits.types.VirtueType;
import net.sf.anathema.character.library.trait.Trait;
import net.sf.anathema.character.library.trait.presenter.TraitPresenter;
import net.sf.anathema.character.main.model.traits.TraitMap;
import net.sf.anathema.character.view.AdvantageView;
import net.sf.anathema.framework.value.IIntValueView;
import net.sf.anathema.lib.gui.Presenter;
import net.sf.anathema.lib.resources.Resources;

public class VirtueConfigurationPresenter implements Presenter {

  private final Trait[] virtues;
  private final AdvantageView view;
  private final Resources resources;

  public VirtueConfigurationPresenter(Resources resources, TraitMap traits, AdvantageView view) {
    this.resources = resources;
    this.virtues = traits.getTraits(VirtueType.values());
    this.view = view;
  }

  @Override
  public void initPresentation() {
    for (Trait virtue : virtues) {
      String labelText = resources.getString("VirtueType.Name." + virtue.getType().getId());
      IIntValueView virtueView = view.addVirtue(labelText, virtue.getCurrentValue(), virtue.getMaximalValue());
      new TraitPresenter(virtue, virtueView).initPresentation();
    }
  }
}
