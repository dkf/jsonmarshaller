package com.twolattes.json.types;

import static com.twolattes.json.Json.string;
import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.junit.Test;


public class URLTypeTest {
  @Test
  public void testMarshall() throws Exception {
    assertEquals(
        string("http://www.twolattes.com"),
        new URLType().marshall(new URL("http://www.twolattes.com")));
  }

  @Test
  public void testUnmarshall() throws Exception {
    assertEquals(
        new URL("http://www.twolattes.com"),
        new URLType().unmarshall(string("http://www.twolattes.com")));
  }
}
