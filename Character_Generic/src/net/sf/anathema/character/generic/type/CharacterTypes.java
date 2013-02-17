package net.sf.anathema.character.generic.type;

import java.util.ArrayList;
import java.util.List;

public class CharacterTypes {

  private static final List<ICharacterType> types = new ArrayList<>();

  static {
    types.add(new SolarCharacterType());
    types.add(new DbCharacterType());
    types.add(new LunarCharacterType());
    types.add(new AbyssalCharacterType());
    types.add(new SiderealCharacterType());
    types.add(new InfernalCharacterType());
    types.add(new SpiritCharacterType());
    types.add(new GhostCharacterType());
    types.add(new MortalCharacterType());
  }

  public static ICharacterType findById(String id) {
    for (ICharacterType type : types) {
      if (type.getId().equals(id)) {
        return type;
      }
    }
    throw new IllegalArgumentException("No type defined for id:" + id); //$NON-NLS-1$
  }

  public static ICharacterType[] findAll() {
    return types.toArray(new ICharacterType[types.size()]);
  }

  public static Iterable<ICharacterType> getAllEssenceUsers() {
    List<ICharacterType> list = new ArrayList<>();
    for (ICharacterType type : types) {
      if (type.isEssenceUser()){
        list.add(type);
      }
    }
    return list;
  }

  public static ICharacterType[] getAllExaltTypes() {
    List<ICharacterType> list = new ArrayList<>();
    for (ICharacterType type : types) {
      if (type.isExaltType()){
        list.add(type);
      }
    }
    return list.toArray(new ICharacterType[types.size()]);
  }
}