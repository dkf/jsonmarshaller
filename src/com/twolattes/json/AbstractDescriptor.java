package com.twolattes.json;


/**
 * Abstract implementation of {@link Descriptor} providing common
 * functionality.
 *
 * @author pascal
 */
abstract class AbstractDescriptor<T, J extends Json.Value> implements Descriptor<T, J> {
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
   * Default implementation.
   * @return {@code false}
   */
  public boolean isEmbeddable() {
    return false;
  }


  /**
   * Default implementation delegating to
   * {@link AbstractDescriptor#marshallInline(Object, boolean)} after
   * verifying that the entity is inlineable.
   */
  public J marshallInline(T entity, String view) {
    return marshall(entity, view);
  }

  /**
   * Default implementation delegating to
   * {@link AbstractDescriptor#unmarshallInline(Object, boolean)} after
   * verifying that the entity is inlineable.
   */
  public T unmarshallInline(J entity, String view) {
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
