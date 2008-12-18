package com.twolattes.json;

import java.math.BigDecimal;

class BigDecimalDescriptor extends NumberDescriptor<BigDecimal> {

  final static BigDecimalDescriptor BIG_DECIMAL_DESC = new BigDecimalDescriptor();

  private BigDecimalDescriptor() {
    super(BigDecimal.class);
  }

  @Override
  protected BigDecimal convert(BigDecimal entity) {
    return entity;
  }

}
