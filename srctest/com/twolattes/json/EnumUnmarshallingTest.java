package com.twolattes.json;

import static com.twolattes.json.Json.NULL;
import static com.twolattes.json.Json.array;
import static com.twolattes.json.Json.number;
import static com.twolattes.json.Json.object;
import static com.twolattes.json.Json.string;
import static com.twolattes.json.TwoLattes.createMarshaller;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EnumUnmarshallingTest {

  enum ABC { A, B, C }

  Marshaller<ABC> abcMarshaller = createMarshaller(ABC.class);

  @Test
  public void enums() {
    assertEquals(
        null,
        abcMarshaller.unmarshall(NULL));
    assertEquals(
        ABC.A,
        abcMarshaller.unmarshall(string("A")));
    assertEquals(
        asList(ABC.A, ABC.A, null, ABC.C),
        abcMarshaller.unmarshallList(
            array(string("A"), string("A"), NULL, string("C"))));
    assertEquals(
        singletonMap("", ABC.B),
        abcMarshaller.unmarshallMap(object(string(""), string("B"))));
  }

  @Test(expected = IllegalArgumentException.class)
  public void numberAsEnum() {
    abcMarshaller.unmarshall(number(1));
  }

}
