package com.twolattes.json.inheritance1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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
public class Inheritance1Test {
  private Marshaller<A> marshaller;

  @Before
  public void start() {
    marshaller =  TwoLattes.createMarshaller(A.class);
  }

  @Test
  public void testMarshallB() throws Exception {
    B b = new B();
    b.name = "John";

    JSONObject o = marshaller.marshall(b);
    assertEquals(2, o.length());
    assertEquals("b", o.get("type"));
    assertEquals("John", o.get("name"));
  }

  @Test
  public void testMarshallC() throws Exception {
    C c = new C();
    c.age = 17;

    JSONObject o = marshaller.marshall(c);
    assertEquals(2, o.length());
    assertEquals("c", o.get("type"));
    assertEquals(17, o.get("age"));
  }

  @Test
  public void testMarshallList() throws Exception {
    B b = new B();
    b.name = "John";

    C c = new C();
    c.age = 17;

    List<A> list = new ArrayList<A>(2);
    list.add(b);
    list.add(c);

    JSONArray array = marshaller.marshallList(list);
    JSONObject o0 = array.getJSONObject(0);
    assertEquals(2, o0.length());
    assertEquals("b", o0.get("type"));
    assertEquals("John", o0.get("name"));
    JSONObject o1 = array.getJSONObject(1);
    assertEquals(2, o1.length());
    assertEquals("c", o1.get("type"));
    assertEquals(17, o1.get("age"));
  }

  @Test
  public void testUnmarshallB() throws Exception {
    A a = marshaller.unmarshall(new JSONObject("{type:'b', name:'John'}"));

    assertTrue(a instanceof B);
    B b = (B) a;
    assertEquals("John", b.name);
  }

  @Test
  public void testUnmarshallC() throws Exception {
    A a = marshaller.unmarshall(new JSONObject("{type:'c', age:5}"));

    assertTrue(a instanceof C);
    C c = (C) a;
    assertEquals(5, c.age);
  }

  @Test
  public void testUnmarshallList() throws Exception {
    List<A> list = marshaller.unmarshallList(new JSONArray(
        "[{type:'b', name:'John'},{type:'c', age:5}]"));

    A a = list.get(0);
    assertTrue(a instanceof B);
    B b = (B) a;
    assertEquals("John", b.name);

    a = list.get(1);
    assertTrue(a instanceof C);
    C c = (C) a;
    assertEquals(5, c.age);
  }
}
