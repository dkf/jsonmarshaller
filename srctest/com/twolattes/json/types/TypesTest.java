package com.twolattes.json.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.twolattes.json.Marshaller;

public class TypesTest {
  private Marshaller<E> marshaller;

  @Before
  public void start() {
    marshaller =  Marshaller.create(E.class);
  }
  
  @Test
  public void testMarshallURL() throws Exception {
    E e = new E();
    e.url = new  URL("http://whatever.com");
    JSONObject o = marshaller.marshall(e, "url");
    
    assertEquals(1, o.length());
    assertEquals("http://whatever.com", o.get("url"));
  }
  
  @Test
  public void testMarshallURLNull() throws Exception {
    JSONObject o = marshaller.marshall(new E(), "url");
    
    assertEquals(1, o.length());
    assertEquals(JSONObject.NULL, o.get("url"));
  }
  
  @Test
  public void testUnmarshallURL() throws Exception {
    E e = marshaller.unmarshall(
        new JSONObject("{url:'http://whatever.com'}"), "url");
    
    assertEquals(new URL("http://whatever.com"), e.url);
  }
  
  @Test
  public void testUnnarshallURLNull() throws Exception {
    E e = marshaller.unmarshall(new JSONObject("{url:null}"), "url");
    
    assertNull(e.url);
  }
  
  @Test
  public void testMarshallBigDecimal() throws Exception {
    E e = new E();
    e.bigDecimal = BigDecimal.valueOf(8.398741);
    JSONObject o = marshaller.marshall(e, "bigDecimal");
    
    assertEquals(1, o.length());
    assertEquals(8.398741, o.get("bigDecimal"));
  }
  
  @Test
  public void testMarshallBigDecimalNull() throws Exception {
    JSONObject o = marshaller.marshall(new E(), "bigDecimal");
    
    assertEquals(1, o.length());
    assertEquals(JSONObject.NULL, o.get("bigDecimal"));
  }
  
  @Test
  public void testUnmarshallBigDecimal1() throws Exception {
    E e = marshaller.unmarshall(
        new JSONObject("{bigDecimal:78945.13245}"), "bigDecimal");
    
    assertEquals(BigDecimal.valueOf(78945.13245), e.bigDecimal);
  }
  
  @Test
  public void testUnmarshallBigDecimal2() throws Exception {
    E e = marshaller.unmarshall(new JSONObject("{bigDecimal:7}"), "bigDecimal");
    
    assertEquals(BigDecimal.valueOf(7), e.bigDecimal);
  }
  
  @Test
  public void testUnmarshallBigDecimalNull() throws Exception {
    E e = marshaller.unmarshall(new JSONObject("{bigDecimal:null}"), "bigDecimal");
    
    assertNull(e.bigDecimal);
  }
  
  @Test
  public void testUnmarshallBigInteger1() throws Exception {
    E e = marshaller.unmarshall(
        new JSONObject("{bigInteger:null}"), "bigInteger");
    
    assertNull(e.bigInteger);
  }
  
  @Test
  public void testUnmarshallBigInteger2() throws Exception {
    E e = marshaller.unmarshall(
        new JSONObject("{bigInteger:78945}"), "bigInteger");
    
    assertTrue(
        BigInteger.valueOf(78945).compareTo(e.bigInteger) == 0);
  }
  
  @Test
  public void testUnmarshallBigInteger3() throws Exception {
    E e = marshaller.unmarshall(
        new JSONObject("{bigInteger:789451111111111111}"), "bigInteger");
    
    assertTrue(
        BigInteger.valueOf(789451111111111111L).compareTo(e.bigInteger) == 0);
  }
}
