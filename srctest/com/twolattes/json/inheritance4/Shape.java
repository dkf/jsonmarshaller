package com.twolattes.json.inheritance4;

import com.twolattes.json.Entity;

@Entity(subclasses = {Circle.class, Rectangle.class, Square.class},
    discriminatorName = "t")
public abstract class Shape {
  public Shape(String name) {
  }
}
