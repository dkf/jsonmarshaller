package com.twolattes.json.types;

/**
 * An abstract implementation dealing with {@code null} and delegating non
 * null cases (null safe) to the concrete implementation.
 * @param <T> the type of object this converter handles
 * @author Pascal
 */
public abstract class NullSafeType<T> implements Type<T> {
  public final Object marshall(T entity) {
    if (entity == null) {
      return null;
    } else {
      return nullSafeMarshall(entity);
    }
  }

  public T unmarshall(Object object) {
    if (object == null) {
      return null;
    } else {
      return nullSafeUnmarshall(object);
    }
  }

  /**
   * Marshalls (see {@link Type#marshall(Object)}) objects which are not null.
   */
  protected abstract Object nullSafeMarshall(T entity);

  /**
   * Unmarshalls (see {@link Type#unmarshall(Object)}) objects which are not
   * null.
   */
  protected abstract T nullSafeUnmarshall(Object object);
}
