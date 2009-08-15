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
    public void unmarshall(Object entity,
        FieldDescriptor fieldDescriptor, Json.Value marshalled, String view) {
      fieldDescriptor.setFieldValueByte(
          entity, ((Json.Number) marshalled).getNumber().byteValueExact());
    }
    @Override
    public Json.Number marshallArray(Object array, int index, String view) {
      return number(Array.getByte(array, index));
    }
    @Override
    public void unmarshallArray(Object array,
        Json.Value value, int index, String view) {
      Array.setByte(array, index,
          ((Json.Number) value).getNumber().byteValueExact());
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
