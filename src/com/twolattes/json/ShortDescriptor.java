package com.twolattes.json;

import java.math.BigDecimal;

/**
 * Descriptor for the {@link Short} type.
 */
class ShortDescriptor extends NumberDescriptor<Short> {

  final static ShortDescriptor SHORT_DESC = new ShortDescriptor(Short.class);
  final static ShortDescriptor SHORT_LITERAL_DESC = new ShortDescriptor(Short.TYPE);

  private ShortDescriptor(Class<Short> klass) {
    super(klass);
  }

  @Override
  protected BigDecimal convert(Short entity) {
    return BigDecimal.valueOf(entity);
  }

  @Override
  Short convert(BigDecimal entity) {
    return entity.shortValue();
  }

}
