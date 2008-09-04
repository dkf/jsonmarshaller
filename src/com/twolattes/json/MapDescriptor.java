package com.twolattes.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Descriptor for {@link Map}s.
 *
 * @author pascallouis
 */
@SuppressWarnings("unchecked")
final class MapDescriptor extends AbstractDescriptor<Map, Object> {
  final Descriptor<Object, Object> mapDescriptor;

  @SuppressWarnings("unchecked")
  MapDescriptor(Descriptor<? extends Object, ? extends Object> mapDescriptor) {
    super(Map.class);
    this.mapDescriptor = (Descriptor<Object, Object>) mapDescriptor;
  }

  @Override
  public boolean isInlineable() {
    return mapDescriptor.isInlineable();
  }

  @Override
  public String toString() {
    return "Map<" + mapDescriptor.toString() + ">";
  }

  @Override
  public Class<?> getReturnedClass() {
    return Map.class;
  }

  @SuppressWarnings("unchecked")
  public Object marshall(Map entity, String view) {
    if (entity == null) {
      return JSONObject.NULL;
    } else {
      Map<String, Object> map = entity;
      JSONObject o = new JSONObject();
      try {
        if (mapDescriptor.shouldInline()) {
          for (Entry<String, Object> e : map.entrySet()) {
            o.put(e.getKey(), mapDescriptor.marshallInline(e.getValue(), view));
          }
        } else {
          for (Entry<String, Object> e : map.entrySet()) {
            o.put(e.getKey(), mapDescriptor.marshall(e.getValue(), view));
          }
        }
      } catch (JSONException e) {
        throw new IllegalStateException(e);
      }
      return o;
    }
  }

  @SuppressWarnings("unchecked")
  public Map<String, ?> unmarshall(Object object, String view) {
    if (object.equals(JSONObject.NULL)) {
      return null;
    } else {
      JSONObject o = (JSONObject) object;
      HashMap<String, Object> map = new HashMap<String, Object>();
      Iterator<String> i = o.keys();
      try {
        if (mapDescriptor.shouldInline()) {
          while (i.hasNext()) {
            String key = i.next();
            map.put(key, mapDescriptor.unmarshallInline(o.get(key), view));
          }
        } else {
          while (i.hasNext()) {
            String key = i.next();
            map.put(key, mapDescriptor.unmarshall(o.get(key), view));
          }
        }
      } catch (JSONException e) {
        throw new IllegalStateException(e);
      }
      return map;
    }
  }
}
