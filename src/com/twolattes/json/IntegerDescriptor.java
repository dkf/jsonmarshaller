package com.twolattes.json;

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
  Integer convert(Number entity) {
    return entity.intValue();
  }
}
