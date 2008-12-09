package com.twolattes.json.visibility1;

import static com.twolattes.json.Json.number;
import static com.twolattes.json.Json.string;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.twolattes.json.Json;
import com.twolattes.json.TwoLattes;

public class Visibility1Test {
  @Test
  public void testPackagePrivateClassPackagePrivateConstuctorM() throws Exception {
    Json.Object o = TwoLattes
        .createMarshaller(PackagePrivateClassPackagePrivateConstuctor.class)
        .marshall(new PackagePrivateClassPackagePrivateConstuctor());

    assertEquals(1, o.size());
    assertEquals(number(9), o.get(string("value")));
  }

  @Test
  public void testPackagePrivateClassPrivateConstuctorM() throws Exception {
    Json.Object o = TwoLattes
        .createMarshaller(PackagePrivateClassPrivateConstuctor.class)
        .marshall(PackagePrivateClassPrivateConstuctor.create());

    assertEquals(1, o.size());
    assertEquals(number(9), o.get(string("value")));
  }

  @Test
  public void testPackagePrivateClassProtectedConstuctorM() throws Exception {
    Json.Object o = TwoLattes
        .createMarshaller(PackagePrivateClassProtectedConstuctor.class)
        .marshall(new PackagePrivateClassProtectedConstuctor());

    assertEquals(1, o.size());
    assertEquals(number(9), o.get(string("value")));
  }

  @Test
  public void testPublicClassPackagePrivateConstructorM() throws Exception {
    Json.Object o = TwoLattes
        .createMarshaller(PublicClassPackagePrivateConstructor.class)
        .marshall(new PublicClassPackagePrivateConstructor());

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
        .marshall(new PublicClassProtectedConstuctor());

    assertEquals(1, o.size());
    assertEquals(number(9), o.get(string("value")));
  }

  @Test
  public void testPackagePrivateClassPackagePrivateConstuctorU() throws Exception {
    PackagePrivateClassPackagePrivateConstuctor e = TwoLattes
        .createMarshaller(PackagePrivateClassPackagePrivateConstuctor.class)
        .unmarshall((Json.Object) Json.fromString("{\"value\":4}"));

    assertNotNull(e);
    assertEquals(4, e.value);
  }

  @Test
  public void testPackagePrivateClassPrivateConstuctorU() throws Exception {
    PackagePrivateClassPrivateConstuctor e = TwoLattes
        .createMarshaller(PackagePrivateClassPrivateConstuctor.class)
        .unmarshall((Json.Object) Json.fromString("{\"value\":4}"));

    assertNotNull(e);
    assertEquals(4, e.value);
  }

  @Test
  public void testPackagePrivateClassProtectedConstuctorU() throws Exception {
    PackagePrivateClassProtectedConstuctor e = TwoLattes
        .createMarshaller(PackagePrivateClassProtectedConstuctor.class)
        .unmarshall((Json.Object) Json.fromString("{\"value\":4}"));

    assertNotNull(e);
    assertEquals(4, e.value);
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
