package net.sf.anathema.hero.specialties.model;

import net.sf.anathema.character.generic.framework.ITraitReference;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.SpecialtiesCollection;
import net.sf.anathema.character.generic.traits.TraitType;
import net.sf.anathema.character.library.trait.specialties.SpecialtiesModel;
import net.sf.anathema.character.library.trait.specialties.SpecialtiesModelFetcher;
import net.sf.anathema.character.library.trait.specialties.Specialty;
import net.sf.anathema.character.library.trait.subtrait.ISpecialtyListener;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.lib.control.ChangeListener;

public class SpecialtiesCollectionImpl implements SpecialtiesCollection {

  private final Hero hero;

  public SpecialtiesCollectionImpl(Hero hero) {
    this.hero = hero;
  }

  @Override
  public Specialty[] getSpecialties(TraitType traitType) {
    SpecialtiesModel specialtyConfiguration = SpecialtiesModelFetcher.fetch(hero);
    return specialtyConfiguration.getSpecialtiesContainer(traitType).getSubTraits();
  }

  @Override
  public void addSpecialtyListChangeListener(final ChangeListener listener) {
    SpecialtiesModel config = SpecialtiesModelFetcher.fetch(hero);
    for (ITraitReference trait : config.getAllTraits()) {
      config.getSpecialtiesContainer(trait).addSubTraitListener(new ISpecialtyListener() {
        @Override
        public void subTraitValueChanged() {
        }

        @Override
        public void subTraitAdded(Specialty subTrait) {
          listener.changeOccurred();
        }

        @Override
        public void subTraitRemoved(Specialty subTrait) {
          listener.changeOccurred();
        }
      });
    }
  }
}
