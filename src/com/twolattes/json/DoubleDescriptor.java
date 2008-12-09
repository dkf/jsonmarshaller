package com.twolattes.json;

import java.math.BigDecimal;

/**
 * Descriptor for the {@link Double} type.
 *
 * @author pascal
 */
class DoubleDescriptor extends NumberDescriptor<Double> {
  DoubleDescriptor() {
    super(Double.class);
  }

  @Override
  Double convert(BigDecimal number) {
    return number.doubleValue();
  }
}
