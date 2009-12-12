package com.twolattes.json.types;

import com.twolattes.json.Json;


/**
 * An abstract implementation dealing with {@code null} and delegating non
 * null cases (null safe) to the concrete implementation.
 * @param <E> the type of object this converter handles
 * @author Pascal
 */
public abstract class NullSafeType<E, J extends Json.Value> implements JsonType<E, J> {
  public final J marshall(E entity) {
    if (entity == null) {
      return null;
    } else {
      return nullSafeMarshall(entity);
    }
  }

  public E unmarshall(J object) {
    if (object == null) {
      return null;
    } else {
      return nullSafeUnmarshall(object);
    }
  }

  /**
   * Marshalls (see {@link JsonType#marshall(Object)}) objects which are not null.
   */
  protected abstract J nullSafeMarshall(E entity);

  /**
   * Unmarshalls (see {@link JsonType#unmarshall(Object)}) objects which are not
   * null.
   */
  protected abstract E nullSafeUnmarshall(J object);
}
