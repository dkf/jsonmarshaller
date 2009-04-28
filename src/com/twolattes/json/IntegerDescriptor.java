package com.twolattes.json;

import java.math.BigDecimal;

/**
 * Descriptor for the {@link Integer} type.
 */
class IntegerDescriptor extends NumberDescriptor<Integer> {

  final static IntegerDescriptor INTEGER_DESC = new IntegerDescriptor(Integer.class);
  final static IntegerDescriptor INT_DESC = new IntegerDescriptor(Integer.TYPE);

  private IntegerDescriptor(Class<Integer> klass) {
    super(klass);
  }

  @Override
  protected BigDecimal convert(Integer entity) {
    return BigDecimal.valueOf(entity);
  }

  @Override
  Integer convert(BigDecimal entity) {
    return entity.intValue();
  }

}
