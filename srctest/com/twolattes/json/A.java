package com.twolattes.json;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class A {
  @Value
  private int a;

  public int getA() {
    return a;
  }
}
