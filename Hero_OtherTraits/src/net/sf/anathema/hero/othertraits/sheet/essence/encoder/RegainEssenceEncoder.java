package net.sf.anathema.hero.othertraits.sheet.essence.encoder;

import com.itextpdf.text.DocumentException;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.AbstractContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.SheetGraphics;
import net.sf.anathema.hero.othertraits.sheet.essence.content.RegainEssenceContent;

public class RegainEssenceEncoder extends AbstractContentEncoder<RegainEssenceContent> {

  private RegainEssenceTableEncoder poolTable = new RegainEssenceTableEncoder();

  public RegainEssenceEncoder() {
    super(RegainEssenceContent.class);
  }

  @Override
  public void encode(SheetGraphics graphics, ReportSession session, Bounds bounds) throws DocumentException {
    RegainEssenceContent essenceContent = createContent(session);
    poolTable.encodeTable(graphics, essenceContent, bounds);
  }
}
