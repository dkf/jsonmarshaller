package com.twolattes.json;

import static java.lang.String.format;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class Instantiator {

  static <T> T newInstance(Class<T> clazz) {
    try {
      Constructor<T> constructor = clazz.getDeclaredConstructor();
      constructor.setAccessible(true);
      return constructor.newInstance();
    } catch (SecurityException e) {
      throw new RuntimeException(
          format("unable to access %s's constructor", clazz), e);
    } catch (NoSuchMethodException e) {
      throw new IllegalArgumentException(
          format("%s is missing a no-argument constructor", clazz), e);
    } catch (InstantiationException e) {
      throw new RuntimeException(
          format("could not instantiate %s", clazz), e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(
          format("cannot access %s", clazz), e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(
          format("exception occured when instantiating %s", clazz), e);
    }
  }

}
