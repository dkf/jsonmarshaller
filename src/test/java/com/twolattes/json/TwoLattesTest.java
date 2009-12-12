package com.twolattes.json;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URL;

import org.junit.Test;

import com.twolattes.json.types.URIType;
import com.twolattes.json.types.URLType;

public class TwoLattesTest {

  @Test
  public void builder1() throws Exception {
    assertEquals(
        URIType.class,
        TwoLattes.withType(URIType.class).get(URI.class));
  }

  @Test
  public void builder2() throws Exception {
    assertEquals(
        URLType.class,
        TwoLattes.withType(URLType.class).get(URL.class));
  }

}
