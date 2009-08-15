package com.twolattes.json;

import static com.twolattes.json.Json.TRUE;
import static com.twolattes.json.Json.array;
import static com.twolattes.json.Json.number;
import static com.twolattes.json.Json.object;
import static com.twolattes.json.Json.string;
import static org.junit.Assert.assertEquals;

import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;

public class PrettyPrinterTest {

  private StringWriter writer;
  private PrettyPrinter prettyPrinter;

  @Before
  public void before() {
    writer = new StringWriter();
    prettyPrinter = new PrettyPrinter("  ", writer);
  }

  @Test
  public void pretty1() throws Exception {
    object().visit(prettyPrinter);

    assertEquals("{\n}", writer.toString());
  }

  @Test
  public void pretty2() throws Exception {
    string("a").visit(prettyPrinter);

    assertEquals("\"a\"", writer.toString());
  }

  @Test
  public void pretty3() throws Exception {
    object(string("a"), string("b")).visit(prettyPrinter);

    assertEquals(
        "{\n" +
        "  \"a\":\"b\"\n" +
        "}", writer.toString());
  }

  @Test
  public void pretty4() throws Exception {
    object(string("a"), string("b1"), string("\""), string("b2")).visit(prettyPrinter);

    assertEquals(
        "{\n" +
        "  \"\\\"\":\"b2\",\n" +
        "  \"a\":\"b1\"\n" +
        "}", writer.toString());
  }

  @Test
  public void pretty5() throws Exception {
    array(array(number(5), TRUE, object(string("a"), string("b")))).visit(prettyPrinter);

    assertEquals(
        "[\n" +
        "  [\n" +
        "    5,\n" +
        "    true,\n" +
        "    {\n" +
        "      \"a\":\"b\"\n" +
        "    }\n" +
        "  ]\n" +
        "]", writer.toString());
  }

}
