package com.twolattes.json.values;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

public class JsonTest {

  @Test
  public void objectEqualsAndHashCode1() throws Exception {
    testEqualsAndHashCode(
        new Json.Object(),
        new Json.Object());
  }

  @Test
  public void objectEqualsAndHashCode2() throws Exception {
    Json.Object o2 = new Json.Object();
    o2.put(new Json.String("a"), new Json.Boolean(true));
    testNotEquals(
        new Json.Object(),
        o2);
  }

  @Test
  public void objectEqualsAndHashCode3() throws Exception {
    Json.Object o1 = new Json.Object();
    o1.put(new Json.String("a"), new Json.Boolean(true));

    Json.Object o2 = new Json.Object();
    o2.put(new Json.String("a"), new Json.Boolean(true));
    testEqualsAndHashCode(o1, o2);
  }

  @Test
  public void stringEqualsAndHashCode() throws Exception {
    testEqualsAndHashCode(
        new Json.String("hello"),
        new Json.String("hello"));
  }

  @Test
  public void numberEqualsAndHashCode() throws Exception {
    testEqualsAndHashCode(
        new Json.Number(9.0),
        new Json.Number(new BigDecimal(9.0)));
    testEqualsAndHashCode(
        new Json.Number(new BigDecimal("0.00")),
        new Json.Number(new BigDecimal("0.0")));
  }

  @Test
  public void booleanEqualsAndHashCode() throws Exception {
    testEqualsAndHashCode(
        new Json.Boolean(true),
        new Json.Boolean(true));
    testEqualsAndHashCode(
        new Json.Boolean(false),
        new Json.Boolean(false));
    testNotEquals(new Json.Boolean(true), new Json.Boolean(false));
  }

  @Test
  public void nullEqualsAndHashCode() throws Exception {
    testEqualsAndHashCode(
        new Json.Null(),
        new Json.Null());
  }

  private void testEqualsAndHashCode(Json v1, Json v2) {
    assertEquals(v1, v2);
    assertEquals(v2, v1);
    assertTrue(v1.hashCode() == v2.hashCode());
  }

  private void testNotEquals(Json v1, Json v2) {
    assertFalse(v1.equals(v2));
    assertFalse(v2.equals(v1));
  }

}
