package com.twolattes.json;

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
  Double convert(Number number) {
    return number.doubleValue();
  }
}
