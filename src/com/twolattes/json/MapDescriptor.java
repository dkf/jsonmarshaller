package com.twolattes.json;

import static com.twolattes.json.Json.NULL;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Descriptor for {@link Map}s.
 */
@SuppressWarnings("unchecked")
final class MapDescriptor extends AbstractDescriptor<Map, Json.Object> {

  private final MapType mapType;
  final Descriptor<Object, Json.Value> mapDescriptor;

  MapDescriptor(MapType mapType, Descriptor<?, ?> mapDescriptor) {
    super(Map.class);
    this.mapType = mapType;
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
    return mapType.toClass();
  }

  public Json.Object marshall(Map entity, String view) {
    if (entity == null) {
      return NULL;
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

  public Map<String, ?> unmarshall(Json.Object object, String view) {
    if (object.equals(NULL)) {
      return null;
    } else {
      Map<String, Object> map = mapType.newMap();
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
