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
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class JavaTypeUnmarshallingTest {

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
        null,
        byteMarshaller.unmarshall(NULL));
    assertEquals(
        23,
        byteMarshaller.unmarshall(number(23)));
    assertEquals(
        asList(Byte.MIN_VALUE, (byte) 0, Byte.MAX_VALUE),
        byteMarshaller.unmarshallList(
            array(number(Byte.MIN_VALUE), number(0), number(Byte.MAX_VALUE))));
    assertEquals(
        map("a", (byte) 2, "-a", (byte) -2),
        byteMarshaller.unmarshallMap(
            object(string("a"), number(2), string("-a"), number(-2))));
  }

  @Test
  public void integerAsByte() {
    // TODO: Expect IllegalArgumentException?
    assertEquals(-128, byteMarshaller.unmarshall(number(128)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void stringAsByte() {
    byteMarshaller.unmarshall(string("0"));
  }

  @Test
  public void shorts() {
    assertEquals(
        Short.MIN_VALUE,
        shortMarshaller.unmarshall(number(Short.MIN_VALUE)));
    assertEquals(
        emptyList(),
        shortMarshaller.unmarshallList(array()));
    assertEquals(
        singletonMap(";", null),
        shortMarshaller.unmarshallMap(object(string(";"), NULL)));
  }

  @Test
  public void integers() {
    assertEquals(
        Integer.MIN_VALUE,
        integerMarshaller.unmarshall(number(Integer.MIN_VALUE)));
    assertEquals(
        asList(1, null, 1, 0, 1),
        integerMarshaller.unmarshallList(
            array(number(1), NULL, number(1), number(0), number(1))));
    assertEquals(
        singletonMap("ii", Integer.MAX_VALUE),
        integerMarshaller.unmarshallMap(
            object(string("ii"), number(Integer.MAX_VALUE))));
  }

  @Test
  public void longs() {
    assertEquals(
        -1234567890,
        longMarshaller.unmarshall(number(-1234567890)));
    assertEquals(
        asList(-2L, 99999999999L),
        longMarshaller.unmarshallList(
            array(number(-2), number(99999999999L)), "foo"));
    assertEquals(
        singletonMap("1", 2L),
        longMarshaller.unmarshallMap(object(string("1"), number(2))));
  }

  @Test
  public void bigDecimals() {
    assertEquals(
        new BigDecimal("-12345678901234567890.12345"),
        bdMarshaller.unmarshall(
            number(new BigDecimal("-12345678901234567890.12345"))));
    assertEquals(
        asList(BigDecimal.ZERO),
        bdMarshaller.unmarshallList(array(number(0)), null));
    assertEquals(
        singletonMap("B D", BigDecimal.ONE),
        bdMarshaller.unmarshallMap(object(string("B D"), number(1))));
  }

  @Test
  public void floats() {
    assertEquals(
        Float.MIN_NORMAL,
        floatMarshaller.unmarshall(
            number(new BigDecimal(".0000000000000000000000000000000000000117549435")),
            null));
    assertEquals(
        asList(Float.MIN_VALUE, Float.MAX_VALUE),
        floatMarshaller.unmarshallList(
            array(number(Float.MIN_VALUE), number(Float.MAX_VALUE))));
    assertEquals(
        emptyMap(),
        floatMarshaller.unmarshallMap(object()));
  }

  @Test
  public void numberTooPreciseForFloat() {
    // TODO: Expect IllegalArgumentException?
    assertEquals(
        123.45679f,
        floatMarshaller.unmarshall(number(123.4567890123)));
  }

  @Test
  public void numberTooBigForFloat() {
    // TODO: Expect IllegalArgumentException?
    assertEquals(
        Float.POSITIVE_INFINITY,
        floatMarshaller.unmarshall(number(Double.MAX_VALUE)));
  }

  @Test
  public void doubles() {
    assertEquals(
        Math.PI,
        doubleMarshaller.unmarshall(number(new BigDecimal("3.141592653589793"))));
    assertEquals(
        asList(Double.MAX_VALUE),
        doubleMarshaller.unmarshallList(array(number(Double.MAX_VALUE)), null));
    assertEquals(
        singletonMap("b", 1.0 / 3.0),
        doubleMarshaller.unmarshallMap(object(string("b"), number(1.0 / 3.0))));
  }

  @Test
  public void strings() {
    assertEquals(
        "",
        stringMarshaller.unmarshall(string(""), null));
    assertEquals(
        asList(" \t \n '\"\"' "),
        stringMarshaller.unmarshallList(array(string(" \t \n '\"\"' "))));
    assertEquals(
        singletonMap("b", null),
        stringMarshaller.unmarshallMap(object(string("b"), NULL)));
  }

  @Test
  public void characters() {
    assertEquals(
        '\u0000',
        characterMarshaller.unmarshall(string("\u0000")));
    assertEquals(
        asList(' ', null, '\n', '\u230C'),
        characterMarshaller.unmarshallList(
            array(string(" "), NULL, string("\n"), string("\u230C")), "view"));
    assertEquals(
        singletonMap("b", 'c'),
        characterMarshaller.unmarshallMap(object(string("b"), string("c"))));
  }

  @Test(expected = IllegalArgumentException.class)
  public void numberAsCharacter() {
    characterMarshaller.unmarshall(number(32));
  }

  @Test
  public void booleans() {
    assertEquals(
        false,
        booleanMarshaller.unmarshall(FALSE));
    assertEquals(
        asList(true, true, false, null),
        booleanMarshaller.unmarshallList(array(TRUE, TRUE, FALSE, NULL)));
    assertEquals(
        map("", false, " ", true),
        booleanMarshaller.unmarshallMap(
            object(string(""), FALSE, string(" "), TRUE)));
  }

  private static <K, V> Map<K, V> map(K k1, V v1, K k2, V v2) {
    Map<K, V> map = new HashMap<K, V>(2);
    map.put(k1, v1);
    map.put(k2, v2);
    return map;
  }

}
