package com.twolattes.json;

import static com.twolattes.json.Json.number;

import java.lang.reflect.Array;
import java.math.BigDecimal;

/**
 * Descriptor for the {@link Byte} type.
 */
class ByteDescriptor extends NumberDescriptor<Byte> {

  final static ByteDescriptor BYTE_DESC = new ByteDescriptor(Byte.class);
  final static ByteDescriptor BYTE_LITERAL_DESC = new ByteDescriptor(Byte.TYPE) {
    @Override
    public Json.Value marshall(
        FieldDescriptor fieldDescriptor, Object parentEntity, String view) {
      return number(fieldDescriptor.getFieldValueByte(parentEntity));
    }
    @Override
    public Json.Number marshallArray(Object array, int index, String view) {
      return number(Array.getByte(array, index));
    }
  };

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
