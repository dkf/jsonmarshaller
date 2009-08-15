package com.twolattes.json;

import java.lang.reflect.Array;
import java.util.Set;

import com.twolattes.json.DescriptorFactory.EntityDescriptorStore;

/**
 * A proxy to an entity descriptor creating lazily the
 * {@link ConcreteEntityDescriptor} it delegates to.
 */
final class ProxyEntityDescriptor<E> implements EntityDescriptor<E> {
  private final Class<E> klass;
  private final EntityDescriptorStore store;
  private EntityDescriptor<E> descriptor = null;

  ProxyEntityDescriptor(Class<E> klass, EntityDescriptorStore store) {
    this.klass = klass;
    this.store = store;
  }

  public Set<FieldDescriptor> getFieldDescriptors() {
    return getDescriptor().getFieldDescriptors();
  }

  public Set<FieldDescriptor> getAllFieldDescriptors() {
    return getDescriptor().getAllFieldDescriptors();
  }

  public Class<?> getReturnedClass() {
    return getDescriptor().getReturnedClass();
  }

  public boolean isInlineable() {
    return getDescriptor().isInlineable();
  }

  @SuppressWarnings("unchecked")
  public Json.Object marshall(Object entity, String view) {
    return (Json.Object) getDescriptor().marshall((E) entity, view);
  }

  public Json.Object marshall(
      FieldDescriptor fieldDescriptor, Object entity, String view) {
    return (Json.Object) getDescriptor().marshall(fieldDescriptor, entity, view);
  }

  @SuppressWarnings("unchecked")
  public Json.Value marshallArray(Object array, int index, String view) {
    return marshall(Array.get(array, index), view);
  }

  @SuppressWarnings("unchecked")
  public void unmarshallArray(
      Object array, Json.Value value, int index, String view) {
    Array.set(array, index, unmarshall(value, view));
  }

  public Json.Value marshallInline(E entity, String view) {
    return getDescriptor().marshallInline(entity, view);
  }

  public E unmarshall(Json.Value object, String view) {
    return getDescriptor().unmarshall(object, view);
  }

  @SuppressWarnings("unchecked")
  public void unmarshall(Object entity,
      FieldDescriptor fieldDescriptor, Json.Value marshalled, String view) {
    fieldDescriptor.setFieldValue(
        entity, unmarshall(marshalled, view));
  }

  public E unmarshallInline(Json.Value entity, String view) {
    return getDescriptor().unmarshallInline(entity, view);
  }

  public String getDiscriminator() {
    return getDescriptor().getDiscriminator();
  }

  @SuppressWarnings("unchecked")
  private EntityDescriptor<E> getDescriptor() {
    if (descriptor == null) {
      descriptor = store.get(klass);
    }
    return descriptor;
  }

  @Override
  public String toString() {
    return toString(0);
  }

  public String toString(int pad) {
    StringBuilder builder = new StringBuilder();
    builder.append("proxy ");
    EntityDescriptor<E> d = getDescriptor();
    builder.append(d == null ? null : d.toString(pad));
    return builder.toString();
  }
}
