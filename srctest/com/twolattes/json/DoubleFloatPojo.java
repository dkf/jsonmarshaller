package com.twolattes.json;

@Entity
public class DoubleFloatPojo {
  @Value (name = "dval")
  private Double doubleValue;

  @Value (name = "fval")
  private Float floatValue;

  public Double getDoubleValue() {
    return doubleValue;
  }

  public void setDoubleValue(Double doubleValue) {
    this.doubleValue = doubleValue;
  }

  public Float getFloatValue() {
    return floatValue;
  }

  public void setFloatValue(Float floatValue) {
    this.floatValue = floatValue;
  }
}
