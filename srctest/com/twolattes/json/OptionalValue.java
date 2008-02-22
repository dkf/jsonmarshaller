package com.twolattes.json;

@Entity
public class OptionalValue {
  @Value(optional = true)
  private int optional = 4;

  public int getOptional() {
    return optional;
  }

  public void setOptional(int optional) {
    this.optional = optional;
  }
}
