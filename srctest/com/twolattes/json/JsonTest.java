package com.twolattes.json;

import static com.google.common.collect.Lists.newArrayList;
import static com.twolattes.json.Json.FALSE;
import static com.twolattes.json.Json.NULL;
import static com.twolattes.json.Json.TRUE;
import static com.twolattes.json.Json.array;
import static com.twolattes.json.Json.booleanValue;
import static com.twolattes.json.Json.fromHex;
import static com.twolattes.json.Json.fromString;
import static com.twolattes.json.Json.nullValue;
import static com.twolattes.json.Json.number;
import static com.twolattes.json.Json.object;
import static com.twolattes.json.Json.string;
import static com.twolattes.json.OrgJsonAssert.assertJsonEquals;
import static java.lang.String.format;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class JsonTest {

  private StringWriter writer;

  @Before
  public void before() throws Exception {
    writer = new StringWriter();
  }

  @Test
  public void writeNull() throws Exception {
    NULL.write(writer);
    assertEquals("null", writer.toString());
  }

  @Test
  public void writeBooleanTrue() throws Exception {
    TRUE.write(writer);
    assertEquals("true", writer.toString());
  }

  @Test
  public void writeBooleanFalse() throws Exception {
    FALSE.write(writer);
    assertEquals("false", writer.toString());
  }

  @Test
  public void writeNumber1() throws Exception {
    number(56.3).write(writer);
    assertEquals("56.3", writer.toString());
  }

  @Test
  public void writeNumber2() throws Exception {
    number(new BigDecimal("789798793182739721789798793182739721")).write(writer);
    assertEquals("789798793182739721789798793182739721", writer.toString());

    double number = new JSONArray("[" + writer.toString() + "]").getDouble(0);
    assertEquals(789798793182739721789798793182739721.0, number);
  }

  @Test
  public void writeString1() throws Exception {
    string("yeah!").write(writer);
    assertEquals("\"yeah!\"", writer.toString());
  }

  @Test
  public void writeString2() throws Exception {
    string("ye\"ah!").write(writer);
    assertEquals("\"ye\\\"ah!\"", writer.toString());
  }

  @Test
  public void writeString3() throws Exception {
    string("ye\\ah!").write(writer);
    assertEquals("\"ye\\\\ah!\"", writer.toString());
  }

  @Test
  public void writeString4() throws Exception {
    assertEquals("\"\\b\"", string("\b").toString());
    assertEquals("\"\\f\"", string("\f").toString());
    assertEquals("\"\\n\"", string("\n").toString());
    assertEquals("\"\\r\"", string("\r").toString());
    assertEquals("\"\\t\"", string("\t").toString());
  }

  @Test
  public void writeArray1() throws Exception {
    array().write(writer);
    assertEquals("[]", writer.toString());
  }

  @Test
  public void writeArray2() throws Exception {
    array(TRUE, FALSE).write(writer);
    assertEquals("[true,false]", writer.toString());
  }

  @Test
  public void writeObject1() throws Exception {
    object().write(writer);
    assertEquals("{}", writer.toString());
  }

  @Test
  public void writeObject2() throws Exception {
    object(string("a"), NULL).write(writer);
    assertEquals("{\"a\":null}", writer.toString());
  }

  @Test
  public void writeObject3() throws Exception {
    object(string("a"), NULL, string("b"), NULL).write(writer);
    assertEquals("{\"a\":null,\"b\":null}", writer.toString());
  }

  @Test
  public void readNull1() throws Exception {
    assertEquals(NULL, fromString("null"));
  }

  @Test
  public void readNull2() throws Exception {
    assertEquals(NULL, fromString("  null "));
  }

  @Test
  public void readBoolean1() throws Exception {
    assertEquals(TRUE, fromString("true"));
  }

  @Test
  public void readBoolean2() throws Exception {
    assertEquals(FALSE, fromString("false"));
  }

  @Test
  public void readBoolean3() throws Exception {
    assertEquals(FALSE, fromString("\tfalse"));
  }

  @Test
  public void readString1() throws Exception {
    assertEquals(string(""), fromString("\"\""));
  }

  @Test
  public void readString2() throws Exception {
    assertEquals(string("a"), fromString("\"a\""));
  }

  @Test
  public void readString3() throws Exception {
    assertEquals(string(" "), fromString("\" \""));
  }

  @Test
  public void readString4() throws Exception {
    assertEquals(string("\""), fromString("\"\\\"\""));
    assertEquals(string("\\"), fromString("\"\\\\\""));
    assertEquals(string("/"), fromString("\"\\/\""));
    assertEquals(string("\b"), fromString("\"\\b\""));
    assertEquals(string("\f"), fromString("\"\\f\""));
    assertEquals(string("\n"), fromString("\"\\n\""));
    assertEquals(string("\r"), fromString("\"\\r\""));
    assertEquals(string("\t"), fromString("\"\\t\""));
    assertEquals(string("\ua098"), fromString("\"\\ua098\""));
    assertEquals(string("\u2931"), fromString("\"\\u2931\""));
  }

  @Test(expected = IllegalArgumentException.class)
  public void readString5() throws Exception {
    fromString("\"");
  }

  @Test
  public void readString6() throws Exception {
    assertEquals(
        string("can you parse me ? this aleph (\u05D0)"),
        fromString("\"can you parse me ? this aleph (\u05D0)\""));
  }

  @Test
  public void readNumber1() throws Exception {
    assertEquals(number(1), fromString("1"));
  }

  @Test
  public void readNumber2() throws Exception {
    assertEquals(number(-1), fromString("-1"));
  }

  @Test
  public void readNumber3() throws Exception {
    assertEquals(number(0.78), fromString("0.78"));
  }

  @Test
  public void readNumber4() throws Exception {
    // We allow multiple 0 to make the parse faster.
    fromString("00.78");
  }

  @Test
  public void readNumber5() throws Exception {
    assertEquals(number(10), fromString("1e+1"));
    assertEquals(number(10), fromString("1E+1"));
    assertEquals(number(400), fromString("4e2"));
    assertEquals(number(400), fromString("4E2"));
    assertEquals(number(0.1), fromString("1e-1"));
    assertEquals(number(0.1), fromString("1E-1"));
    assertEquals(number(8), fromString("8e+00"));
    assertEquals(number(9), fromString("9E+0"));
    assertEquals(number(125), fromString("125e-0"));
    assertEquals(number(3), fromString("3E-0000"));
  }

  @Test(expected = NumberFormatException.class)
  public void readNumber6() throws Exception {
    fromString("1e");
  }

  @Test(expected = NumberFormatException.class)
  public void readNumber7() throws Exception {
    fromString("1E+");
  }

  @Test(expected = NumberFormatException.class)
  public void readNumber8() throws Exception {
    fromString("1e-");
  }

  @Test
  public void readNumber9() throws Exception {
    assertEquals(
        number(new BigDecimal("78923187432674312231.78923187432674312231e21733")),
        fromString("78923187432674312231.78923187432674312231e21733"));
  }

  @Test
  public void readArray1() throws Exception {
    assertEquals(array(), fromString("[]"));
  }

  @Test
  public void readArray2() throws Exception {
    assertEquals(array(TRUE), fromString("[true]"));
  }

  @Test
  public void readArray3() throws Exception {
    assertEquals(
        array(TRUE, NULL),
        fromString("[  true , null \t]"));
  }

  @Test
  public void readArray4() throws Exception {
    assertEquals(
        array(TRUE, NULL, string("")),
        fromString("[  true , null \t,\"\" ]"));
    assertEquals(
        array(TRUE, NULL, string("")),
        fromString("[true,null,\"\"]"));
    assertEquals(
        array(TRUE, NULL, string("")),
        fromString("[true, null,\"\"]"));
  }

  @Test
  public void readArray5() throws Exception {
    assertEquals(
        array(
            array(
                array(
                    array(FALSE)))),
        fromString("[[[[false]]]]"));
  }

  @Test
  public void readArray6() throws Exception {
    assertEquals(
        array(
            array(
                array(
                    array(FALSE))),
        NULL,
        array(FALSE)),
        fromString("[[[[false]]],null,[false]]"));
  }

  @Test
  public void readArray7() throws Exception {
    assertEquals(
        array(number(89), number(8.7), number(42)),
        fromString("[89,8.7,4.2e1]"));
  }

  @Test
  public void readObject1() throws Exception {
    assertEquals(object(), fromString("{}"));
  }

  @Test
  public void readObject2() throws Exception {
    assertEquals(
        object(string("a"), number(5)),
        fromString("{\"a\":5}"));
  }

  @Test
  public void readObject3() throws Exception {
    assertEquals(
        object(string("a"), number(5), string("b"), FALSE),
        fromString("{\"a\":5,\"b\":false}"));
  }

  @Test
  public void readFromHex() {
    assertEquals(0, fromHex('0'));
    assertEquals(3, fromHex('3'));
    assertEquals(8, fromHex('8'));
    assertEquals(9, fromHex('9'));
    assertEquals(10, fromHex('a'));
    assertEquals(15, fromHex('f'));
    assertEquals(10, fromHex('A'));
    assertEquals(15, fromHex('F'));
  }

  @Test
  public void objectFromPairs1() {
    assertEquals(object(), object(new Json.Value[0]));
  }

  @Test
  public void objectFromPairs2() {
    assertEquals(
        object(string("a"), number(1)),
        object(new Json.Value[] { string("a"), number(1) }));
  }

  @Test(expected = IllegalArgumentException.class)
  public void objectFromPairs3() {
    object(new Json.Value[] { string("a") });
  }

  @Test(expected = IllegalArgumentException.class)
  public void objectFromPairs4() {
    object(new Json.Value[] { number(1), number(1) });
  }

  @Test
  public void objectEqualsAndHashCode1() throws Exception {
    testEqualsAndHashCode(
        object(),
        object());
  }

  @Test
  public void objectEqualsAndHashCode2() throws Exception {
    testNotEquals(
        object(),
        object(string("a"), booleanValue(true)));
  }

  @Test
  public void objectEqualsAndHashCode3() throws Exception {
    testEqualsAndHashCode(
        object(string("a"), booleanValue(true)),
        object(string("a"), booleanValue(true)));
  }

  @Test
  public void stringEqualsAndHashCode() throws Exception {
    testEqualsAndHashCode(
        string("hello"),
        string("hello"));
  }

  @Test
  public void numberEqualsAndHashCode() throws Exception {
    testEqualsAndHashCode(
        number(9.0),
        number(new BigDecimal(9.0)));
    testEqualsAndHashCode(
        number(new BigDecimal("0.00")),
        number(new BigDecimal("0.0")));
  }

  @Test
  public void booleanEqualsAndHashCode() throws Exception {
    testEqualsAndHashCode(
        booleanValue(true),
        booleanValue(true));
    testEqualsAndHashCode(
        booleanValue(false),
        booleanValue(false));
    testNotEquals(
        booleanValue(true),
        booleanValue(false));
  }

  @Test
  public void nullEqualsAndHashCode() throws Exception {
    testEqualsAndHashCode(
        nullValue(),
        nullValue());

    testNotEquals(NULL, string("a"));
    testNotEquals(NULL, number(5));
    testNotEquals(NULL, FALSE);
    testNotEquals(NULL, array());
    testNotEquals(NULL, object());
  }

  @Test
  @SuppressWarnings("unchecked")
  public void numbersEquality1() {
    Congruence.check(
        newArrayList(
            number((short) 3),
            number(3),
            number(3L),
            number(3F),
            number(3.0),
            number(BigDecimal.valueOf(3))),
        newArrayList(
            number((short) 5),
            number(5),
            number(5L),
            number(5F),
            number(5.0),
            number(BigDecimal.valueOf(5))),
        newArrayList(
            number(10.5F),
            number(10.5),
            number(new BigDecimal("10.5000"))));
  }

  private void testEqualsAndHashCode(Json.Value v1, Json.Value v2) {
    assertEquals(v1, v2);
    assertEquals(v2, v1);
    assertEquals(v1.hashCode(), v2.hashCode());
  }

  private void testNotEquals(Json.Value v1, Json.Value v2) {
    assertFalse(v1.equals(v2));
    assertFalse(v2.equals(v1));
  }

  @Test
  public void regression1() throws Exception {
    regression(1, true);
  }

  @Test
  public void regression2() throws Exception {
    regression(2, false);
  }

  @Test
  public void regression3() throws Exception {
    regression(3, true);
  }

  @Test
  public void regression4() throws Exception {
    regression(4, true);
  }

  private void regression(int index, boolean array) throws IOException {
    Json.Value sample = Json.read(new BufferedReader(new InputStreamReader(
        JsonTest.class.getResourceAsStream(format("/com/twolattes/json/testdata/sample%s.json", index)))));
    Json.Value samplePretty = Json.read(new BufferedReader(new InputStreamReader(
        JsonTest.class.getResourceAsStream(format("/com/twolattes/json/testdata/sample%s_pretty.json", index)))));

    System.out.println(sample);
    System.out.println(samplePretty);
    assertEquals(sample, samplePretty);

    Object sampleOrgJson;
    if (array) {
      sampleOrgJson = new JSONArray(sample.toString());
    } else {
      sampleOrgJson = new JSONObject(sample.toString());
    }

    assertJsonEquals(sample, sampleOrgJson);
    assertJsonEquals(samplePretty, sampleOrgJson);
  }

}
