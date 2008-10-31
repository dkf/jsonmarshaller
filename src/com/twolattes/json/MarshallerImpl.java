package com.twolattes.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class MarshallerImpl<T> implements Marshaller<T> {

  private final EntityDescriptor<T> descriptor;
  private final Class<T> clazz;


  @SuppressWarnings("unchecked")
  MarshallerImpl(Class<T> clazz) {
    try {
      this.clazz = clazz;
      this.descriptor = new DescriptorFactory().create(
          clazz, new DescriptorFactory.EntityDescriptorStore());
    } catch (IOException e) {
      throw new IllegalArgumentException(clazz + " unreadable");
    }
  }

  public JSONObject marshall(T entity) {
    return marshall(entity, null);
  }

  public JSONObject marshall(T entity, String view) {
    return (JSONObject) descriptor.marshall(entity, view);
  }

  public JSONArray marshallList(Collection<? extends T> entities) {
    return marshallList(entities, null);
  }

  public JSONArray marshallList(Collection<? extends T> entities, String view) {
    JSONArray a = new JSONArray();
    if (descriptor.shouldInline()) {
      for (T entity : entities) {
        a.put(descriptor.marshallInline(entity, view));
      }
    } else {
      for (T entity : entities) {
        a.put(marshall(entity, view));
      }
    }
    return a;
  }

  public T unmarshall(JSONObject entity) {
    return unmarshall(entity, null);
  }

  public T unmarshall(JSONObject entity, String view) {
    return clazz.cast(descriptor.unmarshall(entity, view));
  }

  public List<T> unmarshallList(JSONArray array) {
    return unmarshallList(array, null);
  }

  public List<T> unmarshallList(JSONArray array, String view) {
    int length = array.length();
    List<T> list = new ArrayList<T>(length);
    try {
      if (descriptor.shouldInline()) {
        for (int i = 0; i < length; i++) {
          list.add(descriptor.unmarshallInline(array.get(i), view));
        }
      } else {
        for (int i = 0; i < length; i++) {
          list.add(unmarshall(array.getJSONObject(i), view));
        }
      }
    } catch (JSONException e) {
      throw new IllegalStateException(e);
    }
    return list;
  }

}
