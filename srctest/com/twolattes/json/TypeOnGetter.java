package com.twolattes.json;

import static java.math.BigDecimal.TEN;

import java.math.BigDecimal;

import com.twolattes.json.types.BigDecimalType;

@Entity
public class TypeOnGetter {
  private BigDecimal price;
  
  public TypeOnGetter() {
	price = TEN;
  }
  
  @Value(type = BigDecimalType.class)
  public BigDecimal getPrice() {
	return price;
  }
}
