package com.twolattes.json.values;

import static junit.framework.Assert.assertEquals;

import java.io.StringWriter;
import java.math.BigDecimal;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class WriteVisitorTest {

  private StringWriter writer;
  private WriteVisitor visitor;

  @Before
  public void before() throws Exception {
    writer = new StringWriter();
    visitor = new WriteVisitor(writer);
  }

  @Test
  public void printNull() throws Exception {
    Json.NULL.visit(visitor);
    assertEquals("null", writer.toString());
  }

  @Test
  public void printBooleanTrue() throws Exception {
    Json.TRUE.visit(visitor);
    assertEquals("true", writer.toString());
  }

  @Test
  public void printBooleanFalse() throws Exception {
    Json.FALSE.visit(visitor);
    assertEquals("false", writer.toString());
  }

  @Test
  public void printNumber1() throws Exception {
    new Json.Number(56.3).visit(visitor);
    assertEquals("56.3", writer.toString());
  }

  @Test
  public void printNumber2() throws Exception {
    new Json.Number(new BigDecimal("789798793182739721789798793182739721")).visit(visitor);
    assertEquals("789798793182739721789798793182739721", writer.toString());

    double number = new JSONArray("[" + writer.toString() + "]").getDouble(0);
    assertEquals(789798793182739721789798793182739721.0, number);
  }

  @Test
  public void printString1() throws Exception {
    new Json.String("yeah!").visit(visitor);
    assertEquals("\"yeah!\"", writer.toString());
  }

  @Test
  public void printString2() throws Exception {
    new Json.String("ye\"ah!").visit(visitor);
    assertEquals("\"ye\\\"ah!\"", writer.toString());
  }

  @Test
  public void printString3() throws Exception {
    new Json.String("ye\\ah!").visit(visitor);
    assertEquals("\"ye\\\\ah!\"", writer.toString());
  }

  @Test
  public void printArray1() throws Exception {
    new Json.Array().visit(visitor);
    assertEquals("[]", writer.toString());
  }

  @Test
  public void printArray2() throws Exception {
    new Json.Array(Json.TRUE, Json.FALSE).visit(visitor);
    assertEquals("[true,false]", writer.toString());
  }

  @Test
  public void printObject1() throws Exception {
    new Json.Object().visit(visitor);
    assertEquals("{}", writer.toString());
  }

  @Test
  public void printObject2() throws Exception {
    Json.Object o = new Json.Object();
    o.put(new Json.String("a"), Json.NULL);
    o.visit(visitor);
    assertEquals("{\"a\":null}", writer.toString());
  }

}
