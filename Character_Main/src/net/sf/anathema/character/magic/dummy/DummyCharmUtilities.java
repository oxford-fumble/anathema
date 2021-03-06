package net.sf.anathema.character.magic.dummy;

import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.charms.ComboRestrictions;
import net.sf.anathema.character.generic.magic.charms.IComboRestrictions;
import net.sf.anathema.character.generic.magic.charms.type.CharmType;
import net.sf.anathema.character.generic.traits.GenericTrait;

public class DummyCharmUtilities {

  public static ICharm createCharm(CharmType charmType) {
    return new DummyCharm("Instant", charmType, new ComboRestrictions(), null);
  }

  public static ICharm createCharm(CharmType charmType, GenericTrait prerequisite) {
    return new DummyCharm("Instant", charmType, new ComboRestrictions(), new GenericTrait[]{prerequisite});
  }

  public static ICharm createCharm(CharmType charmType, IComboRestrictions restrictions) {
    return new DummyCharm("Instant", charmType, restrictions, null);
  }

  public static ICharm createCharm(String duration, IComboRestrictions restrictions) {
    return new DummyCharm(duration, CharmType.Reflexive, restrictions, null);
  }

  public static ICharm createCharm(CharmType charmType, IComboRestrictions restrictions, GenericTrait prerequisite) {
    return new DummyCharm("Instant", charmType, restrictions, new GenericTrait[]{prerequisite});
  }

  public static ICharm createCharm(String id, String groupId) {
    DummyCharm dummyCharm = new DummyCharm(id);
    dummyCharm.setGroupId(groupId);
    return dummyCharm;
  }
}