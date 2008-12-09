package com.twolattes.json.types;

import static com.twolattes.json.Json.number;

import java.math.BigDecimal;

import com.twolattes.json.Json;
import com.twolattes.json.Value;

/**
 * A type converter for {@link BigDecimal} objects. This converter may be used
 * with the {@link Value#type()} option.
 *
 * @author Pascal
 */
public class BigDecimalType extends NullSafeType<BigDecimal, Json.Number> {
  @Override
  protected Json.Number nullSafeMarshall(BigDecimal entity) {
    return number(entity);
  }

  @Override
  protected BigDecimal nullSafeUnmarshall(Json.Number number) {
    return number.getNumber();
  }

  public Class<BigDecimal> getReturnedClass() {
    return BigDecimal.class;
  }
}
