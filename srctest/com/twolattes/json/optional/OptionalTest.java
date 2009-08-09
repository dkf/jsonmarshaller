package com.twolattes.json.optional;

import static com.twolattes.json.Json.array;
import static com.twolattes.json.Json.number;
import static com.twolattes.json.Json.object;
import static com.twolattes.json.Json.string;
import static com.twolattes.json.TwoLattes.createMarshaller;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.twolattes.json.Marshaller;

public class OptionalTest {

  private Marshaller<OptionalValues> marshaller;

  @Before
  public void start() {
    marshaller = createMarshaller(OptionalValues.class);
  }

  @Test
  public void marshall1() {
    assertEquals(
        object(string("intValue"), number(4)),
        marshaller.marshall(new OptionalValues()));
  }

  @Test
  public void marshall2() {
    assertEquals(
        object(
            string("intValue"), number(4),
            string("integer"), number(5)),
        marshaller.marshall(new OptionalValues() {{
          integer = 5;
        }}));
  }

  @Test
  public void marshall3() {
    assertEquals(
        object(
            string("intValue"), number(4),
            string("strings"), array()),
        marshaller.marshall(new OptionalValues() {{
          strings = new ArrayList<String>();
        }}));
  }

  @Test
  public void unmarshallDefault() {
    checkValues(
        marshaller.unmarshall(object()),
        4, null, null);
  }

  @Test
  public void unmarshallIntValue() {
    checkValues(
        marshaller.unmarshall(object(string("intValue"), number(6))),
        6, null, null);
  }

  @Test
  public void unmarshallInteger() {
    checkValues(
        marshaller.unmarshall(object(string("integer"), number(6))),
        4, 6, null);
  }

  @Test
  public void unmarshallString1() {
    checkValues(
        marshaller.unmarshall(object(string("strings"), array())),
        4, null, new ArrayList<String>());
  }

  @Test
  public void unmarshallString2() {
    ArrayList<String> strings = new ArrayList<String>();
    strings.add("f");
    strings.add("o");
    strings.add("o");
    checkValues(
        marshaller.unmarshall(object(
            string("strings"),
            array(string("f"), string("o"), string("o")))),
        4, null, strings);
  }

  private void checkValues(OptionalValues values,
      int intValue, Integer integer, List<String> strings) {
    assertEquals("intValue", intValue, values.intValue);
    assertEquals("integer", integer, values.integer);
    assertEquals("strings", strings, values.strings);
  }


}
