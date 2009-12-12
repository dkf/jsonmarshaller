package com.twolattes.json.inheritance4;

import com.twolattes.json.Entity;

@Entity(discriminator = "circle")
public class Circle extends Shape {
  public Circle() {
    super("circle");
  }
}
