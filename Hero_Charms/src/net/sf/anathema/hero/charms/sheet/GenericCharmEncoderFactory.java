package net.sf.anathema.hero.charms.sheet;

import net.sf.anathema.character.reporting.pdf.content.BasicContent;
import net.sf.anathema.character.reporting.pdf.rendering.EncoderIds;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.AbstractEncoderFactory;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.RegisteredEncoderFactory;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.magic.PreferredGenericCharmHeight;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.ContentEncoder;
import net.sf.anathema.lib.resources.Resources;

@RegisteredEncoderFactory
public class GenericCharmEncoderFactory extends AbstractEncoderFactory {

  public GenericCharmEncoderFactory() {
    super(EncoderIds.GENERIC_CHARMS);
    setPreferredHeight(new PreferredGenericCharmHeight());
  }

  @Override
  public ContentEncoder create(Resources resources, BasicContent content) {
    return new GenericCharmEncoder(resources);
  }

  @Override
  public boolean supports(BasicContent content) {
    return content.isEssenceUser();
  }
}
