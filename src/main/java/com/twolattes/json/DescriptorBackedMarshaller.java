package com.twolattes.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DescriptorBackedMarshaller<T, J extends Json.Value> implements Marshaller<T> {

  private final Descriptor<T, J> descriptor;
  private final JsonVisitor<J> visitor;

  DescriptorBackedMarshaller(Descriptor<T, J> descriptor, JsonVisitor<J> visitor) {
    this.descriptor = descriptor;
    this.visitor = visitor;
  }

  public Json.Value marshall(T object) {
    return descriptor.marshall(object, null);
  }

  public Json.Value marshall(T object, String view) {
    return descriptor.marshall(object, view);
  }

  public Json.Array marshallList(Collection<? extends T> objects) {
    Json.Array a = Json.array();
    for (T o : objects) {
      a.add(descriptor.marshall(o, null));
    }
    return a;
  }

  public Json.Array marshallList(Collection<? extends T> objects, String view) {
    Json.Array a = Json.array();
    for (T o : objects) {
      a.add(descriptor.marshall(o, view));
    }
    return a;
  }

  public Json.Object marshallMap(Map<String, ? extends T> map) {
    Json.Object o = Json.object();
    for (String key : map.keySet()) {
      o.put(Json.string(key), descriptor.marshall(map.get(key), null));
    }
    return o;
  }

  public Json.Object marshallMap(Map<String, ? extends T> map, String view) {
    Json.Object o = Json.object();
    for (String key : map.keySet()) {
      o.put(Json.string(key), descriptor.marshall(map.get(key), view));
    }
    return o;
  }

  public T unmarshall(Json.Value value) {
    return unmarshall(value, null);
  }

  public T unmarshall(Json.Value value, String view) {
    return descriptor.unmarshall(value.visit(visitor), view);
  }

  public List<T> unmarshallList(Json.Array array) {
    return unmarshallList(array, null);
  }

  public List<T> unmarshallList(Json.Array array, String view) {
    if (array.isEmpty()) {
      return Collections.emptyList();
    }
    List<T> list = new ArrayList<T>(array.size());
    for (Json.Value value : array) {
      list.add(unmarshall(value, view));
    }
    return list;
  }

  public Map<String, T> unmarshallMap(Json.Object object) {
    return unmarshallMap(object, null);
  }

  public Map<String, T> unmarshallMap(Json.Object object, String view) {
    if (object.isEmpty()) {
      return Collections.emptyMap();
    }
    Map<String, T> map = new HashMap<String, T>(object.size());
    for (Json.String key : object.keySet()) {
      map.put(key.getString(), unmarshall(object.get(key), view));
    }
    return map;
  }

}
