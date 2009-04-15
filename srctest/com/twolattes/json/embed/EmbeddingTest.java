package com.twolattes.json.embed;

import static com.twolattes.json.Json.number;
import static com.twolattes.json.Json.object;
import static com.twolattes.json.Json.string;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.json.JSONException;
import org.junit.Ignore;
import org.junit.Test;

import com.twolattes.json.Json;
import com.twolattes.json.Marshaller;
import com.twolattes.json.TwoLattes;

public class EmbeddingTest {

  @Ignore
  @Test(expected = IllegalArgumentException.class)
  public void embeddingConflict() {
    TwoLattes.createMarshaller(EmbeddingWithConflict.class);
  }

  @Ignore
  @Test(expected = IllegalArgumentException.class)
  public void embeddingConflictInSubclasses() {
    TwoLattes.createMarshaller(EmbeddingWithConflictInSubclasses.class);
  }

  @Ignore
  @Test(expected = IllegalArgumentException.class)
  public void embeddingTwoClassesWithConflict() {
    TwoLattes.createMarshaller(EmbeddingTwoClassesWithConflict.class);
  }

  @Ignore
  @Test(expected = IllegalArgumentException.class)
  public void embeddingWithConflictBetweenSubclassesAndEmbedded() {
    TwoLattes.createMarshaller(EmbeddingWithConflictBetweenSubclassesAndEmbedded.class);
  }

  @Ignore
  @Test(expected = IllegalArgumentException.class)
  public void embeddingNonEntityValue() throws Exception {
    TwoLattes.createMarshaller(EmbeddingNonEntityValue.class);
  }

  @Ignore
  @Test(expected = IllegalArgumentException.class)
  public void nestedEmbeddingWithConflict() throws Exception {
    TwoLattes.createMarshaller(NestedEmbeddingWithConflict.class);
  }

  @Ignore
  @Test
  public void marshallingEmbeddingOnValue() throws Exception {
    EmbeddingOnValue embeddingOnValue = new EmbeddingOnValue();
    embeddingOnValue.embedded = new Embedded();
    embeddingOnValue.embedded.a = 1;
    embeddingOnValue.embedded.b = 2;
    embeddingOnValue.c = 3;

    assertEquals(
        object(
            string("a"), number(1),
            string("b"), number(2),
            string("c"), number(3)),
        TwoLattes.createMarshaller(EmbeddingOnValue.class).marshall(embeddingOnValue));
  }

  @Ignore
  @Test
  public void marshallingEmbeddingOnEntity() throws Exception {
    Embedding embedding = new Embedding();
    embedding.embedded = new Embedding.EmbeddedOnEntity();
    embedding.embedded.a = 1;
    embedding.embedded.b = 2;
    embedding.c = 3;

    assertEquals(
        object(
            string("a"), number(1),
            string("b"), number(2),
            string("c"), number(3)),
        TwoLattes.createMarshaller(Embedding.class).marshall(embedding));
  }

  @Ignore
  @Test
  public void marshallingEmbeddingWithSubclasses() throws Exception {
    EmbeddingWithSubclasses embeddingWithSubclasses = new EmbeddingWithSubclasses();
    EmbeddedWithSubclasses.SecondEmbeddedSubclass subclass =
        new EmbeddedWithSubclasses.SecondEmbeddedSubclass();
    subclass.b = 2;
    embeddingWithSubclasses.embedded = subclass;
    embeddingWithSubclasses.c = "C";

    assertEquals(
        object(
            string("t"), string("second"),
            string("b"), number(2),
            string("c"), string("C")),
        TwoLattes.createMarshaller(EmbeddingWithSubclasses.class)
            .marshall(embeddingWithSubclasses));
  }

  @Ignore
  @Test
  public void marshallingEmbeddingTwoClasses() throws Exception {
    EmbeddingTwoClasses embeddingTwoClasses = new EmbeddingTwoClasses();
    embeddingTwoClasses.first = new EmbeddingTwoClasses.FirstEmbeddedClass();
    embeddingTwoClasses.second = new EmbeddingTwoClasses.SecondEmbeddedClass();
    embeddingTwoClasses.first.a = 1;
    embeddingTwoClasses.second.b = 2;
    embeddingTwoClasses.c = 3;

    assertEquals(
        object(
            string("a"), number(1),
            string("b"), number(2),
            string("c"), number(3)),
        TwoLattes.createMarshaller(EmbeddingTwoClasses.class)
            .marshall(embeddingTwoClasses));
  }

  @Ignore
  @Test
  public void unmarshallingEmbeddingOnValue() throws Exception {
    EmbeddingOnValue obj = unmarshall(EmbeddingOnValue.class,
        "{\"a\":1.0,\"b\":2.0,\"c\":3.0}");
    assertEquals(1, obj.embedded.a);
    assertEquals(2, obj.embedded.b);
    assertEquals(3, obj.c);
  }

  @Ignore
  @Test
  public void unmarshallingEmbeddingOnEntity() throws Exception {
    Embedding obj = unmarshall(Embedding.class,
        "{\"a\":1.0,\"b\":2.0,\"c\":3.0}");
    assertEquals(1, obj.embedded.a);
    assertEquals(2, obj.embedded.b);
    assertEquals(3, obj.c);
  }

  @Ignore
  @Test
  public void unmarshallingEmbeddingWithSubclasses() throws Exception {
    EmbeddingWithSubclasses obj = unmarshall(EmbeddingWithSubclasses.class,
        "{\"t\":\"second\",\"b\":2.0,\"c\":\"C\"}");
    assertEquals("C", obj.c);
    assertTrue(obj.embedded instanceof EmbeddedWithSubclasses.SecondEmbeddedSubclass);
    assertEquals(2, ((EmbeddedWithSubclasses.SecondEmbeddedSubclass) obj.embedded).b);
  }

  @Ignore
  @Test
  public void unmarshallingEmbeddingTwoClasses() throws Exception {
    EmbeddingTwoClasses obj = unmarshall(EmbeddingTwoClasses.class,
        "{\"a\":1.0,\"b\":2.0,\"c\":3.0}");
    assertEquals(1, obj.first.a);
    assertEquals(2, obj.second.b);
    assertEquals(3, obj.c);
  }

  private <T> T unmarshall(Class<T> clazz, String json) throws JSONException {
    Marshaller<T> marshaller = TwoLattes.createMarshaller(clazz);
    return marshaller.unmarshall((Json.Object) Json.fromString(json));
  }

}
