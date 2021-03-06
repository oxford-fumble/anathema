package net.sf.anathema.hero.abilities.sheet.content;

import net.sf.anathema.character.reporting.pdf.content.RegisteredReportContent;
import net.sf.anathema.character.reporting.pdf.content.ReportContentFactory;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.lib.resources.Resources;

@RegisteredReportContent(produces = AbilitiesContent.class)
public class AbilitiesContentFactory implements ReportContentFactory<AbilitiesContent> {
  private Resources resources;

  public AbilitiesContentFactory(Resources resources) {
    this.resources = resources;
  }

  @Override
  public AbilitiesContent create(ReportSession session) {
    return new AbilitiesContent(session.getHero(), session.getCharacter(), resources);
  }
}
