package com.twolattes.json.gettersetter;

import static com.twolattes.json.Json.object;
import static com.twolattes.json.Json.string;
import static com.twolattes.json.TwoLattes.createEntityMarshaller;
import static com.twolattes.json.TwoLattes.createMarshaller;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.net.URL;

import org.junit.Test;

import com.twolattes.json.Email;
import com.twolattes.json.Json;

public class GetterSetterTest {

  @Test
  public void getterSetter1Marshall() throws Exception {
    // entity
    GetterSetter1 e = new GetterSetter1();
    Email email = new Email();
    email.email = "any";
    e.setEmail(email);

    // marshalling
    Json.Object o = createEntityMarshaller(GetterSetter1.class).marshall(e);

    // assertions
    assertEquals(1, o.size());
    assertEquals(string("any"), o.get(string("email")));
  }

  @Test
  public void getterSetter1Unmarshall() throws Exception {
    // unmarshalling
    GetterSetter1 e = createMarshaller(GetterSetter1.class)
      .unmarshall(Json.fromString("{\"email\":\"any\"}"));

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
    Json.Object o1 = createEntityMarshaller(GetterSetter2.class).marshall(e);

    // assertions
    assertEquals(1, o1.size());
    Json.Object o2 = (Json.Object) o1.get(string("foobar"));
    assertEquals(1, o2.size());
    assertEquals(string("any"), o2.get(string("email")));
  }

  @Test
  public void getterSetter2Unmarshall() throws Exception {
    // unmarshalling
    GetterSetter2 e = createMarshaller(GetterSetter2.class)
      .unmarshall(Json.fromString("{\"foobar\":{\"email\":\"any\"}}"));

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
    Json.Object o = createEntityMarshaller(GetterSetter3.class).marshall(e);

    // assertions
    assertEquals(0, o.size());
  }

  @Test
  public void getterSetter3Unmarshall() throws Exception {
    // unmarshalling
    GetterSetter3 e = createMarshaller(GetterSetter3.class).unmarshall(object());

    // assertions
    assertNotNull(e);
    assertNull(e.getEmail());
  }

  @Test
  public void getterSetter4Marshall() throws Exception {
    // entity
    GetterSetter4 e = new GetterSetter4();
    e.setData(new URL("http://b.org"));

    // marshalling
    Json.Object o = createEntityMarshaller(GetterSetter4.class).marshall(e);

    // assertions
    assertEquals(string("http://b.org"), o.get(string("data")));
    assertEquals(1, o.size());
  }

  @Test
  public void getterSetter4Unmarshall() throws Exception {
    // unmarshalling
    GetterSetter4 e = createMarshaller(GetterSetter4.class)
      .unmarshall(Json.fromString("{\"data\":\"http://a.com\"}"));

    // assertions
    assertNotNull(e);
    assertEquals(new URL("http://a.com"), e.data);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getterSetter5() throws Exception {
    createMarshaller(GetterSetter5.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getterSetter6() throws Exception {
    createMarshaller(GetterSetter6.class);
  }

}
