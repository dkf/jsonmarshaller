package com.twolattes.json;

import static com.twolattes.json.Json.number;

import java.lang.reflect.Array;
import java.math.BigDecimal;

/**
 * Descriptor for the {@link Float} type.
 */
class FloatDescriptor extends NumberDescriptor<Float> {

  final static FloatDescriptor FLOAT_DESC = new FloatDescriptor(Float.class);
  final static FloatDescriptor FLOAT_LITERAL_DESC = new FloatDescriptor(Float.TYPE) {
    @Override
    public Json.Value marshall(
        FieldDescriptor fieldDescriptor, Object parentEntity, String view) {
      return number(fieldDescriptor.getFieldValueFloat(parentEntity));
    }
    @Override
    public void unmarshall(Object entity,
        FieldDescriptor fieldDescriptor, Json.Value marshalled, String view) {
      fieldDescriptor.setFieldValueFloat(
          entity, ((Json.Number) marshalled).getNumber().floatValue());
    }
    @Override
    public Json.Number marshallArray(Object array, int index, String view) {
      return number(Array.getFloat(array, index));
    }
    @Override
    public void unmarshallArray(Object array,
        Json.Value value, int index, String view) {
      Array.setFloat(array, index,
          ((Json.Number) value).getNumber().floatValue());
    }
  };

  private FloatDescriptor(Class<Float> klass) {
    super(klass);
  }

  @Override
  protected BigDecimal convert(Float entity) {
    return new BigDecimal(entity.toString());
  }

  @Override
  Float convert(BigDecimal entity) {
    return entity.floatValue();
  }

}
