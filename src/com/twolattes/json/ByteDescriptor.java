package com.twolattes.json;

import java.math.BigDecimal;

/**
 * Descriptor for the {@link Byte} type.
 */
class ByteDescriptor extends NumberDescriptor<Byte> {

  final static ByteDescriptor BYTE_DESC = new ByteDescriptor(Byte.class);
  final static ByteDescriptor BYTE_LITERAL_DESC = new ByteDescriptor(Byte.TYPE);

  private ByteDescriptor(Class<Byte> klass) {
    super(klass);
  }

  @Override
  protected BigDecimal convert(Byte entity) {
    return new BigDecimal(entity.toString());
  }

  @Override
  Byte convert(BigDecimal entity) {
    return (byte) entity.longValue();
  }

}
