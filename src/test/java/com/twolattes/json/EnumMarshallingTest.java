package com.twolattes.json;

import static com.twolattes.json.Json.NULL;
import static com.twolattes.json.Json.array;
import static com.twolattes.json.Json.object;
import static com.twolattes.json.Json.string;
import static com.twolattes.json.TwoLattes.createMarshaller;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;

import java.util.EnumSet;

import org.junit.Test;

public class EnumMarshallingTest {

  enum ABC { A, B, C }

  Marshaller<ABC> abcMarshaller = createMarshaller(ABC.class);

  @Test
  public void enums() {
    assertEquals(
        NULL,
        abcMarshaller.marshall(null));
    assertEquals(
        string("A"),
        abcMarshaller.marshall(ABC.A));
    assertEquals(
        array(string("A"), string("A"), NULL, string("C")),
        abcMarshaller.marshallList(asList(ABC.A, ABC.A, null, ABC.C)));
    assertEquals(
        array(string("A"), string("B"), string("C")),
        abcMarshaller.marshallList(EnumSet.allOf(ABC.class)));
    assertEquals(
        object(string(""), string("B")),
        abcMarshaller.marshallMap(singletonMap("", ABC.B)));
  }

}
