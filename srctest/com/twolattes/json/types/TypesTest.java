package com.twolattes.json.types;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.immutableMap;
import static com.twolattes.json.Json.NULL;
import static com.twolattes.json.Json.array;
import static com.twolattes.json.Json.number;
import static com.twolattes.json.Json.object;
import static com.twolattes.json.Json.string;
import static com.twolattes.json.TwoLattes.createMarshaller;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;

import org.junit.Test;

import com.twolattes.json.Json;
import com.twolattes.json.Marshaller;
import com.twolattes.json.TwoLattes;
import com.twolattes.json.types.EntityRequiringTypeRegistration1.WeirdJsonType;
import com.twolattes.json.types.EntityRequiringTypeRegistration2.ArrayJsonType;
import com.twolattes.json.types.EntityRequiringTypeRegistration2.IdJsonType;

public class TypesTest {

  @Test
  public void testMarshallURL() throws Exception {
    EntityWithType e = new EntityWithType();
    e.url = new  URL("http://whatever.com");
    Json.Object o = (Json.Object) createMarshaller(EntityWithType.class)
      .marshall(e, "url");

    assertEquals(1, o.size());
    assertEquals(string("http://whatever.com"), o.get(string("url")));
  }

  @Test
  public void testMarshallURLNull() throws Exception {
    Json.Object o = (Json.Object) createMarshaller(EntityWithType.class)
        .marshall(new EntityWithType(), "url");

    assertEquals(1, o.size());
    assertEquals(NULL, o.get(string("url")));
  }

  @Test
  public void testUnmarshallURL() throws Exception {
    EntityWithType e = createMarshaller(EntityWithType.class).unmarshall(
        Json.fromString("{\"url\":\"http://whatever.com\"}"), "url");

    assertEquals(new URL("http://whatever.com"), e.url);
  }

  @Test
  public void testUnnarshallURLNull() throws Exception {
    EntityWithType e = createMarshaller(EntityWithType.class)
      .unmarshall(Json.fromString("{\"url\":null}"), "url");

    assertNull(e.url);
  }

  @Test
  public void testMarshallBigDecimal() throws Exception {
    EntityWithType e = new EntityWithType();
    e.bigDecimal = BigDecimal.valueOf(8.398741);
    Json.Object o = (Json.Object) createMarshaller(EntityWithType.class)
      .marshall(e, "bigDecimal");

    assertEquals(1, o.size());
    assertEquals(number(8.398741), o.get(string("bigDecimal")));
  }

  @Test
  public void testMarshallBigDecimalNull() throws Exception {
    Json.Object o = (Json.Object) createMarshaller(EntityWithType.class)
        .marshall(new EntityWithType(), "bigDecimal");

    assertEquals(1, o.size());
    assertEquals(NULL, o.get(string("bigDecimal")));
  }

  @Test
  public void testUnmarshallBigDecimal1() throws Exception {
    EntityWithType e = createMarshaller(EntityWithType.class).unmarshall(
        Json.fromString("{\"bigDecimal\":78945.13245}"), "bigDecimal");

    assertEquals(BigDecimal.valueOf(78945.13245), e.bigDecimal);
  }

  @Test
  public void testUnmarshallBigDecimal2() throws Exception {
    EntityWithType e = createMarshaller(EntityWithType.class).unmarshall(
        Json.fromString("{\"bigDecimal\":7}"), "bigDecimal");

    assertEquals(BigDecimal.valueOf(7), e.bigDecimal);
  }

  @Test
  public void testUnmarshallBigDecimal3() throws Exception {
    EntityWithType e = createMarshaller(EntityWithType.class).unmarshall(
        Json.fromString("{\"bigDecimal\":7777777777777777}"), "bigDecimal");

    assertEquals(BigDecimal.valueOf(7777777777777777L), e.bigDecimal);
  }

  @Test
  public void testUnmarshallBigDecimalNull() throws Exception {
    EntityWithType e = createMarshaller(EntityWithType.class).unmarshall(
        Json.fromString("{\"bigDecimal\":null}"), "bigDecimal");

    assertNull(e.bigDecimal);
  }

  @Test
  public void testUnmarshallBigInteger1() throws Exception {
    EntityWithType e = createMarshaller(EntityWithType.class).unmarshall(
        Json.fromString("{\"bigInteger\":null}"), "bigInteger");

    assertNull(e.bigInteger);
  }

  @Test
  public void testUnmarshallBigInteger2() throws Exception {
    EntityWithType e = createMarshaller(EntityWithType.class).unmarshall(
        Json.fromString("{\"bigInteger\":78945}"), "bigInteger");

    assertTrue(
        BigInteger.valueOf(78945).compareTo(e.bigInteger) == 0);
  }

  @Test
  public void testUnmarshallBigInteger3() throws Exception {
    EntityWithType e = createMarshaller(EntityWithType.class).unmarshall(
        Json.fromString("{\"bigInteger\":789451111111111111}"), "bigInteger");

    assertTrue(
        BigInteger.valueOf(789451111111111111L).compareTo(e.bigInteger) == 0);
  }

  @Test
  public void typesRegistration1() throws Exception {
    Marshaller<EntityRequiringTypeRegistration1> marshaller = TwoLattes
        .withType(WeirdJsonType.class)
        .createMarshaller(EntityRequiringTypeRegistration1.class);
    Json.Object o = (Json.Object) marshaller.marshall(
        new EntityRequiringTypeRegistration1() {{
          this.weird1 = new Weird("field-registered-type");
          this.weird2 = new Weird("field-overridden-type");
          this.weird3 = new Weird("getter-registered-type");
          this.weird4 = new Weird("getter-overridden-type");
        }});

    assertEquals(string("field-registered-type"), o.get(string("weird1")));
    assertEquals(string("$field-overridden-type$"), o.get(string("weird2")));
    assertEquals(string("getter-registered-type"), o.get(string("weird3")));
    assertEquals(string("$getter-overridden-type$"), o.get(string("weird4")));
    assertEquals(4, o.size());

    o = (Json.Object) marshaller.marshall(marshaller.unmarshall(o));

    assertEquals(string("field-registered-type"), o.get(string("weird1")));
    assertEquals(string("$#$field-overridden-type$#$"), o.get(string("weird2")));
    assertEquals(string("getter-registered-type"), o.get(string("weird3")));
    assertEquals(string("$#$getter-overridden-type$#$"), o.get(string("weird4")));
    assertEquals(4, o.size());
  }

  @Test
  public void typesRegistration2() throws Exception {
    Json.Object o = (Json.Object) TwoLattes.withType(WeirdJsonType.class)
        .createMarshaller(EntityRequiringTypeRegistration1.class)
        .marshall(new EntityRequiringTypeRegistration1() {{
          this.list = newArrayList(new Weird("yo"));
        }});

    assertEquals(array(string("yo")), o.get(string("list")));
    assertEquals(1, o.size());
  }

  @Test
  public void typesRegistration3() throws Exception {
    Json.Object o = (Json.Object) TwoLattes.withType(WeirdJsonType.class)
        .createMarshaller(EntityRequiringTypeRegistration1.class)
        .marshall(new EntityRequiringTypeRegistration1() {{
          this.map = immutableMap("foo", new Weird("bar"));
        }});

    assertEquals(object(string("foo"), string("bar")), o.get(string("map")));
    assertEquals(1, o.size());
  }

  @Test
  public void typesRegistration4() throws Exception {
    Marshaller<EntityRequiringTypeRegistration2> marshaller = TwoLattes
        .withType(IdJsonType.class)
        .createMarshaller(EntityRequiringTypeRegistration2.class);

    Json.Value o = marshaller
        .marshall(new EntityRequiringTypeRegistration2() {{
          this.id = new Id<Object>() {{
            this.id = 7;
          }};
        }});

    assertEquals(
        object(
            string("id"), number(7),
            string("array"), NULL),
        o);

    EntityRequiringTypeRegistration2 entity = marshaller.unmarshall(o);

    assertEquals(7L, entity.id.id);
    assertNull(entity.array);
  }

  @Test(expected = IllegalArgumentException.class)
  public void typesRegistration5() throws Exception {
    TwoLattes
        .withType(IdJsonType.class)
        .withType(ArrayJsonType.class)
        .createMarshaller(EntityRequiringTypeRegistration2.class);
  }

}
