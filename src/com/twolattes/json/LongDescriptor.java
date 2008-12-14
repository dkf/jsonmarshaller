package com.twolattes.json;

import java.math.BigDecimal;

/**
 * Descriptor for the {@link Long} type.
 */
class LongDescriptor extends NumberDescriptor<Long> {

  final static LongDescriptor LONG_DESC = new LongDescriptor(Long.class);
  final static LongDescriptor LONG_LITERAL_DESC = new LongDescriptor(Long.TYPE);

  private LongDescriptor(Class<Long> klass) {
    super(klass);
  }

  @Override
  Long convert(BigDecimal entity) {
    return entity.longValue();
  }

}
