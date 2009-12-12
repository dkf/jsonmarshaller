package com.twolattes.json;

@Entity
public class NullOptionalValue {

  @Value(optional = true)
  private String optional = null;

  @Value(optional = true)
  private Boolean optionalBoolean = null;

  public String getOptional() {
    return optional;
  }

  public Boolean getOptionalBoolean() {
    return optionalBoolean;
  }

  public void setOptional(String optional) {
    this.optional = optional;
  }

}
