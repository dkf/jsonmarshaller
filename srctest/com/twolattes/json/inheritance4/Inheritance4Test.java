package com.twolattes.json.inheritance4;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.twolattes.json.Marshaller;

public class Inheritance4Test {
  private Marshaller<Shape> marshaller;

  @Before
  public void start() {
    marshaller =  Marshaller.create(Shape.class);
  }
  
  @Test
  public void marshallSquare() throws Exception {
    JSONObject o = marshaller.marshall(new Square());
    
    assertEquals(1, o.length());
    assertEquals(4, o.get("size"));
  }
}
