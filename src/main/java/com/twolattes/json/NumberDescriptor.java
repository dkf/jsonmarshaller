package com.twolattes.json;

import static com.twolattes.json.Json.NULL;
import static com.twolattes.json.Json.number;

import java.math.BigDecimal;

/**
 * Abstract descriptor for subtypes of {@link Number}s.
 *
 * @param <N> the type of number
 */
abstract class NumberDescriptor<N extends Number>
    extends AbstractDescriptor<N, Json.Number> {

  public NumberDescriptor(Class<? extends N> klass) {
    super(klass);
  }

  public final Json.Number marshall(N entity, String view) {
    return entity == null ? NULL : number(convert(entity));
  }

  public final N unmarshall(Json.Number entity, String view) {
    return NULL.equals(entity) ? null : convert(entity.getNumber());
  }

  /**
   * Converts an {@code N} into a {@link BigDecimal}. The default implementation
   * uses {@link Number#doubleValue()}.
   */
  protected BigDecimal convert(N entity) {
    return BigDecimal.valueOf(entity.doubleValue());
  }

  /**
   * Convert a {@link BigDecimal} into an {@code N}.
   */
  abstract N convert(BigDecimal entity);
}
