package com.twolattes.json;

import java.math.BigDecimal;

/**
 * Descriptor for the {@link Integer} type.
 *
 * @author pascal
 */
class IntegerDescriptor extends NumberDescriptor<Integer> {
  IntegerDescriptor() {
    super(Integer.class);
  }

  @Override
  Integer convert(BigDecimal entity) {
    return entity.intValue();
  }
}
