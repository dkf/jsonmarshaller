package com.twolattes.json.types;

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
      return BigDecimal.valueOf((Integer) object);
	} else {
      return BigDecimal.valueOf((Double) object);
	}
  }

  public Class<BigDecimal> getReturnedClass() {
    return BigDecimal.class;
  }
}
