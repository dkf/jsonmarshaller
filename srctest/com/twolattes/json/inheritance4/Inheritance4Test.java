package com.twolattes.json.inheritance4;

import static com.twolattes.json.Json.string;
import static com.twolattes.json.TwoLattes.createEntityMarshaller;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.twolattes.json.EntityMarshaller;
import com.twolattes.json.Json;

public class Inheritance4Test {
  private EntityMarshaller<Shape> marshaller;

  @Before
  public void start() {
    marshaller = createEntityMarshaller(Shape.class);
  }

  @Test
  public void marshallSquare() throws Exception {
    Json.Object o = marshaller.marshall(new Square());

    assertEquals(1, o.size());
    assertEquals(4, o.get(string("size")));
  }
}
