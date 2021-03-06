package net.sf.anathema.character.library.virtueflaw.model;

import net.sf.anathema.character.generic.traits.TraitType;
import net.sf.anathema.hero.model.HeroModel;
import net.sf.anathema.lib.control.IBooleanValueChangedListener;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.lib.util.SimpleIdentifier;

public interface VirtueFlawModel extends HeroModel {

  Identifier ID = new SimpleIdentifier("GreatCurse");

  boolean isVirtueFlawChangable();

  VirtueFlaw getVirtueFlaw();

  TraitType[] getFlawVirtueTypes();

  void addVirtueFlawChangableListener(IBooleanValueChangedListener listener);
}