package com.twolattes.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Descriptor for {@link Map}s.
 */
@SuppressWarnings("unchecked")
final class MapDescriptor extends AbstractDescriptor<Map, Json.Object> {
  final Descriptor<Object, Json.Value> mapDescriptor;

  @SuppressWarnings("unchecked")
  MapDescriptor(Descriptor<?, ?> mapDescriptor) {
    super(Map.class);
    this.mapDescriptor = (Descriptor<Object, Json.Value>) mapDescriptor;
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
  public Json.Object marshall(Map entity, String view) {
    if (entity == null) {
      return Json.NULL;
    } else {
      Map<String, Object> map = entity;
      Json.Object o = Json.object();
      if (mapDescriptor.shouldInline()) {
        for (Entry<String, Object> e : map.entrySet()) {
          o.put(
              Json.string(e.getKey()),
              mapDescriptor.marshallInline(e.getValue(), view));
        }
      } else {
        for (Entry<String, Object> e : map.entrySet()) {
          o.put(
              Json.string(e.getKey()),
              mapDescriptor.marshall(e.getValue(), view));
        }
      }
      return o;
    }
  }

  @SuppressWarnings("unchecked")
  public Map<String, ?> unmarshall(Json.Object object, String view) {
    if (object.equals(Json.NULL)) {
      return null;
    } else {
      HashMap<String, Object> map = new HashMap<String, Object>();
      Iterator<Json.String> i = object.keySet().iterator();
      if (mapDescriptor.shouldInline()) {
        while (i.hasNext()) {
          Json.String key = i.next();
          map.put(key.getString(), mapDescriptor.unmarshallInline(
              object.get(key), view));
        }
      } else {
        while (i.hasNext()) {
          Json.String key = i.next();
          map.put(key.getString(), mapDescriptor.unmarshall(
              object.get(key), view));
        }
      }
      return map;
    }
  }
}
