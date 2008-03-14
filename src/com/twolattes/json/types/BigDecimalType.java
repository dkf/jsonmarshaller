package com.twolattes.json.types;

import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;

import com.twolattes.json.Value;

/**
 * A type converter for {@link BigDecimal} objects. This converter may be used
 * with the {@link Value#type()} option.
 *
 * @author Pascal
 */
public class BigDecimalType extends NullSafeType<BigDecimal> {
  @Override
  protected Object nullSafeMarshall(BigDecimal entity) {
    return entity.doubleValue();
  }

  @Override
  protected BigDecimal nullSafeUnmarshall(Object object) {
  	if (object instanceof Integer) {
      return valueOf((Integer) object);
  	} else if (object instanceof Long) {
      return valueOf((Long) object);
  	} else {
      return valueOf(((Number) object).doubleValue());
  	}
  }

  public Class<BigDecimal> getReturnedClass() {
    return BigDecimal.class;
  }
}
