package com.twolattes.json;

@Entity
public class GetSetCollidingWithField {
  @Value
  boolean fooBar;

  @Value
  public boolean isFooBar() {
    return fooBar;
  }

  @Value
  public void setFooBar(boolean fooBar) {
    this.fooBar = fooBar;
  }
}
