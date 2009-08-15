package com.twolattes.json;

import static com.twolattes.json.Json.number;

import java.lang.reflect.Array;
import java.math.BigDecimal;

/**
 * Descriptor for the {@link Short} type.
 */
class ShortDescriptor extends NumberDescriptor<Short> {

  final static ShortDescriptor SHORT_DESC = new ShortDescriptor(Short.class);
  final static ShortDescriptor SHORT_LITERAL_DESC = new ShortDescriptor(Short.TYPE) {
    @Override
    public Json.Value marshall(
        FieldDescriptor fieldDescriptor, Object parentEntity, String view) {
      return number(fieldDescriptor.getFieldValueShort(parentEntity));
    }
    @Override
    public Json.Number marshallArray(Object array, int index, String view) {
      return number(Array.getShort(array, index));
    }
  };

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
