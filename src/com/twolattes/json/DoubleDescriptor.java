package com.twolattes.json;

import static com.twolattes.json.Json.number;

import java.lang.reflect.Array;
import java.math.BigDecimal;

/**
 * Descriptor for the {@link Double} type.
 */
class DoubleDescriptor extends NumberDescriptor<Double> {

  final static DoubleDescriptor DOUBLE_DESC = new DoubleDescriptor(Double.class);

  final static DoubleDescriptor DOUBLE_LITERAL_DESC = new DoubleDescriptor(Double.TYPE) {
    @Override
    public Json.Value marshall(
        FieldDescriptor fieldDescriptor, Object parentEntity, String view) {
      return number(fieldDescriptor.getFieldValueDouble(parentEntity));
    }
    @Override
    public Json.Number marshallArray(Object array, int index, String view) {
      return number(Array.getDouble(array, index));
    }
  };

  private DoubleDescriptor(Class<Double> klass) {
    super(klass);
  }

  @Override
  Double convert(BigDecimal number) {
    return number.doubleValue();
  }

}
