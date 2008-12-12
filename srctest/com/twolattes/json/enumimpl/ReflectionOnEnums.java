package com.twolattes.json.enumimpl;

import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;

import org.junit.Test;

public class ReflectionOnEnums {

  enum Abc { A, B, C };

  @Test
  public void cannotInstantiateNewEnumValue() throws Exception {
    Constructor<?>[] constructors = Abc.class.getDeclaredConstructors();
    assertEquals(1, constructors.length);
    constructors[0].setAccessible(true);
    try {
      constructors[0].newInstance();
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot reflectively create enum objects", e.getMessage());
    }
  }

}
