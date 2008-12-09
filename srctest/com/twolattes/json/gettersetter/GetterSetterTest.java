package com.twolattes.json.gettersetter;

import static com.twolattes.json.Json.object;
import static com.twolattes.json.Json.string;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Ignore;
import org.junit.Test;

import com.twolattes.json.Email;
import com.twolattes.json.Json;
import com.twolattes.json.TwoLattes;

public class GetterSetterTest {
  @Test
  public void getterSetter1Marshall() throws Exception {
    // entity
    GetterSetter1 e = new GetterSetter1();
    Email email = new Email();
    email.email = "any";
    e.setEmail(email);

    // marshalling
    Json.Object o = TwoLattes.createMarshaller(GetterSetter1.class).marshall(e);

    // assertions
    assertEquals(1, o.size());
    assertEquals(string("any"), o.get(string("email")));
  }

  @Test
  public void getterSetter1Unmarshall() throws Exception {
    // unmarshalling
    GetterSetter1 e = TwoLattes.createMarshaller(GetterSetter1.class).unmarshall(
        (Json.Object) Json.fromString("{\"email\":\"any\"}"));

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
    Json.Object o1 = TwoLattes.createMarshaller(GetterSetter2.class).marshall(e);

    // assertions
    assertEquals(1, o1.size());
    Json.Object o2 = (Json.Object) o1.get(string("foobar"));
    assertEquals(1, o2.size());
    assertEquals(string("any"), o2.get(string("email")));
  }

  @Test
  public void getterSetter2Unmarshall() throws Exception {
    // unmarshalling
    GetterSetter2 e = TwoLattes.createMarshaller(GetterSetter2.class).unmarshall(
        (Json.Object) Json.fromString("{\"foobar\":{\"email\":\"any\"}}"));

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
    Json.Object o = TwoLattes.createMarshaller(GetterSetter3.class).marshall(e);

    // assertions
    assertEquals(0, o.size());
  }

  @Test
  public void getterSetter3Unmarshall() throws Exception {
    // unmarshalling
    GetterSetter3 e = TwoLattes.createMarshaller(GetterSetter3.class).unmarshall(
        object());

    // assertions
    assertNotNull(e);
    assertNull(e.getEmail());
  }

  @Test
  @Ignore("TODO(pascal): implement.")
  public void getterSetter4Marshall() throws Exception {
    // entity
    GetterSetter4 e = new GetterSetter4();

    // marshalling
    Json.Object o = TwoLattes.createMarshaller(GetterSetter4.class).marshall(e);

    // assertions
    assertEquals(string("foobar"), o.get(string("email")));
    assertEquals(1, o.size());
  }

  @Test
  @Ignore("TODO(pascal): implement.")
  public void getterSetter4Unmarshall() throws Exception {
    // unmarshalling
    GetterSetter4 e = TwoLattes.createMarshaller(GetterSetter4.class)
        .unmarshall(object());

    // assertions
    assertNotNull(e);
    assertNull(e.data);
  }
}
