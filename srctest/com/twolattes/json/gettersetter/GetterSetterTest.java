package com.twolattes.json.gettersetter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.json.JSONObject;
import org.junit.Test;

import com.twolattes.json.Email;
import com.twolattes.json.Marshaller;

public class GetterSetterTest {
  @Test
  public void getterSetter1Marshall() throws Exception {
    // entity
    GetterSetter1 e = new GetterSetter1();
    Email email = new Email();
    email.email = "any";
    e.setEmail(email);

    // marshalling
    JSONObject o = Marshaller.create(GetterSetter1.class).marshall(e);

    // assertions
    assertEquals(1, o.length());
    assertEquals("any", o.get("email"));
  }

  @Test
  public void getterSetter1Unmarshall() throws Exception {
    // unmarshalling
    GetterSetter1 e = Marshaller.create(GetterSetter1.class).unmarshall(
        new JSONObject("{email:'any'}"));

    // assertions
    assertNotNull(e);
    Email email = e.getEmail();
    assertNotNull(email);
    assertEquals("any", email.email);
  }

  @Test
  public void getterSetter2Marshall() throws Exception {
    // entity
    GetterSetter2 e = new GetterSetter2();
    Email email = new Email();
    email.email = "any";
    e.setEmail(email);

    // marshalling
    JSONObject o1 = Marshaller.create(GetterSetter2.class).marshall(e);

    // assertions
    assertEquals(1, o1.length());
    JSONObject o2 = o1.getJSONObject("foobar");
    assertEquals(1, o2.length());
    assertEquals("any", o2.get("email"));
  }

  @Test
  public void getterSetter2Unmarshall() throws Exception {
    // unmarshalling
    GetterSetter2 e = Marshaller.create(GetterSetter2.class).unmarshall(
        new JSONObject("{foobar:{email:'any'}}"));

    // assertions
    assertNotNull(e);
    Email email = e.getEmail();
    assertNotNull(email);
    assertEquals("any", email.email);
  }

  @Test
  public void getterSetter3Marshall() throws Exception {
    // entity
    GetterSetter3 e = new GetterSetter3();

    // marshalling
    JSONObject o = Marshaller.create(GetterSetter3.class).marshall(e);

    // assertions
    assertEquals(0, o.length());
  }

  @Test
  public void getterSetter3Unmarshall() throws Exception {
    // unmarshalling
    GetterSetter3 e = Marshaller.create(GetterSetter3.class).unmarshall(
        new JSONObject("{}"));

    // assertions
    assertNotNull(e);
    assertNull(e.getEmail());
  }
}
