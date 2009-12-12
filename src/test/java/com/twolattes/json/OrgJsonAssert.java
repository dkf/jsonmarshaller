package com.twolattes.json;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.Map;

import junit.framework.AssertionFailedError;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

public class OrgJsonAssert {

  public static void assertJsonEquals(Json.Value json1, final Object json2) {
    json1.visit(new JsonVisitor<Void>() {
      public Void caseArray(Json.Array array) {
        assertTrue(json2 instanceof JSONArray);
        JSONArray jsonArray = (JSONArray) json2;
        assertEquals(array.size(), jsonArray.length());
        for (int i = 0; i < array.size(); i++) {
          assertJsonEquals(array.get(i), jsonArray.get(i));
        }
        return null;
      }

      public Void caseBoolean(Json.Boolean b) {
        assertTrue(json2 instanceof java.lang.Boolean);
        assertFalse(b.getBoolean() ^ ((java.lang.Boolean) json2));
        return null;
      }

      public Void caseNull() {
        assertTrue(json2.equals(JSONObject.NULL) || json2.equals(JSONArray.NULL));
        return null;
      }

      public Void caseNumber(Json.Number number) {
        assertTrue(json2 instanceof java.lang.Number);
        assertEquals(number.getNumber().doubleValue(), ((java.lang.Number) json2).doubleValue());
        return null;
      }

      public Void caseObject(Json.Object jsonObject1) {
        JSONObject jsonObject2 = (JSONObject) json2;
        assertEquals("size", jsonObject1.size(), jsonObject2.length());
        for (Map.Entry<Json.String, Json.Value> e : jsonObject1.entrySet()) {
          assertTrue("has key <" + e.getKey().getString() + ">", jsonObject2.has(e.getKey().getString()));
          assertJsonEquals(e.getValue(), jsonObject2.get(e.getKey().getString()));
        }
        return null;
      }

      public Void caseString(Json.String string) {
        assertTrue(json2 instanceof java.lang.String);
        assertEquals(string.getString(), json2);
        return null;
      }
    });
  }

  @Test
  public void orgJsonCompare1() throws Exception {
    assertJsonEquals(Json.object(), new JSONObject("{}"));
  }

  @Test(expected = AssertionFailedError.class)
  public void orgJsonCompare2() throws Exception {
    Json.Object o1 = Json.object();
    o1.put(Json.string("a"), Json.NULL);
    assertJsonEquals(o1, new JSONObject("{}"));
  }

  @Test(expected = AssertionFailedError.class)
  public void orgJsonCompare3() throws Exception {
    Json.Object o1 = Json.object();
    o1.put(Json.string("a"), Json.NULL);
    assertJsonEquals(o1, new JSONObject("{\"a\":4}"));
  }

  @Test
  public void orgJsonCompare4() throws Exception {
    Json.Object o1 = Json.object();
    o1.put(Json.string("a"), Json.NULL);
    assertJsonEquals(o1, new JSONObject("{\"a\":null}"));
  }

  @Test
  public void orgJsonCompare5() throws Exception {
    Json.Object o1 = Json.object();
    o1.put(Json.string("a"), Json.TRUE);
    assertJsonEquals(o1, new JSONObject("{\"a\":true}"));
  }

  @Test(expected = AssertionFailedError.class)
  public void orgJsonCompare6() throws Exception {
    Json.Object o1 = Json.object();
    o1.put(Json.string("a"), Json.FALSE);
    assertJsonEquals(o1, new JSONObject("{\"a\":true}"));
  }

  @Test
  public void orgJsonCompare7() throws Exception {
    Json.Object o1 = Json.object();
    o1.put(Json.string("a"), Json.string("wow"));
    assertJsonEquals(o1, new JSONObject("{\"a\":'wow'}"));
  }

  @Test(expected = AssertionFailedError.class)
  public void orgJsonCompare8() throws Exception {
    Json.Object o1 = Json.object();
    o1.put(Json.string("a"), Json.string("wow"));
    assertJsonEquals(o1, new JSONObject("{\"a\":'waw'}"));
  }

  @Test
  public void orgJsonCompare9() throws Exception {
    Json.Object o1 = Json.object();
    o1.put(Json.string("a"), Json.number(4));
    assertJsonEquals(o1, new JSONObject("{\"a\":4}"));
  }

  @Test(expected = AssertionFailedError.class)
  public void orgJsonCompare10() throws Exception {
    Json.Object o1 = Json.object();
    o1.put(Json.string("a"), Json.number(4));
    assertJsonEquals(o1, new JSONObject("{\"a\":4.1}"));
  }

  @Test(expected = AssertionFailedError.class)
  public void orgJsonCompare11() throws Exception {
    Json.Object o1 = Json.object();
    o1.put(Json.string("a"), Json.number(4));
    assertJsonEquals(o1, new JSONObject("{\"b\":4}"));
  }

  @Test
  public void orgJsonCompare12() throws Exception {
    assertJsonEquals(Json.array(), new JSONArray("[]"));
  }

  @Test
  public void orgJsonCompare13() throws Exception {
    Json.Array array = Json.array();
    array.add(Json.TRUE);
    assertJsonEquals(array, new JSONArray("[true]"));
  }

  @Test(expected = AssertionFailedError.class)
  public void orgJsonCompare14() throws Exception {
    Json.Array array = Json.array();
    array.add(Json.TRUE);
    assertJsonEquals(array, new JSONArray("[false]"));
  }

  @Test(expected = AssertionFailedError.class)
  public void orgJsonCompare15() throws Exception {
    Json.Array array = Json.array();
    array.add(Json.TRUE);
    assertJsonEquals(array, new JSONArray("[true,8]"));
  }

}
