package com.twolattes.json.visibility1;

import static com.twolattes.json.Json.number;
import static com.twolattes.json.Json.object;
import static com.twolattes.json.Json.string;
import static com.twolattes.json.TwoLattes.createEntityMarshaller;
import static com.twolattes.json.TwoLattes.createMarshaller;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.twolattes.json.Json;

public class Visibility1Test {
  @Test
  public void testPackagePrivateClassPackagePrivateConstuctorM() throws Exception {
    assertEquals(
        object(string("value"), number(9)),
        createEntityMarshaller(PackagePrivateClassPackagePrivateConstuctor.class)
          .marshall(new PackagePrivateClassPackagePrivateConstuctor()));
  }

  @Test
  public void testPackagePrivateClassPrivateConstuctorM() throws Exception {
    Json.Object o =
      createEntityMarshaller(PackagePrivateClassPrivateConstuctor.class)
        .marshall(PackagePrivateClassPrivateConstuctor.create());

    assertEquals(1, o.size());
    assertEquals(number(9), o.get(string("value")));
  }

  @Test
  public void testPackagePrivateClassProtectedConstuctorM() throws Exception {
    Json.Object o =
      createEntityMarshaller(PackagePrivateClassProtectedConstuctor.class)
        .marshall(new PackagePrivateClassProtectedConstuctor());

    assertEquals(1, o.size());
    assertEquals(number(9), o.get(string("value")));
  }

  @Test
  public void testPublicClassPackagePrivateConstructorM() throws Exception {
    Json.Object o =
      createEntityMarshaller(PublicClassPackagePrivateConstructor.class)
        .marshall(new PublicClassPackagePrivateConstructor());

    assertEquals(1, o.size());
    assertEquals(number(9), o.get(string("value")));
  }

  @Test
  public void testPublicClassPrivateConstructorM() throws Exception {
    Json.Object o = createEntityMarshaller(PublicClassPrivateConstructor.class)
        .marshall(PublicClassPrivateConstructor.create());

    assertEquals(1, o.size());
    assertEquals(number(9), o.get(string("value")));
  }

  @Test
  public void testPublicClassProtectedConstuctorM() throws Exception {
    Json.Object o = createEntityMarshaller(PublicClassProtectedConstuctor.class)
        .marshall(new PublicClassProtectedConstuctor());

    assertEquals(1, o.size());
    assertEquals(number(9), o.get(string("value")));
  }

  @Test
  public void testPackagePrivateClassPackagePrivateConstuctorU() throws Exception {
    PackagePrivateClassPackagePrivateConstuctor e =
      createMarshaller(PackagePrivateClassPackagePrivateConstuctor.class)
        .unmarshall(Json.fromString("{\"value\":4}"));

    assertNotNull(e);
    assertEquals(4, e.value);
  }

  @Test
  public void testPackagePrivateClassPrivateConstuctorU() throws Exception {
    PackagePrivateClassPrivateConstuctor e =
      createMarshaller(PackagePrivateClassPrivateConstuctor.class)
        .unmarshall(Json.fromString("{\"value\":4}"));

    assertNotNull(e);
    assertEquals(4, e.value);
  }

  @Test
  public void testPackagePrivateClassProtectedConstuctorU() throws Exception {
    PackagePrivateClassProtectedConstuctor e =
      createMarshaller(PackagePrivateClassProtectedConstuctor.class)
        .unmarshall(Json.fromString("{\"value\":4}"));

    assertNotNull(e);
    assertEquals(4, e.value);
  }

  @Test
  public void testPublicClassPackagePrivateConstructorU() throws Exception {
    PublicClassPackagePrivateConstructor e =
      createMarshaller(PublicClassPackagePrivateConstructor.class)
        .unmarshall(Json.fromString("{\"value\":4}"));

    assertNotNull(e);
    assertEquals(4, e.value);
  }

  @Test
  public void testPublicClassPrivateConstructorU() throws Exception {
    PublicClassPrivateConstructor e =
      createMarshaller(PublicClassPrivateConstructor.class)
        .unmarshall(Json.fromString("{\"value\":4}"));

    assertNotNull(e);
    assertEquals(4, e.value);
  }

  @Test
  public void testPublicClassProtectedConstuctorU() throws Exception {
    PublicClassProtectedConstuctor e =
      createMarshaller(PublicClassProtectedConstuctor.class)
        .unmarshall(Json.fromString("{\"value\":4}"));

    assertNotNull(e);
    assertEquals(4, e.value);
  }
}
