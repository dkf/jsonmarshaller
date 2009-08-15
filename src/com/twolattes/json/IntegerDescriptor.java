package com.twolattes.json;

import static com.twolattes.json.Json.number;

import java.lang.reflect.Array;
import java.math.BigDecimal;

/**
 * Descriptor for the {@link Integer} type.
 */
class IntegerDescriptor extends NumberDescriptor<Integer> {

  final static IntegerDescriptor INTEGER_DESC = new IntegerDescriptor(Integer.class);
  final static IntegerDescriptor INT_DESC = new IntegerDescriptor(Integer.TYPE) {
    @Override
    public Json.Value marshall(
        FieldDescriptor fieldDescriptor, Object parentEntity, String view) {
      return number(fieldDescriptor.getFieldValueInt(parentEntity));
    }
    @Override
    public void unmarshall(Object entity,
        FieldDescriptor fieldDescriptor, Json.Value marshalled, String view) {
      fieldDescriptor.setFieldValueInt(
          entity, ((Json.Number) marshalled).getNumber().intValueExact());
    }
    @Override
    public Json.Number marshallArray(Object array, int index, String view) {
      return number(Array.getInt(array, index));
    }
    @Override
    public void unmarshallArray(Object array,
        Json.Value value, int index, String view) {
      Array.setInt(array, index,
          ((Json.Number) value).getNumber().intValueExact());
    }
  };

  private IntegerDescriptor(Class<Integer> klass) {
    super(klass);
  }

  @Override
  protected BigDecimal convert(Integer entity) {
    return BigDecimal.valueOf(entity);
  }

  @Override
  Integer convert(BigDecimal entity) {
    return entity.intValue();
  }

}
