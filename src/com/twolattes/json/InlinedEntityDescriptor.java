package com.twolattes.json;

import java.util.Set;

import com.twolattes.json.Json.Value;

class InlinedEntityDescriptor<E> implements EntityDescriptor<E> {

  private final EntityDescriptor<E> delegate;

  InlinedEntityDescriptor(EntityDescriptor<E> delegate) {
    this.delegate = delegate;
  }

  public Class<?> getReturnedClass() {
    return delegate.getReturnedClass();
  }

  public boolean isEmbeddable() {
    return delegate.isEmbeddable();
  }

  public boolean isInlineable() {
    return delegate.isInlineable();
  }

  public Value marshall(E entity, String view) {
    return delegate.marshallInline(entity, view);
  }

  public Value marshallInline(E entity, String view) {
    return delegate.marshallInline(entity, view);
  }

  public E unmarshall(Value marshalled, String view) {
    return delegate.unmarshallInline(marshalled, view);
  }

  public E unmarshallInline(Value entity, String view) {
    return delegate.unmarshallInline(entity, view);
  }

  public String getDiscriminator() {
    return delegate.getDiscriminator();
  }

  public Set<FieldDescriptor> getFieldDescriptors() {
    return delegate.getFieldDescriptors();
  }

  public Set<FieldDescriptor> getAllFieldDescriptors() {
    return delegate.getAllFieldDescriptors();
  }

  @Override
  public String toString() {
    return toString(0);
  }

  public String toString(int pad) {
    StringBuilder builder = new StringBuilder();
    builder.append("inline ");
    builder.append(delegate.toString(pad));
    return builder.toString();
  }

}
