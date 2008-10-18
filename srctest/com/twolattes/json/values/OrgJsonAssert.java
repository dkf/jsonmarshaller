package com.twolattes.json.values;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.Map;

import junit.framework.AssertionFailedError;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.twolattes.json.values.Json.Array;
import com.twolattes.json.values.Json.Boolean;
import com.twolattes.json.values.Json.Number;
import com.twolattes.json.values.Json.String;

public class OrgJsonAssert {

  public static void assertJsonEquals(Json json1, final Object json2) {
    json1.visit(new JsonVisitor<Void>() {
      @Override
      public Void caseArray(Array array) {
        assertTrue(json2 instanceof JSONArray);
        JSONArray jsonArray = (JSONArray) json2;
        assertEquals(array.size(), jsonArray.length());
        for (int i = 0; i < array.size(); i++) {
          assertJsonEquals(array.get(i), jsonArray.get(i));
        }
        return null;
      }

      @Override
      public Void caseBoolean(Boolean b) {
        assertTrue(json2 instanceof java.lang.Boolean);
        assertFalse(b.get() ^ ((java.lang.Boolean) json2));
        return null;
      }

      @Override
      public Void caseNull() {
        assertTrue(json2.equals(JSONObject.NULL) || json2.equals(JSONArray.NULL));
        return null;
      }

      @Override
      public Void caseNumber(Number number) {
        assertTrue(json2 instanceof java.lang.Number);
        assertEquals(number.get().doubleValue(), ((java.lang.Number) json2).doubleValue());
        return null;
      }

      @Override
      public Void caseObject(Json.Object jsonObject1) {
        JSONObject jsonObject2 = (JSONObject) json2;
        assertEquals("size", jsonObject1.size(), jsonObject2.length());
        for (Map.Entry<Json.String, Json> e : jsonObject1.entrySet()) {
          assertTrue("has key <" + e.getKey().get() + ">", jsonObject2.has(e.getKey().get()));
          assertJsonEquals(e.getValue(), jsonObject2.get(e.getKey().get()));
        }
        return null;
      }

      @Override
      public Void caseString(String string) {
        assertTrue(json2 instanceof java.lang.String);
        assertEquals(string.get(), json2);
        return null;
      }
    });
  }

  @Test
  public void orgJsonCompare1() throws Exception {
    assertJsonEquals(new Json.Object(), new JSONObject("{}"));
  }

  @Test(expected = AssertionFailedError.class)
  public void orgJsonCompare2() throws Exception {
    Json.Object o1 = new Json.Object();
    o1.put(new Json.String("a"), Json.NULL);
    assertJsonEquals(o1, new JSONObject("{}"));
  }

  @Test(expected = AssertionFailedError.class)
  public void orgJsonCompare3() throws Exception {
    Json.Object o1 = new Json.Object();
    o1.put(new Json.String("a"), Json.NULL);
    assertJsonEquals(o1, new JSONObject("{\"a\":4}"));
  }

  @Test
  public void orgJsonCompare4() throws Exception {
    Json.Object o1 = new Json.Object();
    o1.put(new Json.String("a"), Json.NULL);
    assertJsonEquals(o1, new JSONObject("{\"a\":null}"));
  }

  @Test
  public void orgJsonCompare5() throws Exception {
    Json.Object o1 = new Json.Object();
    o1.put(new Json.String("a"), Json.TRUE);
    assertJsonEquals(o1, new JSONObject("{\"a\":true}"));
  }

  @Test(expected = AssertionFailedError.class)
  public void orgJsonCompare6() throws Exception {
    Json.Object o1 = new Json.Object();
    o1.put(new Json.String("a"), Json.FALSE);
    assertJsonEquals(o1, new JSONObject("{\"a\":true}"));
  }

  @Test
  public void orgJsonCompare7() throws Exception {
    Json.Object o1 = new Json.Object();
    o1.put(new Json.String("a"), new Json.String("wow"));
    assertJsonEquals(o1, new JSONObject("{\"a\":'wow'}"));
  }

  @Test(expected = AssertionFailedError.class)
  public void orgJsonCompare8() throws Exception {
    Json.Object o1 = new Json.Object();
    o1.put(new Json.String("a"), new Json.String("wow"));
    assertJsonEquals(o1, new JSONObject("{\"a\":'waw'}"));
  }

  @Test
  public void orgJsonCompare9() throws Exception {
    Json.Object o1 = new Json.Object();
    o1.put(new Json.String("a"), new Json.Number(4));
    assertJsonEquals(o1, new JSONObject("{\"a\":4}"));
  }

  @Test(expected = AssertionFailedError.class)
  public void orgJsonCompare10() throws Exception {
    Json.Object o1 = new Json.Object();
    o1.put(new Json.String("a"), new Json.Number(4));
    assertJsonEquals(o1, new JSONObject("{\"a\":4.1}"));
  }

  @Test(expected = AssertionFailedError.class)
  public void orgJsonCompare11() throws Exception {
    Json.Object o1 = new Json.Object();
    o1.put(new Json.String("a"), new Json.Number(4));
    assertJsonEquals(o1, new JSONObject("{\"b\":4}"));
  }

  @Test
  public void orgJsonCompare12() throws Exception {
    assertJsonEquals(new Json.Array(), new JSONArray("[]"));
  }

  @Test
  public void orgJsonCompare13() throws Exception {
    Json.Array array = new Json.Array();
    array.add(Json.TRUE);
    assertJsonEquals(array, new JSONArray("[true]"));
  }

  @Test(expected = AssertionFailedError.class)
  public void orgJsonCompare14() throws Exception {
    Json.Array array = new Json.Array();
    array.add(Json.TRUE);
    assertJsonEquals(array, new JSONArray("[false]"));
  }

  @Test(expected = AssertionFailedError.class)
  public void orgJsonCompare15() throws Exception {
    Json.Array array = new Json.Array();
    array.add(Json.TRUE);
    assertJsonEquals(array, new JSONArray("[true,8]"));
  }

}
