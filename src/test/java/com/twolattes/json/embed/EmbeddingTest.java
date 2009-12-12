package com.twolattes.json.embed;

import static com.twolattes.json.Json.array;
import static com.twolattes.json.Json.number;
import static com.twolattes.json.Json.object;
import static com.twolattes.json.Json.string;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.json.JSONException;
import org.junit.Test;

import com.twolattes.json.Json;
import com.twolattes.json.Marshaller;
import com.twolattes.json.TwoLattes;

public class EmbeddingTest {

  @Test(expected = IllegalArgumentException.class)
  public void embeddingConflict() {
    TwoLattes.createMarshaller(EmbeddingWithConflict.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void embeddingConflictInSubclasses() {
    TwoLattes.createMarshaller(EmbeddingWithConflictInSubclasses.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void embeddingTwoClassesWithConflict() {
    TwoLattes.createMarshaller(EmbeddingTwoClassesWithConflict.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void embeddingWithConflictBetweenSubclassesAndEmbedded() {
    TwoLattes.createMarshaller(EmbeddingWithConflictBetweenSubclassesAndEmbedded.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void embeddingNonEntityValue() throws Exception {
    TwoLattes.createMarshaller(EmbeddingNonEntityValue.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nestedEmbeddingWithConflict() throws Exception {
    TwoLattes.createMarshaller(NestedEmbeddingWithConflict.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void embeddingWithConflictOnDiscriminator() throws Exception {
    TwoLattes.createMarshaller(EmbeddingWithConflictOnDiscriminator.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void embeddingList() throws Exception {
    TwoLattes.createMarshaller(EmbeddingList.class);
  }

  @Test
  public void embeddingOnValue() {
    TwoLattes.createMarshaller(EmbeddingOnValue.class);
  }

  @Test
  public void embeddingOnEntity() {
    TwoLattes.createMarshaller(Embedding.class);
  }

  @Test
  public void embeddingWithSubclasses() {
    TwoLattes.createMarshaller(EmbeddingWithSubclasses.class);
  }

  @Test
  public void embeddingTwoClasses() {
    TwoLattes.createMarshaller(EmbeddingTwoClasses.class);
  }

  @Test
  public void embeddingWithInlinedValue() {
    TwoLattes.createMarshaller(EmbeddingWithInlinedValue.class);
  }

  @Test
  public void embeddingWithGetterSetter() {
    TwoLattes.createMarshaller(EmbeddingWithGetterSetter.class);
  }

  @Test
  public void nestedEmbedding() {
    TwoLattes.createMarshaller(NestedEmbedding.class);
  }

  @Test
  public void twoEmbeddingClasses() {
    TwoLattes.createMarshaller(TwoEmbeddingClasses.class);
  }

  @Test
  public void listOfEmbeddings() {
    TwoLattes.createMarshaller(ListOfEmbeddings.class);
  }

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

  @Test
  public void marshallingEmbeddingWithGetterSetter() throws Exception {
    EmbeddingWithGetterSetter embedding = new EmbeddingWithGetterSetter();
    embedding.setFoo(new EmbeddingWithGetterSetter.EmbeddedOnEntity());
    embedding.embedded.a = 1;
    embedding.embedded.b = 2;
    embedding.c = 3;

    assertEquals(
        object(
            string("a"), number(1),
            string("b"), number(2),
            string("c"), number(3)),
        TwoLattes.createMarshaller(EmbeddingWithGetterSetter.class).marshall(embedding));
  }

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

  @Test
  public void marshallingEmbeddingWithInlinedValue() throws Exception {
    EmbeddingWithInlinedValue embeddingWithInlinedValue = new EmbeddingWithInlinedValue();
    embeddingWithInlinedValue.a = 6;
    embeddingWithInlinedValue.b = new EmbeddingWithInlinedValue.JustB();
    embeddingWithInlinedValue.b.b = new EmbeddingWithInlinedValue.JustA();
    embeddingWithInlinedValue.b.b.a = 3;

    assertEquals(
        object(
            string("a"), number(6),
            string("b"), number(3)),
        TwoLattes.createMarshaller(EmbeddingWithInlinedValue.class)
            .marshall(embeddingWithInlinedValue));
  }

  @Test
  public void marshallingNestedEmbedding() throws Exception {
    NestedEmbedding nestedEmbedding = new NestedEmbedding();
    nestedEmbedding.d = "D";
    nestedEmbedding.nested = new NestedEmbedding.FirstNestedClass();
    nestedEmbedding.nested.c = "C";
    nestedEmbedding.nested.embedded = new Embedded();
    nestedEmbedding.nested.embedded.a = 1;
    nestedEmbedding.nested.embedded.b = 2;

    assertEquals(
        object(
            string("a"), number(1),
            string("b"), number(2),
            string("c"), string("C"),
            string("d"), string("D")),
        TwoLattes.createMarshaller(NestedEmbedding.class)
            .marshall(nestedEmbedding));
  }

  @Test
  public void marshallingTwoEmbeddingClasses() throws Exception {
    TwoEmbeddingClasses embedding = new TwoEmbeddingClasses();
    embedding.d = 4;
    embedding.foo = new TwoEmbeddingClasses.Foo();
    embedding.foo.c = 3;
    embedding.foo.bar = new TwoEmbeddingClasses.Bar();
    embedding.foo.bar.b = 2;
    embedding.foo.bar.baz = new TwoEmbeddingClasses.Baz();
    embedding.foo.bar.baz.a = 1;

    assertEquals(
        object(
            string("d"), number(4),
            string("c"), number(3),
            string("bar"), object(
                string("b"), number(2),
                string("a"), number(1))),
        TwoLattes.createMarshaller(TwoEmbeddingClasses.class)
            .marshall(embedding));
  }

  @Test
  public void marshallingListOfEmbeddings() throws Exception {
    ListOfEmbeddings embedding = new ListOfEmbeddings();
    embedding.embeddings = new ArrayList<EmbeddingOnValue>();
    embedding.embeddings.add(new EmbeddingOnValue());
    embedding.embeddings.get(0).c = 1;
    embedding.embeddings.get(0).embedded = new Embedded();
    embedding.embeddings.get(0).embedded.a = 2;
    embedding.embeddings.get(0).embedded.b = 3;
    embedding.embeddings.add(new EmbeddingOnValue());
    embedding.embeddings.get(1).c = 4;
    embedding.embeddings.get(1).embedded = new Embedded();
    embedding.embeddings.get(1).embedded.a = 5;
    embedding.embeddings.get(1).embedded.b = 6;

    assertEquals(
        object(
            string("embeddings"),
            array(
                object(
                    string("c"), number(1),
                    string("a"), number(2),
                    string("b"), number(3)
                    ),
                object(
                    string("c"), number(4),
                    string("a"), number(5),
                    string("b"), number(6)
                    ))),
        TwoLattes.createMarshaller(ListOfEmbeddings.class)
            .marshall(embedding));
  }

  @Test
  public void nonOptionalNullBehavior() throws Exception {
    NonOptionalNull embedding = new NonOptionalNull();
    embedding.embedded = null;

    assertEquals(
        object(string("embedded"), Json.NULL),
        TwoLattes.createMarshaller(NonOptionalNull.class).marshall(embedding));
  }

  @Test
  public void optionalNullBehavior() throws Exception {
    OptionalNull embedding = new OptionalNull();
    embedding.embedded = null;

    assertEquals(
        object(),
        TwoLattes.createMarshaller(OptionalNull.class).marshall(embedding));
  }

  @Test
  public void marshallingEmbeddingOnValueWithNull() throws Exception {
    EmbeddingOnValue embeddingOnValue = new EmbeddingOnValue();
    embeddingOnValue.embedded = null;
    embeddingOnValue.c = 3;

    assertEquals(
        object(string("c"), number(3)),
        TwoLattes.createMarshaller(EmbeddingOnValue.class).marshall(embeddingOnValue));
  }

  @Test
  public void marshallingEmbeddingOptionalNull() throws Exception {
    EmbeddingOptionalNull embedding = new EmbeddingOptionalNull();
    embedding.embedded = null;
    embedding.c = 3;

    assertEquals(
        object(
            string("c"), number(3)),
        TwoLattes.createMarshaller(EmbeddingOptionalNull.class)
            .marshall(embedding));
  }

  @Test
  public void unmarshallingEmbeddingOnValue() throws Exception {
    EmbeddingOnValue obj = unmarshall(EmbeddingOnValue.class,
        "{\"a\":1.0,\"b\":2.0,\"c\":3.0}");
    assertEquals(1, obj.embedded.a);
    assertEquals(2, obj.embedded.b);
    assertEquals(3, obj.c);
  }

  @Test
  public void unmarshallingEmbeddingOnEntity() throws Exception {
    Embedding obj = unmarshall(Embedding.class,
        "{\"a\":1.0,\"b\":2.0,\"c\":3.0}");
    assertEquals(1, obj.embedded.a);
    assertEquals(2, obj.embedded.b);
    assertEquals(3, obj.c);
  }

  @Test
  public void unmarshallingEmbeddingWithSubclasses() throws Exception {
    EmbeddingWithSubclasses obj = unmarshall(EmbeddingWithSubclasses.class,
        "{\"t\":\"second\",\"b\":2.0,\"c\":\"C\"}");
    assertEquals("C", obj.c);
    assertTrue(obj.embedded instanceof EmbeddedWithSubclasses.SecondEmbeddedSubclass);
    assertEquals(2, ((EmbeddedWithSubclasses.SecondEmbeddedSubclass) obj.embedded).b);
  }

  @Test
  public void unmarshallingEmbeddingTwoClasses() throws Exception {
    EmbeddingTwoClasses obj = unmarshall(EmbeddingTwoClasses.class,
        "{\"a\":1.0,\"b\":2.0,\"c\":3.0}");
    assertEquals(1, obj.first.a);
    assertEquals(2, obj.second.b);
    assertEquals(3, obj.c);
  }

  @Test
  public void unmarshallingEmbeddingWithInlinedValue() throws Exception {
    EmbeddingWithInlinedValue embeddingWithInlinedValue =
        TwoLattes.createMarshaller(EmbeddingWithInlinedValue.class)
            .unmarshall(object(
                string("a"), number(6),
                string("b"), number(3)));

    assertEquals(6, embeddingWithInlinedValue.a);
    assertEquals(3, embeddingWithInlinedValue.b.b.a);
  }

  @Test
  public void unmarshallingNestedEmbedding() throws Exception {
    NestedEmbedding nestedEmbedding =
        TwoLattes.createMarshaller(NestedEmbedding.class)
            .unmarshall(object(
                string("a"), number(1),
                string("b"), number(2),
                string("c"), string("C"),
                string("d"), string("D")));

    assertEquals("D", nestedEmbedding.d);
    assertEquals("C", nestedEmbedding.nested.c);
    assertEquals(1, nestedEmbedding.nested.embedded.a);
    assertEquals(2, nestedEmbedding.nested.embedded.b);
  }

  @Test
  public void unmarshallingTwoEmbeddingClasses() throws Exception {
    TwoEmbeddingClasses embedding =
        TwoLattes.createMarshaller(TwoEmbeddingClasses.class)
            .unmarshall(object(
                string("d"), number(4),
                string("c"), number(3),
                string("bar"), object(
                    string("b"), number(2),
                    string("a"), number(1))));

    assertEquals(4, embedding.d);
    assertEquals(3, embedding.foo.c);
    assertEquals(2, embedding.foo.bar.b);
    assertEquals(1, embedding.foo.bar.baz.a);
  }

  @Test
  public void unmarshallingListOfEmbeddings() throws Exception {
    ListOfEmbeddings embedding =
      TwoLattes.createMarshaller(ListOfEmbeddings.class)
          .unmarshall(object(
            string("embeddings"),
            array(
                object(
                    string("c"), number(1),
                    string("a"), number(2),
                    string("b"), number(3)
                    ),
                object(
                    string("c"), number(4),
                    string("a"), number(5),
                    string("b"), number(6)
                    ))));

    assertEquals(1, embedding.embeddings.get(0).c);
    assertEquals(2, embedding.embeddings.get(0).embedded.a);
    assertEquals(3, embedding.embeddings.get(0).embedded.b);
    assertEquals(4, embedding.embeddings.get(1).c);
    assertEquals(5, embedding.embeddings.get(1).embedded.a);
    assertEquals(6, embedding.embeddings.get(1).embedded.b);
  }

  @Test
  public void unmarshallNonOptionalNullBehavior1() throws Exception {
    NonOptionalNull embedding =
        TwoLattes.createMarshaller(NonOptionalNull.class).unmarshall(
              object(string("embedded"), Json.NULL));

    assertNull(embedding.embedded);
  }

  @Test(expected = IllegalStateException.class)
  public void unmarshallNonOptionalNullBehavior2() throws Exception {
    NonOptionalNull embedding =
        TwoLattes.createMarshaller(NonOptionalNull.class).unmarshall(
              object());

    assertNull(embedding.embedded);
  }

  @Test
  public void unmarshallOptionalNullBehavior() throws Exception {
    OptionalNull embedding =
      TwoLattes.createMarshaller(OptionalNull.class).unmarshall(
            object());

    assertNull(embedding.embedded);
  }

  @Test
  public void unmarshallingEmbeddingOnValueWithNull() throws Exception {
    EmbeddingOnValue embeddingOnValue =
        TwoLattes.createMarshaller(EmbeddingOnValue.class).unmarshall(
            object(string("c"), number(3)));

    assertEquals(0, embeddingOnValue.embedded.a);
    assertEquals(0, embeddingOnValue.embedded.b);
    assertEquals(3, embeddingOnValue.c);
  }

  @Test
  public void unmarshallingEmbeddingOptionalNull() throws Exception {
    EmbeddingOptionalNull embedding =
        TwoLattes.createMarshaller(EmbeddingOptionalNull.class).unmarshall(
            object(string("c"), number(3)));

    assertEquals(3, embedding.c);
    assertNull(embedding.embedded);
  }

  private <T> T unmarshall(Class<T> clazz, String json) throws JSONException {
    Marshaller<T> marshaller = TwoLattes.createMarshaller(clazz);
    return marshaller.unmarshall((Json.Object) Json.fromString(json));
  }

}
