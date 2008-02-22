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

  public void init(boolean cyclic) {
    getDescriptor().init(cyclic);
  }

  public boolean isInlineable() {
    return getDescriptor().isInlineable();
  }

  public JSONObject marshall(Object entity, boolean cyclic, String view) {
    return getDescriptor().marshall(entity, cyclic, view);
  }

  public Object marshall(T entity, boolean cyclic) {
    return getDescriptor().marshall(entity, cyclic);
  }

  public Object marshallInline(T entity, boolean cyclic) {
    return getDescriptor().marshallInline(entity, cyclic);
  }

  public boolean shouldInline() {
    return getDescriptor().shouldInline();
  }

  public T unmarshall(Object object, boolean cyclic, String view) {
    return getDescriptor().unmarshall(object, cyclic, view);
  }

  public T unmarshall(Object marshalled, boolean cyclic) {
    return getDescriptor().unmarshall(marshalled, cyclic);
  }

  public T unmarshallInline(Object entity, boolean cyclic) {
    return getDescriptor().unmarshallInline(entity, cyclic);
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
