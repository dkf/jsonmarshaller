package com.twolattes.json.nativetypes;

import static com.twolattes.json.Json.FALSE;
import static com.twolattes.json.Json.TRUE;
import static com.twolattes.json.Json.number;
import static com.twolattes.json.Json.string;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.twolattes.json.Entity;
import com.twolattes.json.Json;
import com.twolattes.json.Marshaller;
import com.twolattes.json.TwoLattes;
import com.twolattes.json.Value;

public class LiteralsTest {

  @Entity
  static class Int {
    @Value int literal;
    @Value Integer object;
    @Value int[] nativeArray;
    @Value Integer[] objectArray;
  }

  @Test
  public void testInt() throws Exception {
    Marshaller<Int> marshaller = TwoLattes.createMarshaller(Int.class);

    Json.Object object = marshaller.marshall(new Int() {{
      this.literal = 1;
      this.object = 2;
      this.nativeArray = new int[] { 3, 4 };
      this.objectArray = new Integer[] { 5, 6 };
    }});

    assertJsonObjectWellFormed(object);

    Int instance = marshaller.unmarshall(object);

    assertEquals(1, instance.literal);
    assertEquals(2, instance.object);
    assertEquals(2, instance.nativeArray.length);
    assertEquals(3, instance.nativeArray[0]);
    assertEquals(4, instance.nativeArray[1]);
    assertEquals(2, instance.objectArray.length);
    assertEquals(5, instance.objectArray[0]);
    assertEquals(6, instance.objectArray[1]);
  }

  @Entity
  static class Double {
    @Value double literal;
    @Value java.lang.Double object;
    @Value double[] nativeArray;
    @Value java.lang.Double[] objectArray;
  }

  @Test
  public void testDouble() throws Exception {
    Marshaller<Double> marshaller = TwoLattes.createMarshaller(Double.class);

    Json.Object object = marshaller.marshall(new Double() {{
      this.literal = 1.0;
      this.object = 2.0;
      this.nativeArray = new double[] { 3, 4 };
      this.objectArray = new java.lang.Double[] { 5.0, 6.0 };
    }});

    assertJsonObjectWellFormed(object);

    Double instance = marshaller.unmarshall(object);

    assertEquals(1.0, instance.literal);
    assertEquals(2.0, instance.object);
    assertEquals(2, instance.nativeArray.length);
    assertEquals(3.0, instance.nativeArray[0]);
    assertEquals(4.0, instance.nativeArray[1]);
    assertEquals(2, instance.objectArray.length);
    assertEquals(5.0, instance.objectArray[0]);
    assertEquals(6.0, instance.objectArray[1]);
  }

  @Entity
  static class Short {
    @Value short literal;
    @Value java.lang.Short object;
    @Value short[] nativeArray;
    @Value java.lang.Short[] objectArray;
  }

  @Test
  public void testShort() throws Exception {
    Marshaller<Short> marshaller = TwoLattes.createMarshaller(Short.class);

    Json.Object object = marshaller.marshall(new Short() {{
      this.literal = 1;
      this.object = 2;
      this.nativeArray = new short[] { 3, 4 };
      this.objectArray = new java.lang.Short[] { 5, 6 };
    }});

    assertJsonObjectWellFormed(object);

    Short instance = marshaller.unmarshall(object);

    assertEquals(1, instance.literal);
    assertEquals(2.0, instance.object);
    assertEquals(2, instance.nativeArray.length);
    assertEquals(3, instance.nativeArray[0]);
    assertEquals(4, instance.nativeArray[1]);
    assertEquals(2, instance.objectArray.length);
    assertEquals(5, instance.objectArray[0]);
    assertEquals(6, instance.objectArray[1]);
  }

  @Entity
  static class Long {
    @Value long literal;
    @Value java.lang.Long object;
    @Value long[] nativeArray;
    @Value java.lang.Long[] objectArray;
  }

  @Test
  public void testLong() throws Exception {
    Marshaller<Long> marshaller = TwoLattes.createMarshaller(Long.class);

    Json.Object object = marshaller.marshall(new Long() {{
      this.literal = 1L;
      this.object = 2L;
      this.nativeArray = new long[] { 3, 4 };
      this.objectArray = new java.lang.Long[] { 5L, 6L };
    }});

    assertJsonObjectWellFormed(object);

    Long instance = marshaller.unmarshall(object);

    assertEquals(1, instance.literal);
    assertEquals(2.0, instance.object);
    assertEquals(2, instance.nativeArray.length);
    assertEquals(3, instance.nativeArray[0]);
    assertEquals(4, instance.nativeArray[1]);
    assertEquals(2, instance.objectArray.length);
    assertEquals(5, instance.objectArray[0]);
    assertEquals(6, instance.objectArray[1]);
  }

  @Entity
  static class Float {
    @Value float literal;
    @Value java.lang.Float object;
    @Value float[] nativeArray;
    @Value java.lang.Float[] objectArray;
  }

  @Test
  public void testFloat() throws Exception {
    Marshaller<Float> marshaller = TwoLattes.createMarshaller(Float.class);

    Json.Object object = marshaller.marshall(new Float() {{
      this.literal = 1f;
      this.object = 2f;
      this.nativeArray = new float[] { 3f, 4f };
      this.objectArray = new java.lang.Float[] { 5f, 6f };
    }});

    assertJsonObjectWellFormed(object);

    Float instance = marshaller.unmarshall(object);

    assertEquals(1f, instance.literal);
    assertEquals(2f, instance.object);
    assertEquals(2, instance.nativeArray.length);
    assertEquals(3f, instance.nativeArray[0]);
    assertEquals(4f, instance.nativeArray[1]);
    assertEquals(2, instance.objectArray.length);
    assertEquals(5f, instance.objectArray[0]);
    assertEquals(6f, instance.objectArray[1]);
  }

  @Entity
  static class Char {
    @Value char literal;
    @Value Character object;
    @Value char[] nativeArray;
    @Value Character[] objectArray;
  }

  @Test
  public void testChar() throws Exception {
    Marshaller<Char> marshaller = TwoLattes.createMarshaller(Char.class);

    Json.Object object = marshaller.marshall(new Char() {{
      this.literal = 'a';
      this.object = 'b';
      this.nativeArray = new char[] { 'c', 'd' };
      this.objectArray = new Character[] { 'e', 'f' };
    }});

    assertJsonObjectWellFormed(object,
        string("a"), string("b"), string("c"),
        string("d"), string("e"), string("f"));

    Char instance = marshaller.unmarshall(object);

    assertEquals('a', instance.literal);
    assertEquals('b', instance.object);
    assertEquals(2, instance.nativeArray.length);
    assertEquals('c', instance.nativeArray[0]);
    assertEquals('d', instance.nativeArray[1]);
    assertEquals(2, instance.objectArray.length);
    assertEquals('e', instance.objectArray[0]);
    assertEquals('f', instance.objectArray[1]);
  }

  @Entity
  static class Bool {
    @Value boolean literal;
    @Value Boolean object;
    @Value boolean[] nativeArray;
    @Value Boolean[] objectArray;
  }

  @Test
  public void testBool() throws Exception {
    Marshaller<Bool> marshaller = TwoLattes.createMarshaller(Bool.class);

    Json.Object object = marshaller.marshall(new Bool() {{
      this.literal = true;
      this.object = false;
      this.nativeArray = new boolean[] { true, false };
      this.objectArray = new Boolean[] { true, false };
    }});

    assertJsonObjectWellFormed(object,
        TRUE, FALSE, TRUE, FALSE, TRUE, FALSE);

    Bool instance = marshaller.unmarshall(object);

    assertEquals(true, instance.literal);
    assertEquals(false, instance.object);
    assertEquals(2, instance.nativeArray.length);
    assertEquals(true, instance.nativeArray[0]);
    assertEquals(false, instance.nativeArray[1]);
    assertEquals(2, instance.objectArray.length);
    assertEquals(true, instance.objectArray[0]);
    assertEquals(false, instance.objectArray[1]);
  }

  private void assertJsonObjectWellFormed(Json.Object object) {
    assertEquals(4, object.size());
    assertJsonObjectWellFormed(object, number(1), number(2), number(3),
        number(4), number(5), number(6));
  }

  private void assertJsonObjectWellFormed(Json.Object object, Json.Value one,
      Json.Value two, Json.Value three, Json.Value four, Json.Value five,
      Json.Value six) {
    assertEquals(one, object.get(string("literal")));
    assertEquals(two, object.get(string("object")));
    Json.Array nativeArray = (Json.Array) object.get(string("nativeArray"));
    assertEquals(2, nativeArray.size());
    assertEquals(three, nativeArray.get(0));
    assertEquals(four, nativeArray.get(1));
    Json.Array objectArray = (Json.Array) object.get(string("objectArray"));
    assertEquals(2, objectArray.size());
    assertEquals(five, objectArray.get(0));
    assertEquals(six, objectArray.get(1));
  }

}
