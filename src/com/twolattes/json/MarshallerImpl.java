package com.twolattes.json;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

class MarshallerImpl<T> implements Marshaller<T> {

  private final EntityDescriptor<T> descriptor;
  private final Class<T> clazz;


  @SuppressWarnings("unchecked")
  MarshallerImpl(Class<T> clazz, Map<Type, Class<?>> types) {
    try {
      this.clazz = clazz;
      this.descriptor = new DescriptorFactory().create(
          clazz, new DescriptorFactory.EntityDescriptorStore(), types);
    } catch (IOException e) {
      throw new IllegalArgumentException(clazz + " unreadable");
    }
  }

  public Json.Object marshall(T entity) {
    return marshall(entity, null);
  }

  public Json.Object marshall(T entity, String view) {
    return (Json.Object) descriptor.marshall(entity, view);
  }

  public Json.Array marshallList(Collection<? extends T> entities) {
    return marshallList(entities, null);
  }

  public Json.Array marshallList(Collection<? extends T> entities, String view) {
    Json.Array a = Json.array();
    if (descriptor.shouldInline()) {
      for (T entity : entities) {
        a.add(descriptor.marshallInline(entity, view));
      }
    } else {
      for (T entity : entities) {
        a.add(marshall(entity, view));
      }
    }
    return a;
  }

  public T unmarshall(Json.Object entity) {
    return unmarshall(entity, null);
  }

  public T unmarshall(Json.Object entity, String view) {
    return clazz.cast(descriptor.unmarshall(entity, view));
  }

  public List<T> unmarshallList(Json.Array array) {
    return unmarshallList(array, null);
  }

  public List<T> unmarshallList(Json.Array array, String view) {
    int length = array.size();
    List<T> list = new ArrayList<T>(length);
    if (descriptor.shouldInline()) {
      for (int i = 0; i < length; i++) {
        list.add(descriptor.unmarshallInline(array.get(i), view));
      }
    } else {
      for (int i = 0; i < length; i++) {
        list.add(unmarshall((Json.Object) array.get(i), view));
      }
    }
    return list;
  }

}
