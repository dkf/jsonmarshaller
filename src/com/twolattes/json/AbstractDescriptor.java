package com.twolattes.json;

import java.lang.reflect.Array;

/**
 * Abstract implementation of {@link Descriptor} providing common
 * functionality.
 */
abstract class AbstractDescriptor<E, J extends Json.Value> implements Descriptor<E, J> {
  private final Class<?> klass;

  /**
   * Default constructor.
   * @param klass the returned class of this descriptor, see
   *     {@link #getReturnedClass()}
   */
  AbstractDescriptor(Class<? extends E> klass) {
    this.klass = klass;
  }

  public Class<?> getReturnedClass() {
    return klass;
  }

  /**
   * Default implementation.
   * @return {@code false}
   */
  public boolean shouldInline() {
    return false;
  }

  /**
   * Default implementation.
   * @return {@code false}
   */
  public boolean isInlineable() {
    return false;
  }

  @SuppressWarnings("unchecked")
  public Json.Value marshall(
      FieldDescriptor fieldDescriptor, Object parentEntity, String view) {
    return marshall((E) fieldDescriptor.getFieldValue(parentEntity), view);
  }

  @SuppressWarnings("unchecked")
  public J marshallArray(Object array, int index, String view) {
    return marshall((E) Array.get(array, index), view);
  }

  /**
   * Default implementation delegating to
   * {@link AbstractDescriptor#marshallInline(Object, boolean)} after
   * verifying that the entity is inlineable.
   */
  public J marshallInline(E entity, String view) {
    return marshall(entity, view);
  }

  /**
   * Default implementation delegating to
   * {@link AbstractDescriptor#unmarshallInline(Object, boolean)} after
   * verifying that the entity is inlineable.
   */
  public E unmarshallInline(J entity, String view) {
    return unmarshall(entity, view);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof Descriptor) {
      Descriptor<?, ?> that = (Descriptor<?, ?>) object;
      return this.getReturnedClass().equals(that.getReturnedClass());
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return this.getReturnedClass().hashCode();
  }

  public String toString(int pad) {
    StringBuilder builder = new StringBuilder();
    builder.append(toString());
    return builder.toString();
  }
}
