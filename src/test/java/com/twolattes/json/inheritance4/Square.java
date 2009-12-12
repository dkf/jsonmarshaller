package com.twolattes.json.inheritance4;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity(discriminator = "square")
public class Square extends FourSides {
  @Value int size;
  
  public Square() {
    super("square");
  }
}
