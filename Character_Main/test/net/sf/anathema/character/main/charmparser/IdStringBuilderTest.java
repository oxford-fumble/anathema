package net.sf.anathema.character.main.charmparser;

import net.sf.anathema.character.generic.impl.magic.persistence.builder.IIdStringBuilder;
import net.sf.anathema.character.generic.impl.magic.persistence.builder.IdStringBuilder;
import net.sf.anathema.character.generic.magic.charms.CharmException;
import net.sf.anathema.lib.xml.DocumentUtilities;
import org.dom4j.Element;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IdStringBuilderTest {

  private IIdStringBuilder builder = new IdStringBuilder();

  @Test
  public void testIdPresent() throws Exception {
    String xml = "<charm id=\"test\"/>";
    Element rootElement = DocumentUtilities.read(xml).getRootElement();
    String id = builder.build(rootElement);
    assertEquals("test", id);
  }

  @Test
  public void testIdVariable() throws Exception {
    String xml = "<charm id=\"otherTest\"/>";
    Element rootElement = DocumentUtilities.read(xml).getRootElement();
    String id = builder.build(rootElement);
    assertEquals("otherTest", id);
  }

  @Test(expected = CharmException.class)
  public void testIdMissing() throws Exception {
    String xml = "<charm />";
    Element rootElement = DocumentUtilities.read(xml).getRootElement();
    builder.build(rootElement);
  }

  @Test(expected = CharmException.class)
  public void testBadId() throws Exception {
    String xml = "<charm id=\"\"/>";
    Element rootElement = DocumentUtilities.read(xml).getRootElement();
    builder.build(rootElement);
  }
}