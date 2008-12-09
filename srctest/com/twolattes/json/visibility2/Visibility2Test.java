package com.twolattes.json.visibility2;

import static com.twolattes.json.Json.number;
import static com.twolattes.json.Json.string;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.twolattes.json.Json;
import com.twolattes.json.TwoLattes;
import com.twolattes.json.visibility1.PublicClassPackagePrivateConstructor;
import com.twolattes.json.visibility1.PublicClassPrivateConstructor;
import com.twolattes.json.visibility1.PublicClassProtectedConstuctor;

public class Visibility2Test {
  @Test
  public void testPublicClassPackagePrivateConstructorM() throws Exception {
    Json.Object o = TwoLattes
        .createMarshaller(PublicClassPackagePrivateConstructor.class)
        .marshall(PublicClassPackagePrivateConstructor.create());

    assertEquals(1, o.size());
    assertEquals(number(9), o.get(string("value")));
  }

  @Test
  public void testPublicClassPrivateConstructorM() throws Exception {
    Json.Object o = TwoLattes
        .createMarshaller(PublicClassPrivateConstructor.class)
        .marshall(PublicClassPrivateConstructor.create());

    assertEquals(1, o.size());
    assertEquals(number(9), o.get(string("value")));
  }

  @Test
  public void testPublicClassProtectedConstuctorM() throws Exception {
    Json.Object o = TwoLattes
        .createMarshaller(PublicClassProtectedConstuctor.class)
        .marshall(PublicClassProtectedConstuctor.create());

    assertEquals(1, o.size());
    assertEquals(number(9), o.get(string("value")));
  }

  @Test
  public void testPublicClassPackagePrivateConstructorU() throws Exception {
    PublicClassPackagePrivateConstructor e = TwoLattes
        .createMarshaller(PublicClassPackagePrivateConstructor.class)
        .unmarshall((Json.Object) Json.fromString("{\"value\":4}"));

    assertNotNull(e);
    assertEquals(4, e.value);
  }

  @Test
  public void testPublicClassPrivateConstructorU() throws Exception {
    PublicClassPrivateConstructor e = TwoLattes
        .createMarshaller(PublicClassPrivateConstructor.class)
        .unmarshall((Json.Object) Json.fromString("{\"value\":4}"));

    assertNotNull(e);
    assertEquals(4, e.value);
  }

  @Test
  public void testPublicClassProtectedConstuctorU() throws Exception {
    PublicClassProtectedConstuctor e = TwoLattes
        .createMarshaller(PublicClassProtectedConstuctor.class)
        .unmarshall((Json.Object) Json.fromString("{\"value\":4}"));

    assertNotNull(e);
    assertEquals(4, e.value);
  }
}
