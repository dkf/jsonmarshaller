package com.twolattes.json;

import java.lang.reflect.Array;
import java.util.Set;

class InlinedEntityDescriptor<E> implements EntityDescriptor<E> {

  private final EntityDescriptor<E> delegate;

  InlinedEntityDescriptor(EntityDescriptor<E> delegate) {
    this.delegate = delegate;
  }

  public Class<?> getReturnedClass() {
    return delegate.getReturnedClass();
  }

  public boolean isInlineable() {
    return delegate.isInlineable();
  }

  public Json.Value marshall(E entity, String view) {
    return delegate.marshallInline(entity, view);
  }

  @SuppressWarnings("unchecked")
  public Json.Value marshall(
      FieldDescriptor fieldDescriptor, Object entity, String view) {
    return delegate.marshallInline((E) fieldDescriptor.getFieldValue(entity), view);
  }

  @SuppressWarnings("unchecked")
  public Json.Value marshallArray(Object array, int index, String view) {
    return marshall((E) Array.get(array, index), view);
  }

  @SuppressWarnings("unchecked")
  public void unmarshallArray(
      Object array, Json.Value value, int index, String view) {
    Array.set(array, index, unmarshall(value, view));
  }

  public Json.Value marshallInline(E entity, String view) {
    return delegate.marshallInline(entity, view);
  }

  public E unmarshall(Json.Value marshalled, String view) {
    return delegate.unmarshallInline(marshalled, view);
  }

  @SuppressWarnings("unchecked")
  public void unmarshall(Object entity,
      FieldDescriptor fieldDescriptor, Json.Value marshalled, String view) {
    fieldDescriptor.setFieldValue(
        entity, unmarshall(marshalled, view));
  }

  public E unmarshallInline(Json.Value entity, String view) {
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
