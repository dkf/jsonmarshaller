package com.twolattes.json.inheritance4;

import com.twolattes.json.Entity;

@Entity(discriminator = "rectangle")
public class Rectangle extends FourSides {
  public Rectangle() {
    super("rectangle");
  }
}
