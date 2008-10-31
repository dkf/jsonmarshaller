package com.twolattes.json.inheritance2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.twolattes.json.Marshaller;
import com.twolattes.json.TwoLattes;

/**
 * Tests marshalling and unmarshalling for the inheritance of entities defined
 * in this package.
 * @author Pascal
 */
public class Inheritance2Test {
  private Marshaller<A> marshaller;

  @Before
  public void start() {
    marshaller =  TwoLattes.createMarshaller(A.class);
  }

  @Test
  public void testMarshallA() throws Exception {
    A a = new A();
    a.age = 17;

    JSONObject o = marshaller.marshall(a);
    assertEquals(2, o.length());
    assertEquals("a", o.get("type"));
    assertEquals(17, o.get("age"));
  }

  @Test
  public void testMarshallB() throws Exception {
    B b = new B();
    b.name = "John";
    b.age = 83;

    JSONObject o = marshaller.marshall(b);
    assertEquals(3, o.length());
    assertEquals("b", o.get("type"));
    assertEquals("John", o.get("name"));
    assertEquals(83, o.get("age"));
  }

  @Test
  public void testUnmarshallA() throws Exception {
    A a = marshaller.unmarshall(new JSONObject("{type:'a', age:5}"));

    assertEquals(5, a.age);
  }

  @Test
  public void testUnmarshallB() throws Exception {
    A a = marshaller.unmarshall(new JSONObject(
        "{type:'b', age: 24, name:'John'}"));

    assertTrue(a instanceof B);
    B b = (B) a;
    assertEquals("John", b.name);
    assertEquals(24, b.age);
  }
}
