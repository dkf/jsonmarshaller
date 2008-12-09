package com.twolattes.json;

import java.math.BigDecimal;

/**
 * Descriptor for the {@link Long} type.
 *
 * @author pascal
 */
class LongDescriptor extends NumberDescriptor<Long> {
  LongDescriptor() {
    super(Long.class);
  }

  @Override
  Long convert(BigDecimal entity) {
    return entity.longValue();
  }
}
