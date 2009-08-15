package com.twolattes.json;

import static com.twolattes.json.Json.number;

import java.lang.reflect.Array;
import java.math.BigDecimal;

/**
 * Descriptor for the {@link Long} type.
 */
class LongDescriptor extends NumberDescriptor<Long> {

  final static LongDescriptor LONG_DESC = new LongDescriptor(Long.class);
  final static LongDescriptor LONG_LITERAL_DESC = new LongDescriptor(Long.TYPE) {
    @Override
    public Json.Value marshall(
        FieldDescriptor fieldDescriptor, Object parentEntity, String view) {
      return number(fieldDescriptor.getFieldValueLong(parentEntity));
    }
    @Override
    public Json.Number marshallArray(Object array, int index, String view) {
      return number(Array.getLong(array, index));
    }
  };

  private LongDescriptor(Class<Long> klass) {
    super(klass);
  }

  @Override
  protected BigDecimal convert(Long entity) {
    return BigDecimal.valueOf(entity);
  }

  @Override
  Long convert(BigDecimal entity) {
    return entity.longValue();
  }

}
