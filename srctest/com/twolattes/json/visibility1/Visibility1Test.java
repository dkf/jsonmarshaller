package com.twolattes.json.visibility1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.json.JSONObject;
import org.junit.Test;

import com.twolattes.json.TwoLattes;

public class Visibility1Test {
  @Test
  public void testPackagePrivateClassPackagePrivateConstuctorM() throws Exception {
    JSONObject o = TwoLattes
        .createMarshaller(PackagePrivateClassPackagePrivateConstuctor.class)
        .marshall(new PackagePrivateClassPackagePrivateConstuctor());

    assertEquals(1, o.length());
    assertEquals(9, o.get("value"));
  }

  @Test
  public void testPackagePrivateClassPrivateConstuctorM() throws Exception {
    JSONObject o = TwoLattes
        .createMarshaller(PackagePrivateClassPrivateConstuctor.class)
        .marshall(PackagePrivateClassPrivateConstuctor.create());

    assertEquals(1, o.length());
    assertEquals(9, o.get("value"));
  }

  @Test
  public void testPackagePrivateClassProtectedConstuctorM() throws Exception {
    JSONObject o = TwoLattes
        .createMarshaller(PackagePrivateClassProtectedConstuctor.class)
        .marshall(new PackagePrivateClassProtectedConstuctor());

    assertEquals(1, o.length());
    assertEquals(9, o.get("value"));
  }

  @Test
  public void testPublicClassPackagePrivateConstructorM() throws Exception {
    JSONObject o = TwoLattes
        .createMarshaller(PublicClassPackagePrivateConstructor.class)
        .marshall(new PublicClassPackagePrivateConstructor());

    assertEquals(1, o.length());
    assertEquals(9, o.get("value"));
  }

  @Test
  public void testPublicClassPrivateConstructorM() throws Exception {
    JSONObject o = TwoLattes
        .createMarshaller(PublicClassPrivateConstructor.class)
        .marshall(PublicClassPrivateConstructor.create());

    assertEquals(1, o.length());
    assertEquals(9, o.get("value"));
  }

  @Test
  public void testPublicClassProtectedConstuctorM() throws Exception {
    JSONObject o = TwoLattes
        .createMarshaller(PublicClassProtectedConstuctor.class)
        .marshall(new PublicClassProtectedConstuctor());

    assertEquals(1, o.length());
    assertEquals(9, o.get("value"));
  }

  @Test
  public void testPackagePrivateClassPackagePrivateConstuctorU() throws Exception {
    PackagePrivateClassPackagePrivateConstuctor e = TwoLattes
        .createMarshaller(PackagePrivateClassPackagePrivateConstuctor.class)
        .unmarshall(new JSONObject("{value:4}"));

    assertNotNull(e);
    assertEquals(4, e.value);
  }

  @Test
  public void testPackagePrivateClassPrivateConstuctorU() throws Exception {
    PackagePrivateClassPrivateConstuctor e = TwoLattes
        .createMarshaller(PackagePrivateClassPrivateConstuctor.class)
        .unmarshall(new JSONObject("{value:4}"));

    assertNotNull(e);
    assertEquals(4, e.value);
  }

  @Test
  public void testPackagePrivateClassProtectedConstuctorU() throws Exception {
    PackagePrivateClassProtectedConstuctor e = TwoLattes
        .createMarshaller(PackagePrivateClassProtectedConstuctor.class)
        .unmarshall(new JSONObject("{value:4}"));

    assertNotNull(e);
    assertEquals(4, e.value);
  }

  @Test
  public void testPublicClassPackagePrivateConstructorU() throws Exception {
    PublicClassPackagePrivateConstructor e = TwoLattes
        .createMarshaller(PublicClassPackagePrivateConstructor.class)
        .unmarshall(new JSONObject("{value:4}"));

    assertNotNull(e);
    assertEquals(4, e.value);
  }

  @Test
  public void testPublicClassPrivateConstructorU() throws Exception {
    PublicClassPrivateConstructor e = TwoLattes
        .createMarshaller(PublicClassPrivateConstructor.class)
        .unmarshall(new JSONObject("{value:4}"));

    assertNotNull(e);
    assertEquals(4, e.value);
  }

  @Test
  public void testPublicClassProtectedConstuctorU() throws Exception {
    PublicClassProtectedConstuctor e = TwoLattes
        .createMarshaller(PublicClassProtectedConstuctor.class)
        .unmarshall(new JSONObject("{value:4}"));

    assertNotNull(e);
    assertEquals(4, e.value);
  }
}
