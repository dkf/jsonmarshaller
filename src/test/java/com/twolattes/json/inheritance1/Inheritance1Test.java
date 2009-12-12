package com.twolattes.json.inheritance1;

import static com.twolattes.json.Json.number;
import static com.twolattes.json.Json.string;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.twolattes.json.EntityMarshaller;
import com.twolattes.json.Json;
import com.twolattes.json.TwoLattes;
import com.twolattes.json.Json.Object;

/**
 * Tests marshalling and unmarshalling for the inheritance of entities defined
 * in this package.
 * @author Pascal
 */
public class Inheritance1Test {
  private EntityMarshaller<A> marshaller;

  @Before
  public void start() {
    marshaller = TwoLattes.createEntityMarshaller(A.class);
  }

  @Test
  public void testMarshallB() throws Exception {
    B b = new B();
    b.name = "John";

    Json.Object o = marshaller.marshall(b);
    assertEquals(2, o.size());
    assertEquals(string("b"), o.get(string("type")));
    assertEquals(string("John"), o.get(string("name")));
  }

  @Test
  public void testMarshallC() throws Exception {
    C c = new C();
    c.age = 17;

    Json.Object o = marshaller.marshall(c);
    assertEquals(2, o.size());
    assertEquals(string("c"), o.get(string("type")));
    assertEquals(number(17), o.get(string("age")));
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

    Json.Array array = marshaller.marshallList(list);
    Json.Object o0 = (Object) array.get(0);
    assertEquals(2, o0.size());
    assertEquals(string("b"), o0.get(string("type")));
    assertEquals(string("John"), o0.get(string("name")));
    Json.Object o1 = (Json.Object) array.get(1);
    assertEquals(2, o1.size());
    assertEquals(string("c"), o1.get(string("type")));
    assertEquals(number(17), o1.get(string("age")));
  }

  @Test
  public void testUnmarshallB() throws Exception {
    A a = marshaller.unmarshall(
        Json.fromString("{\"type\":\"b\", \"name\":\"John\"}"));

    assertTrue(a instanceof B);
    B b = (B) a;
    assertEquals("John", b.name);
  }

  @Test
  public void testUnmarshallC() throws Exception {
    A a = marshaller.unmarshall(
        Json.fromString("{\"type\":\"c\", \"age\":5}"));

    assertTrue(a instanceof C);
    C c = (C) a;
    assertEquals(5, c.age);
  }

  @Test
  public void testUnmarshallList() throws Exception {
    List<A> list = marshaller.unmarshallList(
        (Json.Array) Json.fromString(
            "[{\"type\":\"b\", \"name\":\"John\"},{\"type\":\"c\", \"age\":5}]"));

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
