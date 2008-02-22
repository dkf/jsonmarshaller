package com.twolattes.json;

/**
 * Descriptor for the {@link Short} type.
 * 
 * @author pascal
 */
class ShortDescriptor extends NumberDescriptor<Short> {
  ShortDescriptor() {
    super(Short.class);
  }

  @Override
  Short convert(Number entity) {
    return entity.shortValue();
  }
}
