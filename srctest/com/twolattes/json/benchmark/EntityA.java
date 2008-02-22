package com.twolattes.json.benchmark;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class EntityA {
  @Value
  int a = 9;

  @Value
  double b = 67.9103;

  @Value
  char c = 'f';

  @Value
  String d = "foo, bar";

  @Value
  float e = 8;

  public int getA() {
    return a;
  }

  public void setA(int a) {
    this.a = a;
  }

  public double getB() {
    return b;
  }

  public void setB(double b) {
    this.b = b;
  }

  public char getC() {
    return c;
  }

  public void setC(char c) {
    this.c = c;
  }

  public String getD() {
    return d;
  }

  public void setD(String d) {
    this.d = d;
  }

  public float getE() {
    return e;
  }

  public void setE(float e) {
    this.e = e;
  }
}
