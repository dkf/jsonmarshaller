package com.twolattes.json;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URL;
import java.util.Set;

import org.junit.Test;

import com.twolattes.json.types.NullSafeType;
import com.twolattes.json.types.URIType;
import com.twolattes.json.types.URLType;

@SuppressWarnings("unchecked")
public class UserTypeDescriptorTest {

  @Test
  public void getReturnedClass1() throws Exception {
    assertEquals(
        URL.class,
        new UserTypeDescriptor(new URLType()).getReturnedClass());
  }

  @Test
  public void getReturnedClass2() throws Exception {
    assertEquals(
        URI.class,
        new UserTypeDescriptor(new URIType()).getReturnedClass());
  }

  @Test
  public void getReturnedClass3() throws Exception {
    assertEquals(
        Set.class,
        new UserTypeDescriptor(new SetType()).getReturnedClass());
  }

  @Test
  public void getReturnedClass4() throws Exception {
    assertEquals(
        Array.class,
        new UserTypeDescriptor(new IntegerArrayType()).getReturnedClass());
  }

  @Test
  public void getReturnedClass5() throws Exception {
    assertEquals(
        Array.class,
        new UserTypeDescriptor(new IntArrayType()).getReturnedClass());
  }

  static class SetType extends NullSafeType<Set<?>, Json.String> {
    @Override protected Json.String nullSafeMarshall(Set<?> entity) { return null; }
    @Override protected Set<?> nullSafeUnmarshall(Json.String object) { return null; }
  }

  static class IntegerArrayType extends NullSafeType<Integer[], Json.String> {
    @Override protected Json.String nullSafeMarshall(Integer[] entity) { return null; }
    @Override protected Integer[] nullSafeUnmarshall(Json.String object) { return null; }
  }

  static class IntArrayType extends NullSafeType<int[], Json.String> {
    @Override protected Json.String nullSafeMarshall(int[] entity) { return null; }
    @Override protected int[] nullSafeUnmarshall(Json.String object) { return null; }
  }

}
