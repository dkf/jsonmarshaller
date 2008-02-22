package com.twolattes.json;


@Entity
public class Abis extends A {
  @Value
  private int a;

  @Override
  public int getA() {
    return a;
  }
}
