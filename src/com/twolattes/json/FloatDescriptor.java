package com.twolattes.json;

import java.math.BigDecimal;

/**
 * Descriptor for the {@link Float} type.
 */
class FloatDescriptor extends NumberDescriptor<Float> {

  final static FloatDescriptor FLOAT_DESC = new FloatDescriptor(Float.class);
  final static FloatDescriptor FLOAT_LITERAL_DESC = new FloatDescriptor(Float.TYPE);

  private FloatDescriptor(Class<Float> klass) {
    super(klass);
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
