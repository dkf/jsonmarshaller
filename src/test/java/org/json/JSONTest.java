package org.json;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JSONTest {
  @Test
  public void testInteger() throws JSONException {
    JSONObject json = new JSONObject("{a:3}");
    assertEquals(3, json.get("a"));
  }

  @Test
  public void testLeastInteger() throws JSONException {
    JSONObject json = new JSONObject("{a:" + Integer.MIN_VALUE + "}");
    assertEquals(Integer.MIN_VALUE, json.get("a"));
  }

  @Test
  public void testLeastLong() throws JSONException {
    JSONObject json = new JSONObject("{a:" + Long.MIN_VALUE + "}");
    assertEquals(Long.MIN_VALUE, json.get("a"));
  }

  @Test
  public void testEmptyString() throws JSONException {
    JSONObject json = new JSONObject("{yaya:\"\"}");
    assertEquals("", json.get("yaya"));
  }

  @Test
  public void testQuotedKeyWithDouble() throws JSONException {
    JSONObject json = new JSONObject("{\"key\":\"foobar\"}");
    assertEquals("foobar", json.get("key"));
  }

  @Test
  public void testOneCharacterStringInSingleQuotes() throws Exception {
    JSONObject json = new JSONObject("{key:'a'}");
    assertEquals("a", json.get("key"));
  }

  @Test
  public void testQuotedKeyWithSingle() throws JSONException {
    JSONObject json = new JSONObject("{'key':\"foobar\"}");
    assertEquals("foobar", json.get("key"));
  }

  @Test
  public void testString1() throws JSONException {
    JSONObject json = new JSONObject("{yaya:\"hello from\"}");
    assertEquals("hello from", json.get("yaya"));
  }

  @Test
  public void testString2() throws JSONException {
    JSONObject json = new JSONObject("{ami: \"Jean-Paul\\nII\"}");
    assertEquals("Jean-Paul\nII", json.get("ami"));
  }

  @Test
  public void testString3_singleQuotes() throws JSONException {
    JSONObject json = new JSONObject("{asd: 'Famous CS Phrase'}");
    assertEquals("Famous CS Phrase", json.get("asd"));
  }

  @Test
  public void testChar1_simple() throws JSONException {
    JSONObject json = new JSONObject("{ami: 'H'}");
    assertEquals("H", json.get("ami"));
  }

  @Test
  public void testChar2_escaped() throws JSONException {
    JSONObject json = new JSONObject("{ami: '\b'}");
    assertEquals("\b", json.get("ami"));
  }

  @Test
  public void testObjectInObject() throws JSONException {
    JSONObject json = new JSONObject("{\"__yo\":{b:4.5}}");
    assertTrue(json.get("__yo") instanceof JSONObject);
    JSONObject innerjson = (JSONObject) json.get("__yo");
    assertEquals(4.5, innerjson.get("b"));
  }

  @Test
  public void testBooleanArray() throws JSONException {
    JSONObject json = new JSONObject("{c: [true, false, true]}");
    assertTrue(json.get("c") instanceof JSONArray);
    JSONArray array = (JSONArray) json.get("c");
    assertEquals(true, array.get(0));
    assertEquals(false, array.get(1));
    assertEquals(true, array.get(2));
  }

  @Test
  public void testMixedArray() throws JSONException {
    JSONObject json = new JSONObject("{hello_world: [3, 1.2, \"value\"]}");
    assertTrue(json.get("hello_world") instanceof JSONArray);
    JSONArray array = (JSONArray) json.get("hello_world");
    assertEquals(3, array.get(0));
    assertEquals(1.2, array.get(1));
    assertEquals("value", array.get(2));
  }

  @Test
  public void testNullInObject() throws Exception {
    JSONObject o = new JSONObject("{\"hello\":null}");

    assertEquals(JSONObject.NULL, o.get("hello"));
  }

  @Test
  public void testNullArray() throws Exception {
    JSONObject o = new JSONObject();
    o.put("hello", JSONArray.NULL);

    assertEquals("{\"hello\":null}", o.toString());
  }

  @Test
  public void testUnderscoreAsStartingKey() throws Exception {
    JSONObject o = new JSONObject("{items:" +
        "[{name:'Africa', type:'continent'," +
        "children:[{_reference:'Egypt'}]}," +
        "{ name:'Egypt', type:'country'}]}");

    JSONArray array = o.getJSONArray("items");
    assertEquals(2, array.length());
    JSONObject africa = array.getJSONObject(0);
    assertEquals("Africa", africa.getString("name"));
    JSONArray children = africa.getJSONArray("children");
    assertEquals(1, children.length());
    assertEquals("Egypt", children.getJSONObject(0).getString("_reference"));
  }

  @Test
  public void testUnmarshalMarshall1() throws JSONException {
    twoWayTest("{foo: 5}");
  }

  @Test
  public void testUnmarshalMarshall2() throws JSONException {
    twoWayTest("{jackie: \"ya\"}");
  }

  @Test
  public void testUnmarshalMarshall3() throws JSONException {
    twoWayTest("{foo: 5.0}");
  }

  @Test
  public void testUnmarshalMarshall4() throws JSONException {
    twoWayTest("{foo:  [5.0]}");
  }

  @Test
  public void testUnmarshalMarshallNull() throws JSONException {
    twoWayTest("{foo:  null}");
  }

  @Test
  public void testJSONObjectNUllEqualsJSONArrayNull() throws Exception {
    assertEquals(JSONObject.NULL, JSONArray.NULL);
  }

  @Test
  public void testJSONArrayNullEqualsJSONObjectNUll() throws Exception {
    assertEquals(JSONArray.NULL, JSONObject.NULL);
  }

  private void twoWayTest(String o) throws JSONException {
    JSONObject json1 = new JSONObject(o);
    JSONObject json2 = new JSONObject(json1.toString());
    assertEquals(json1, json2);
  }
}
