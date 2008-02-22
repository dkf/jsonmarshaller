package com.twolattes.json;

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
  Long convert(Number entity) {
    return entity.longValue();
  }
}
