package com.twolattes.json;

import com.google.common.base.Preconditions;

/**
 * Abstract implementation of {@link Descriptor} providing common
 * functionality.
 *
 * @author pascal
 */
abstract class AbstractDescriptor<T, J> implements Descriptor<T, J> {
  private final Class<?> klass;

  /**
   * Default constructor.
   * @param klass the returned class of this descriptor, see
   *     {@link #getReturnedClass()}
   */
  AbstractDescriptor(Class<? extends T> klass) {
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

  /**
   * Default implementation delegating to
   * {@link AbstractDescriptor#marshallInline(Object, boolean)} after
   * verifying that the entity is inlineable.
   */
  public J marshallInline(T entity, boolean cyclic, String view) {
    Preconditions.checkState(isInlineable());
    return marshall(entity, cyclic, view);
  }

  /**
   * Default implementation delegating to
   * {@link AbstractDescriptor#unmarshallInline(Object, boolean)} after
   * verifying that the entity is inlineable.
   */
  public T unmarshallInline(J entity, boolean cyclic, String view) {
    Preconditions.checkState(isInlineable());
    return unmarshall(entity, cyclic, view);
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
}
