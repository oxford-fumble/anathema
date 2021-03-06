package net.sf.anathema.character.view;

import net.sf.anathema.character.generic.type.ICharacterType;
import net.sf.anathema.character.library.intvalue.IntValueDisplayFactoryPrototype;
import net.sf.anathema.character.platform.RegisteredCharacterView;
import net.sf.anathema.framework.value.IntegerViewFactory;

@RegisteredCharacterView(IGroupedFavorableTraitConfigurationView.class)
public class GroupedFavorableTraitViewFactory implements SubViewFactory {

  @Override
  public <T> T create(ICharacterType type) {
    IntegerViewFactory withMarker = IntValueDisplayFactoryPrototype.createWithMarkerForCharacterType(type);
    return (T) new GroupedFavorableTraitConfigurationView(withMarker);
  }
}