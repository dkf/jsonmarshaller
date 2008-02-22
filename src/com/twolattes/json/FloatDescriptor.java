package com.twolattes.json;

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
  Float convert(Number entity) {
    return entity.floatValue();
  }
}
