package com.twolattes.json;

import java.math.BigDecimal;

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
  Short convert(BigDecimal entity) {
    return entity.shortValue();
  }
}
