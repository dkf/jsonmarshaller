package com.twolattes.json;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Type;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class EntityClassVisitorTest {
  private EntityClassVisitor visitor;

  @Before
  public void start() {
    visitor = new EntityClassVisitor(
        EntityInterface.class, null, false, new HashMap<Type, Class<?>>());
  }

  @Test
  public void testIsGetter() {
    assertTrue(visitor.isGetterName("getFoo"));
    assertTrue(visitor.isGetterName("getFooBar"));
    assertTrue(visitor.isGetterName("isNice"));

    assertFalse(visitor.isGetterName("getgoo"));
    assertFalse(visitor.isGetterName("fooBar"));
    assertFalse(visitor.isGetterName("isice"));
  }

  @Test
  public void testIsSetterSignature() throws Exception {
    assertTrue(visitor.isSetterSignature("(Z)V"));
    assertTrue(visitor.isSetterSignature("(Ljava/lang/String;)V"));

    assertFalse(visitor.isSetterSignature("(Z)I"));
    assertFalse(visitor.isSetterSignature("()V"));
  }
}
