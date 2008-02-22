package com.twolattes.json.visibility1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.json.JSONObject;
import org.junit.Test;

import com.twolattes.json.Marshaller;

public class Visibility1Test {
  @Test
  public void testPackagePrivateClassPackagePrivateConstuctorM() throws Exception {
    JSONObject o = Marshaller
        .create(PackagePrivateClassPackagePrivateConstuctor.class)
        .marshall(new PackagePrivateClassPackagePrivateConstuctor());
    
    assertEquals(1, o.length());
    assertEquals(9, o.get("value"));
  }
  
  @Test
  public void testPackagePrivateClassPrivateConstuctorM() throws Exception {
    JSONObject o = Marshaller
        .create(PackagePrivateClassPrivateConstuctor.class)
        .marshall(PackagePrivateClassPrivateConstuctor.create());
    
    assertEquals(1, o.length());
    assertEquals(9, o.get("value"));
  }
  
  @Test
  public void testPackagePrivateClassProtectedConstuctorM() throws Exception {
    JSONObject o = Marshaller
        .create(PackagePrivateClassProtectedConstuctor.class)
        .marshall(new PackagePrivateClassProtectedConstuctor());
    
    assertEquals(1, o.length());
    assertEquals(9, o.get("value"));
  }
  
  @Test
  public void testPublicClassPackagePrivateConstructorM() throws Exception {
    JSONObject o = Marshaller
        .create(PublicClassPackagePrivateConstructor.class)
        .marshall(new PublicClassPackagePrivateConstructor());
    
    assertEquals(1, o.length());
    assertEquals(9, o.get("value"));
  }
  
  @Test
  public void testPublicClassPrivateConstructorM() throws Exception {
    JSONObject o = Marshaller
        .create(PublicClassPrivateConstructor.class)
        .marshall(PublicClassPrivateConstructor.create());
    
    assertEquals(1, o.length());
    assertEquals(9, o.get("value"));
  }
  
  @Test
  public void testPublicClassProtectedConstuctorM() throws Exception {
    JSONObject o = Marshaller
        .create(PublicClassProtectedConstuctor.class)
        .marshall(new PublicClassProtectedConstuctor());
    
    assertEquals(1, o.length());
    assertEquals(9, o.get("value"));
  }

  @Test
  public void testPackagePrivateClassPackagePrivateConstuctorU() throws Exception {
    PackagePrivateClassPackagePrivateConstuctor e = Marshaller
        .create(PackagePrivateClassPackagePrivateConstuctor.class)
        .unmarshall(new JSONObject("{value:4}"));
    
    assertNotNull(e);
    assertEquals(4, e.value);
  }

  @Test
  public void testPackagePrivateClassPrivateConstuctorU() throws Exception {
    PackagePrivateClassPrivateConstuctor e = Marshaller
        .create(PackagePrivateClassPrivateConstuctor.class)
        .unmarshall(new JSONObject("{value:4}"));
    
    assertNotNull(e);
    assertEquals(4, e.value);
  }

  @Test
  public void testPackagePrivateClassProtectedConstuctorU() throws Exception {
    PackagePrivateClassProtectedConstuctor e = Marshaller
        .create(PackagePrivateClassProtectedConstuctor.class)
        .unmarshall(new JSONObject("{value:4}"));
    
    assertNotNull(e);
    assertEquals(4, e.value);
  }

  @Test
  public void testPublicClassPackagePrivateConstructorU() throws Exception {
    PublicClassPackagePrivateConstructor e = Marshaller
        .create(PublicClassPackagePrivateConstructor.class)
        .unmarshall(new JSONObject("{value:4}"));
    
    assertNotNull(e);
    assertEquals(4, e.value);
  }

  @Test
  public void testPublicClassPrivateConstructorU() throws Exception {
    PublicClassPrivateConstructor e = Marshaller
        .create(PublicClassPrivateConstructor.class)
        .unmarshall(new JSONObject("{value:4}"));
    
    assertNotNull(e);
    assertEquals(4, e.value);
  }

  @Test
  public void testPublicClassProtectedConstuctorU() throws Exception {
    PublicClassProtectedConstuctor e = Marshaller
        .create(PublicClassProtectedConstuctor.class)
        .unmarshall(new JSONObject("{value:4}"));
    
    assertNotNull(e);
    assertEquals(4, e.value);
  }
}
