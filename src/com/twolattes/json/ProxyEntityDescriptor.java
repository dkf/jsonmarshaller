package com.twolattes.json;

import java.util.Set;

import com.twolattes.json.DescriptorFactory.EntityDescriptorStore;

/**
 * A proxy to an entity descriptor creating lazily the
 * {@link ConcreteEntityDescriptor} it delegates to.
 */
final class ProxyEntityDescriptor<T> implements EntityDescriptor<T> {
  private final Class<T> klass;
  private final EntityDescriptorStore store;
  private EntityDescriptor<T> descriptor = null;

  ProxyEntityDescriptor(Class<T> klass, EntityDescriptorStore store) {
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
    return (Json.Object) getDescriptor().marshall((T) entity, view);
  }

  public Json.Value marshallInline(T entity, String view) {
    return getDescriptor().marshallInline(entity, view);
  }

  public T unmarshall(Json.Value object, String view) {
    return getDescriptor().unmarshall(object, view);
  }

  public T unmarshallInline(Json.Value entity, String view) {
    return getDescriptor().unmarshallInline(entity, view);
  }

  public String getDiscriminator() {
    return getDescriptor().getDiscriminator();
  }

  @SuppressWarnings("unchecked")
  private EntityDescriptor<T> getDescriptor() {
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
    EntityDescriptor<T> d = getDescriptor();
    builder.append(d == null ? null : d.toString(pad));
    return builder.toString();
  }
}
