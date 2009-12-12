package com.twolattes.json.optional;

import static com.twolattes.json.Json.array;
import static com.twolattes.json.Json.number;
import static com.twolattes.json.Json.object;
import static com.twolattes.json.Json.string;
import static com.twolattes.json.TwoLattes.createMarshaller;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.twolattes.json.Email;
import com.twolattes.json.EmailInline;
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
        object(
            string("intValue"), number(4),
            string("intValueGs"), number(90)),
        marshaller.marshall(new OptionalValues()));
  }

  @Test
  public void marshall2() {
    assertEquals(
        object(
            string("intValue"), number(4),
            string("intValueGs"), number(90),
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
            string("intValueGs"), number(90),
            string("strings"), array()),
        marshaller.marshall(new OptionalValues() {{
          strings = new ArrayList<String>();
        }}));
  }

  @Test
  public void marshall4() {
    assertEquals(
        object(
            string("intValue"), number(4),
            string("intValueGs"), number(90),
            string("emailInlined"), string("Email:inline=true"),
            string("emailInline"), string("EmailInline"),
            string("stringsGs"), array()),
        marshaller.marshall(new OptionalValues() {{
          emailInlined = new Email("Email:inline=true");
          emailInline = new EmailInline("EmailInline");
          stringsGs = new ArrayList<String>();
        }}));
  }

  @Test
  public void marshall5() {
    assertEquals(
        object(
            string("intValue"), number(4),
            string("intValueGs"), number(90),
            string("emailInlinedGs"), string("Email:inline=true"),
            string("emailInlineGs"), string("EmailInline")),
        marshaller.marshall(new OptionalValues() {{
          emailInlinedGs = new Email("Email:inline=true");
          emailInlineGs = new EmailInline("EmailInline");
        }}));
  }

  @Test
  public void unmarshallDefault() {
    checkValues(
        marshaller.unmarshall(object()),
        4, null, null, null, null,
        90, null, null, null, null);
  }

  @Test
  public void unmarshallIntValue() {
    checkValues(
        marshaller.unmarshall(object(string("intValue"), number(6))),
        6, null, null, null, null,
        90, null, null, null, null);
  }

  @Test
  public void unmarshallIntValueGs() {
    checkValues(
        marshaller.unmarshall(object(string("intValueGs"), number(6))),
        4, null, null, null, null,
        6, null, null, null, null);
  }

  @Test
  public void unmarshallInteger() {
    checkValues(
        marshaller.unmarshall(object(string("integer"), number(6))),
        4, 6, null, null, null,
        90, null, null, null, null);
  }

  @Test
  public void unmarshallIntegerGs() {
    checkValues(
        marshaller.unmarshall(object(string("integerGs"), number(6))),
        4, null, null, null, null,
        90, 6, null, null, null);
  }

  @Test
  public void unmarshallString1() {
    checkValues(
        marshaller.unmarshall(object(string("strings"), array())),
        4, null, new ArrayList<String>(), null, null,
        90, null, null, null, null);
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
        4, null, strings, null, null,
        90, null, null, null, null);
  }

  @Test
  public void unmarshallStringGs1() {
    checkValues(
        marshaller.unmarshall(object(string("stringsGs"), array())),
        4, null, null, null, null,
        90, null, new ArrayList<String>(), null, null);
  }

  @Test
  public void unmarshallStringGs2() {
    ArrayList<String> strings = new ArrayList<String>();
    strings.add("f");
    strings.add("o");
    strings.add("o");
    checkValues(
        marshaller.unmarshall(object(
            string("stringsGs"),
            array(string("f"), string("o"), string("o")))),
        4, null, null, null, null,
        90, null, strings, null, null);
  }

  @Test
  public void unmarshallEmailInlined() {
    checkValues(
        marshaller.unmarshall(object(string("emailInlined"), string("f"))),
        4, null, null, "f", null,
        90, null, null, null, null);
  }

  @Test
  public void unmarshallEmailInline() {
    checkValues(
        marshaller.unmarshall(object(string("emailInline"), string("f"))),
        4, null, null, null, "f",
        90, null, null, null, null);
  }

  @Test
  public void unmarshallEmailInlinedGs() {
    checkValues(
        marshaller.unmarshall(object(string("emailInlinedGs"), string("f"))),
        4, null, null, null, null,
        90, null, null, "f", null);
  }

  @Test
  public void unmarshallEmailInlineGs() {
    checkValues(
        marshaller.unmarshall(object(string("emailInlineGs"), string("f"))),
        4, null, null, null, null,
        90, null, null, null, "f");
  }

  private void checkValues(OptionalValues values,
      int intValue, Integer integer, List<String> strings,
      String emailInlined, String emailInline,
      int intValueGs, Integer integerGs, List<String> stringsGs,
      String emailInlinedGs, String emailInlineGs) {
    assertEquals("intValue", intValue, values.intValue);
    assertEquals("integer", integer, values.integer);
    assertEquals("strings", strings, values.strings);
    if (emailInlined == null) {
      assertNull("emailInlined", values.emailInlined);
    } else {
      assertEquals("emailInlined", emailInlined, values.emailInlined.email);
    }
    if (emailInline == null) {
      assertNull("emailInline", values.emailInline);
    } else {
      assertEquals("emailInline", emailInline, values.emailInline.email);
    }
    assertEquals("intValueGs", intValueGs, values.intValueGs);
    assertEquals("integerGs", integerGs, values.integerGs);
    assertEquals("stringsGs", stringsGs, values.stringsGs);
    if (emailInlinedGs == null) {
      assertNull("emailInlinedGs", values.emailInlinedGs);
    } else {
      assertEquals("emailInlinedGs", emailInlinedGs, values.emailInlinedGs.email);
    }
    if (emailInlineGs == null) {
      assertNull("emailInlineGs", values.emailInlineGs);
    } else {
      assertEquals("emailInlineGs", emailInlineGs, values.emailInlineGs.email);
    }
  }

}
