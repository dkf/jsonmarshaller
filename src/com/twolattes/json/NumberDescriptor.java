package com.twolattes.json;

import org.json.JSONObject;

import com.google.common.base.Preconditions;

/**
 * Abstract descriptor for subtypes of {@link Number}s.
 *
 * @param <N> the type of number
 * @author pascallouis
 */
abstract class NumberDescriptor<N extends Number>
    extends AbstractDescriptor<N, Object> {

  public NumberDescriptor(Class<? extends N> klass) {
    super(klass);
  }

  public final Object marshall(N entity, boolean cyclic, String view) {
    if (entity == null) {
      return JSONObject.NULL;
    } else {
      Preconditions.checkState(getReturnedClass().isAssignableFrom(entity.getClass()));
      return entity;
    }
  }

  public final N unmarshall(Object entity, boolean cyclic, String view) {
    if (entity.equals(JSONObject.NULL)) {
      return null;
    } else {
      Preconditions.checkState(entity instanceof Number);
      return convert((Number) entity);
    }
  }

  /**
   * Convert a {@link Number} to a {@code N}.
   */
  abstract N convert(Number entity);
}
