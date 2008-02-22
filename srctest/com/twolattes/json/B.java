package com.twolattes.json;

@Entity
public class B extends A {
  @Value
  private int b;

  public int getB() {
    return b;
  }
}
