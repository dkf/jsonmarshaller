package com.twolattes.json.inheritance4;

import static com.twolattes.json.Json.string;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.twolattes.json.Json;
import com.twolattes.json.Marshaller;
import com.twolattes.json.TwoLattes;

public class Inheritance4Test {
  private Marshaller<Shape> marshaller;

  @Before
  public void start() {
    marshaller =  TwoLattes.createMarshaller(Shape.class);
  }

  @Test
  public void marshallSquare() throws Exception {
    Json.Object o = marshaller.marshall(new Square());

    assertEquals(1, o.size());
    assertEquals(4, o.get(string("size")));
  }
}
