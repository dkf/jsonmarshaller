package com.twolattes.json;

import static com.twolattes.json.Json.FALSE;
import static com.twolattes.json.Json.NULL;
import static com.twolattes.json.Json.TRUE;
import static com.twolattes.json.Json.array;
import static com.twolattes.json.Json.number;
import static com.twolattes.json.Json.object;
import static com.twolattes.json.Json.string;
import static com.twolattes.json.TwoLattes.createMarshaller;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class JavaTypeMarshallingTest {

  Marshaller<Byte> byteMarshaller = createMarshaller(Byte.class);
  Marshaller<Short> shortMarshaller = createMarshaller(Short.class);
  Marshaller<Integer> integerMarshaller = createMarshaller(Integer.class);
  Marshaller<Long> longMarshaller = createMarshaller(Long.class);
  Marshaller<BigDecimal> bdMarshaller = createMarshaller(BigDecimal.class);
  Marshaller<Float> floatMarshaller = createMarshaller(Float.class);
  Marshaller<Double> doubleMarshaller = createMarshaller(Double.class);
  Marshaller<String> stringMarshaller = createMarshaller(String.class);
  Marshaller<Character> characterMarshaller = createMarshaller(Character.class);
  Marshaller<Boolean> booleanMarshaller = createMarshaller(Boolean.class);

  @Test
  public void bytes() {
    assertEquals(
        NULL,
        byteMarshaller.marshall(null));
    assertEquals(
        number(23),
        byteMarshaller.marshall((byte) 23));
    assertEquals(
        array(number(Byte.MIN_VALUE), number(0), number(Byte.MAX_VALUE)),
        byteMarshaller.marshallList(asList(Byte.MIN_VALUE, (byte) 0, Byte.MAX_VALUE)));
    assertEquals(
        object(string("a"), number(2), string("-a"), number(-2)),
        byteMarshaller.marshallMap(map("a", (byte) 2, "-a", (byte) -2)));
  }

  @Test
  public void shorts() {
    assertEquals(
        number(Short.MIN_VALUE),
        shortMarshaller.marshall(Short.MIN_VALUE));
    assertEquals(
        array(),
        shortMarshaller.marshallList(Collections.<Short>emptyList()));
    assertEquals(
        object(string(";"), NULL),
        shortMarshaller.marshallMap(singletonMap(";", (Short) null)));
  }

  @Test
  public void integers() {
    assertEquals(
        number(Integer.MIN_VALUE),
        integerMarshaller.marshall(Integer.MIN_VALUE));
    assertEquals(
        array(number(1), NULL, number(1), number(0), number(1)),
        integerMarshaller.marshallList(asList(1, null, 1, 0, 1)));
    assertEquals(
        object(string("ii"), number(Integer.MAX_VALUE)),
        integerMarshaller.marshallMap(singletonMap("ii", Integer.MAX_VALUE)));
  }

  @Test
  public void longs() {
    assertEquals(
        number(-1234567890),
        longMarshaller.marshall(-1234567890L));
    assertEquals(
        array(number(-2), number(99999999999L)),
        longMarshaller.marshallList(asList(-2L, 99999999999L), "foo"));
    assertEquals(
        object(string("1"), number(2)),
        longMarshaller.marshallMap(singletonMap("1", 2L)));
  }

  @Test
  public void bigDecimals() {
    assertEquals(
        number(new BigDecimal("-12345678901234567890.12345")),
        bdMarshaller.marshall(new BigDecimal("-12345678901234567890.12345")));
    assertEquals(
        array(number(0)),
        bdMarshaller.marshallList(asList(BigDecimal.ZERO), null));
    assertEquals(
        object(string("B D"), number(1)),
        bdMarshaller.marshallMap(singletonMap("B D", BigDecimal.ONE)));
  }

  @Test
  public void floats() {
    assertEquals(
        number(new BigDecimal(".0000000000000000000000000000000000000117549435")),
        floatMarshaller.marshall(Float.MIN_NORMAL, null));
    assertEquals(
        array(number(Float.MIN_VALUE), number(Float.MAX_VALUE)),
        floatMarshaller.marshallList(asList(Float.MIN_VALUE, Float.MAX_VALUE)));
    assertEquals(
        object(),
        floatMarshaller.marshallMap(Collections.<String, Float>emptyMap()));
  }

  @Test(expected = NumberFormatException.class)
  public void floatNaN() {
    floatMarshaller.marshall(Float.NaN);
  }

  @Test(expected = NumberFormatException.class)
  public void floatPositiveInfinity() {
    floatMarshaller.marshall(Float.POSITIVE_INFINITY);
  }

  @Test(expected = NumberFormatException.class)
  public void floatNegativeInfinity() {
    floatMarshaller.marshall(Float.NEGATIVE_INFINITY);
  }

  @Test
  public void doubles() {
    assertEquals(
        number(new BigDecimal("3.141592653589793")),
        doubleMarshaller.marshall(Math.PI, null));
    assertEquals(
        array(number(Double.MAX_VALUE)),
        doubleMarshaller.marshallList(asList(Double.MAX_VALUE)));
    assertEquals(
        object(string("b"), number(1.0 / 3.0)),
        doubleMarshaller.marshallMap(singletonMap("b", 1.0 / 3.0)));
  }

  @Test(expected = NumberFormatException.class)
  public void doubleNaN() {
    doubleMarshaller.marshall(Double.NaN);
  }

  @Test(expected = NumberFormatException.class)
  public void doublePositiveInfinity() {
    doubleMarshaller.marshall(Double.POSITIVE_INFINITY);
  }

  @Test(expected = NumberFormatException.class)
  public void doubleNegativeInfinity() {
    doubleMarshaller.marshall(Double.NEGATIVE_INFINITY);
  }

  @Test
  public void strings() {
    assertEquals(
        string(""),
        stringMarshaller.marshall("", null));
    assertEquals(
        array(string(" \t \n '\"\"' ")),
        stringMarshaller.marshallList(asList(" \t \n '\"\"' ")));
    assertEquals(
        object(string("b"), NULL),
        stringMarshaller.marshallMap(singletonMap("b", (String) null)));
  }

  @Test
  public void characters() {
    assertEquals(
        string(String.valueOf(Character.MAX_VALUE)),
        characterMarshaller.marshall(Character.MAX_VALUE, "view"));
    assertEquals(
        array(string(" "), NULL, string("\n"), string("\u230C")),
        characterMarshaller.marshallList(asList(' ', null, '\n', '\u230C')));
    assertEquals(
        object(string("b"), string("c")),
        characterMarshaller.marshallMap(singletonMap("b", 'c')));
  }

  @Test
  public void booleans() {
    assertEquals(
        FALSE,
        booleanMarshaller.marshall(false));
    assertEquals(
        array(TRUE, TRUE, FALSE, NULL),
        booleanMarshaller.marshallList(asList(true, true, false, null)));
    assertEquals(
        object(string(""), FALSE, string(" "), TRUE),
        booleanMarshaller.marshallMap(map("", false, " ", true)));
  }

  private static <K, V> Map<K, V> map(K k1, V v1, K k2, V v2) {
    Map<K, V> map = new HashMap<K, V>(2);
    map.put(k1, v1);
    map.put(k2, v2);
    return map;
  }

}
