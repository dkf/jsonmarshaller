package com.twolattes.json;

@Entity
public class NullOptionalValue {
  @Value(optional = true)
  private String optional = null;

  public String getOptional() {
    return optional;
  }

  public void setOptional(String optional) {
    this.optional = optional;
  }

}
