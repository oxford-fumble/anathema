package net.sf.anathema.character.generic.impl.template.magic;

import net.sf.anathema.character.generic.impl.magic.persistence.ICharmCache;
import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.charms.CharmIdMap;
import net.sf.anathema.character.generic.magic.charms.ICharmLearnableArbitrator;
import net.sf.anathema.character.generic.magic.charms.special.ISpecialCharm;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.lib.util.SimpleIdentifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CharmProvider implements ICharmProvider {

  private final Map<Identifier, ISpecialCharm[]> charmsByType = new HashMap<>();
  private final ICharmCache cache;

  public CharmProvider(ICharmCache cache) {
    this.cache = cache;
    for (Identifier type : cache.getCharmTypes()) {
      charmsByType.put(type, cache.getSpecialCharmData(type));
    }
  }

  @Override
  public ISpecialCharm[] getSpecialCharms(ICharmLearnableArbitrator arbitrator, CharmIdMap map, Identifier preferredType) {
    List<ISpecialCharm> relevantCharms = new ArrayList<>();
    ISpecialCharm[] allSpecialCharms = getAllSpecialCharms(preferredType);
    for (ISpecialCharm specialCharm : allSpecialCharms) {
      ICharm charm = map.getCharmById(specialCharm.getCharmId());
      if (charm != null && arbitrator.isLearnable(charm)) {
        relevantCharms.add(specialCharm);
      }
    }
    return relevantCharms.toArray(new ISpecialCharm[relevantCharms.size()]);
  }

  @Override
  public ISpecialCharm[] getSpecialCharms(Identifier type) {
    ISpecialCharm[] specialCharms = charmsByType.get(new SimpleIdentifier(type.getId()));
    if (specialCharms == null) {
      specialCharms = new ISpecialCharm[0];
    }
    return specialCharms;
  }

  private ISpecialCharm[] getAllSpecialCharms(Identifier preferredCharacterType) {
    SpecialCharmSet set = new SpecialCharmSet();
    for (Identifier type : charmsByType.keySet()) {
      set.add(getSpecialCharms(type));
    }
    for (ISpecialCharm preferredCharm : getSpecialCharms(preferredCharacterType)) {
      set.add(preferredCharm);
    }
    return set.toArray(new ISpecialCharm[set.size()]);
  }

  @Override
  public String getCharmRename(String name) {
    return cache.getCharmRename(name);
  }
}