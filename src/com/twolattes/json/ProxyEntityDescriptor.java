package com.twolattes.json;

import java.util.Set;

import org.json.JSONObject;

import com.twolattes.json.DescriptorFactory.EntityDescriptorStore;

/**
 * A proxy to an entity descriptor creating lazily the
 * {@link ConcreteEntityDescriptor} it delegates to.
 *
 * @author pascallouis
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

  public Class<?> getReturnedClass() {
    return getDescriptor().getReturnedClass();
  }

  public boolean isInlineable() {
    return getDescriptor().isInlineable();
  }

  public JSONObject marshall(Object entity, String view) {
    return getDescriptor().marshall(entity, view);
  }

  public Object marshallInline(T entity, String view) {
    return getDescriptor().marshallInline(entity, view);
  }

  public boolean shouldInline() {
    return getDescriptor().shouldInline();
  }

  public T unmarshall(Object object, String view) {
    return getDescriptor().unmarshall(object, view);
  }

  public T unmarshallInline(Object entity, String view) {
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
}
