package com.twolattes.json.types;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.junit.Test;


public class URLTypeTest {
  @Test
  public void testMarshall() throws Exception {
    Object url = new URLType().marshall(new URL("http://www.twolattes.com"));

    assertEquals("http://www.twolattes.com", url);
  }

  @Test
  public void testUnmarshall() throws Exception {
    URL url = new URLType().unmarshall("http://www.twolattes.com");

    assertEquals(new URL("http://www.twolattes.com"), url);
  }
}
