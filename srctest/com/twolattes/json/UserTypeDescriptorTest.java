package com.twolattes.json;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URL;

import org.junit.Test;

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

}
