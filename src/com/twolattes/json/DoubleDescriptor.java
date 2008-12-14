package com.twolattes.json;

import java.math.BigDecimal;

/**
 * Descriptor for the {@link Double} type.
 */
class DoubleDescriptor extends NumberDescriptor<Double> {

  final static DoubleDescriptor DOUBLE_DESC = new DoubleDescriptor(Double.class);
  final static DoubleDescriptor DOUBLE_LITERAL_DESC = new DoubleDescriptor(Double.TYPE);

  private DoubleDescriptor(Class<Double> klass) {
    super(klass);
  }

  @Override
  Double convert(BigDecimal number) {
    return number.doubleValue();
  }

}
