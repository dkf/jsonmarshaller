package com.twolattes.json;

import java.math.BigDecimal;

/**
 * Descriptor for the {@link Float} type.
 *
 * @author pascal
 */
class FloatDescriptor extends NumberDescriptor<Float> {
  FloatDescriptor() {
    super(Float.class);
  }

  @Override
  protected BigDecimal convert(Float entity) {
    return new BigDecimal(entity.toString());
  }

  @Override
  Float convert(BigDecimal entity) {
    return entity.floatValue();
  }
}
