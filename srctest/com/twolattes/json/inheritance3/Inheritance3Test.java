package com.twolattes.json.inheritance3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.twolattes.json.Marshaller;

/**
 * Tests marshalling and unmarshalling for the inheritance of entities defined
 * in this package.
 * @author Pascal
 */
public class Inheritance3Test {
  private Marshaller<A> marshaller;

  @Before
  public void start() {
    marshaller =  Marshaller.create(A.class);
  }

  @Test
  public void testMarshallC1() throws Exception {
    JSONObject o = marshaller.marshall(new C1());
    assertEquals(4, o.length());
    assertEquals("1", o.get("discriminator"));
    assertEquals("class A", o.get("a"));
    assertEquals("class B", o.get("b"));
    assertEquals("class C1", o.get("c1"));
  }

  @Test
  public void testMarshallC2() throws Exception {
    JSONObject o = marshaller.marshall(new C2());
    assertEquals(4, o.length());
    assertEquals("2", o.get("discriminator"));
    assertEquals("class A", o.get("a"));
    assertEquals("class B", o.get("b"));
    assertEquals("class C2", o.get("c2"));
  }
  
  @Test
  public void testUnmarshallC1() throws Exception {
    A a = marshaller.unmarshall(new JSONObject(
        "{discriminator:'1', a:'changed A', b:'changed B', c1:'changed C1'}"));
    
    assertTrue(a instanceof C1);
    C1 c1 = (C1) a;
    assertEquals("changed A", c1.a);
    assertEquals("changed B", c1.b);
    assertEquals("changed C1", c1.c1);
  }
  
  @Test
  public void testUnmarshallC2() throws Exception {
    A a = marshaller.unmarshall(new JSONObject(
        "{discriminator:'2', a:'changed A', b:'changed B', c2:'changed C2'}"));
    
    assertTrue(a instanceof C2);
    C2 c2 = (C2) a;
    assertEquals("changed A", c2.a);
    assertEquals("changed B", c2.b);
    assertEquals("changed C2", c2.c2);
  }
}
