package com.twolattes.json.inheritance3;

import static com.twolattes.json.Json.string;
import static com.twolattes.json.TwoLattes.createEntityMarshaller;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.twolattes.json.EntityMarshaller;
import com.twolattes.json.Json;

/**
 * Tests marshalling and unmarshalling for the inheritance of entities defined
 * in this package.
 * @author Pascal
 */
public class Inheritance3Test {
  private EntityMarshaller<A> marshaller;

  @Before
  public void start() {
    marshaller =  createEntityMarshaller(A.class);
  }

  @Test
  public void testMarshallC1() throws Exception {
    Json.Object o = marshaller.marshall(new C1());
    assertEquals(4, o.size());
    assertEquals(string("1"), o.get(string("discriminator")));
    assertEquals(string("class A"), o.get(string("a")));
    assertEquals(string("class B"), o.get(string("b")));
    assertEquals(string("class C1"), o.get(string("c1")));
  }

  @Test
  public void testMarshallC2() throws Exception {
    Json.Object o = marshaller.marshall(new C2());
    assertEquals(4, o.size());
    assertEquals(string("2"), o.get(string("discriminator")));
    assertEquals(string("class A"), o.get(string("a")));
    assertEquals(string("class B"), o.get(string("b")));
    assertEquals(string("class C2"), o.get(string("c2")));
  }

  @Test
  public void testUnmarshallC1() throws Exception {
    A a = marshaller.unmarshall(Json.fromString(
        "{\"discriminator\":\"1\", \"a\":\"changed A\", \"b\":\"changed B\", \"c1\":\"changed C1\"}"));

    assertTrue(a instanceof C1);
    C1 c1 = (C1) a;
    assertEquals("changed A", c1.a);
    assertEquals("changed B", c1.b);
    assertEquals("changed C1", c1.c1);
  }

  @Test
  public void testUnmarshallC2() throws Exception {
    A a = marshaller.unmarshall(Json.fromString(
        "{\"discriminator\":\"2\", \"a\":\"changed A\", \"b\":\"changed B\", \"c2\":\"changed C2\"}"));

    assertTrue(a instanceof C2);
    C2 c2 = (C2) a;
    assertEquals("changed A", c2.a);
    assertEquals("changed B", c2.b);
    assertEquals("changed C2", c2.c2);
  }
}
