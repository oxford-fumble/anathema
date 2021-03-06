package net.sf.anathema.character.main.model.spells;

import net.sf.anathema.character.generic.magic.ISpell;
import net.sf.anathema.character.generic.magic.spells.CircleType;
import net.sf.anathema.character.generic.template.HeroTemplate;
import net.sf.anathema.character.generic.template.magic.ISpellMagicTemplate;
import net.sf.anathema.character.main.model.charms.CharmsModel;
import net.sf.anathema.character.main.model.charms.CharmsModelFetcher;
import net.sf.anathema.character.main.model.experience.ExperienceChange;
import net.sf.anathema.character.main.model.experience.ExperienceModel;
import net.sf.anathema.character.main.model.experience.ExperienceModelFetcher;
import net.sf.anathema.character.model.IMagicLearnListener;
import net.sf.anathema.character.model.ISpellMapper;
import net.sf.anathema.character.model.SpellMapper;
import net.sf.anathema.character.model.UnspecifiedChangeListener;
import net.sf.anathema.hero.change.ChangeAnnouncer;
import net.sf.anathema.hero.change.ChangeFlavor;
import net.sf.anathema.hero.change.FlavoredChangeListener;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.model.InitializationContext;
import net.sf.anathema.lib.control.ChangeListener;
import net.sf.anathema.lib.util.Identifier;
import org.jmock.example.announcer.Announcer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellModelImpl implements SpellModel {

  private final ProxySpellLearnStrategy strategy = new ProxySpellLearnStrategy(new CreationSpellLearnStrategy());
  private final List<ISpell> creationLearnedList = new ArrayList<>();
  private final List<ISpell> experiencedLearnedList = new ArrayList<>();
  private final Announcer<ChangeListener> changeControl = Announcer.to(ChangeListener.class);
  private final Announcer<IMagicLearnListener> magicLearnControl = Announcer.to(IMagicLearnListener.class);
  private final Map<CircleType, List<ISpell>> spellsByCircle = new HashMap<>();
  private final ISpellMapper spellMapper = new SpellMapper();
  private CharmsModel charms;
  private HeroTemplate heroTemplate;
  private ExperienceModel experience;

  @Override
  public Identifier getId() {
    return ID;
  }

  @Override
  public void initialize(InitializationContext context, Hero hero) {
    this.charms = CharmsModelFetcher.fetch(hero);
    this.experience = ExperienceModelFetcher.fetch(hero);
    this.heroTemplate = hero.getTemplate();
    for (CircleType type : CircleType.values()) {
      spellsByCircle.put(type, new ArrayList<ISpell>());
    }
    for (ISpell spell : context.getSpellCache().getSpells()) {
      spellsByCircle.get(spell.getCircleType()).add(spell);
    }
  }

  @Override
  public void initializeListening(ChangeAnnouncer announcer) {
    announcer.addListener(new FlavoredChangeListener() {
      @Override
      public void changeOccurred(ChangeFlavor flavor) {
        if (flavor == ExperienceChange.FLAVOR_EXPERIENCE_STATE) {
          boolean experienced = experience.isExperienced();
          updateLearnStrategies(experienced);
        }
      }
    });
    addChangeListener(new UnspecifiedChangeListener(announcer));
  }

  private void updateLearnStrategies(boolean experienced) {
    if (experienced) {
      strategy.setStrategy(new ExperiencedSpellLearnStrategy());
    } else {
      strategy.setStrategy(new CreationSpellLearnStrategy());
    }
  }

  @Override
  public void removeSpells(ISpell[] removedSpells) {
    strategy.removeSpells(this, removedSpells);
  }

  @Override
  public void removeSpells(ISpell[] removedSpells, boolean experienced) {
    for (ISpell spell : removedSpells) {
      if (experienced) {
        experiencedLearnedList.remove(spell);
      } else {
        creationLearnedList.remove(spell);
      }
    }
    fireSpellsForgottenEvent(removedSpells);
  }

  @Override
  public void addSpells(ISpell[] addedSpells) {
    strategy.addSpells(this, addedSpells);
  }

  @Override
  public void addSpells(ISpell[] addedSpells, boolean experienced) {
    for (ISpell spell : addedSpells) {
      if (isSpellAllowed(spell, experienced)) {
        if (experienced) {
          experiencedLearnedList.add(spell);
        } else {
          creationLearnedList.add(spell);
        }
      } else {
        throw new IllegalArgumentException("Cannot learn Spell: " + spell);
      }
    }
    fireSpellsAddedEvent(addedSpells);
  }

  @SuppressWarnings("unchecked")
  private void fireSpellsAddedEvent(ISpell[] addedSpells) {
    magicLearnControl.announce().magicLearned(addedSpells);
    changeControl.announce().changeOccurred();
  }

  @SuppressWarnings("unchecked")
  private void fireSpellsForgottenEvent(ISpell[] removedSpells) {
    magicLearnControl.announce().magicForgotten(removedSpells);
    changeControl.announce().changeOccurred();
  }

  @Override
  public boolean isSpellAllowed(ISpell spell) {
    return strategy.isSpellAllowed(this, spell);
  }

  @Override
  public boolean isSpellAllowed(ISpell spell, boolean experienced) {
    if (creationLearnedList.contains(spell) || (experienced && experiencedLearnedList.contains(spell))) {
      return false;
    }
    ISpellMagicTemplate template = heroTemplate.getMagicTemplate().getSpellMagic();
    return template.canLearnSpell(spell, charms.getLearnedCharms(true));
  }

  @Override
  public ISpell[] getLearnedSpells() {
    return strategy.getLearnedSpells(this);
  }

  @Override
  public ISpell[] getLearnedSpells(boolean experienced) {
    List<ISpell> list = new ArrayList<>();
    list.addAll(creationLearnedList);
    if (experienced) {
      list.addAll(experiencedLearnedList);
    }
    return list.toArray(new ISpell[list.size()]);
  }

  @Override
  public void addChangeListener(ChangeListener listener) {
    changeControl.addListener(listener);
  }

  @Override
  public void addMagicLearnListener(IMagicLearnListener<ISpell> listener) {
    magicLearnControl.addListener(listener);
  }

  @Override
  public ISpell[] getSpellsByCircle(CircleType circle) {
    List<ISpell> spells = spellsByCircle.get(circle);
    if (spells != null) {
      return spells.toArray(new ISpell[spells.size()]);
    }
    return new ISpell[0];
  }

  @Override
  public ISpell getSpellById(String id) {
    String correctedId = spellMapper.getId(id);
    for (ISpell spell : getAllSpells()) {
      if (spell.getId().equals(correctedId)) {
        return spell;
      }
    }
    throw new IllegalArgumentException("No Spell for id: " + id);
  }

  private Iterable<ISpell> getAllSpells() {
    List<ISpell> allSpells = new ArrayList<>();
    for (List<ISpell> circleSpells : spellsByCircle.values()) {
      allSpells.addAll(circleSpells);
    }
    return allSpells;
  }

  @Override
  public boolean isLearnedOnCreation(ISpell spell) {
    return creationLearnedList.contains(spell);
  }

  @Override
  public boolean isLearnedOnCreationOrExperience(ISpell spell) {
    return experiencedLearnedList.contains(spell) || isLearnedOnCreation(spell);
  }

  @Override
  public boolean isLearned(ISpell spell) {
    return strategy.isLearned(this, spell);
  }
}