package net.sf.anathema.hero.languages.display;

import net.sf.anathema.character.generic.type.ICharacterType;
import net.sf.anathema.character.platform.RegisteredCharacterView;
import net.sf.anathema.character.view.SubViewFactory;

@RegisteredCharacterView(LanguagesView.class)
public class LanguagesSubViewFactory implements SubViewFactory {
  @Override
  public <T> T create(ICharacterType type) {
    return (T) new LanguagesViewImpl();
  }
}