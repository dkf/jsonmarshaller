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
  final Descriptor<Object, Json.Value> valueDescriptor;

  MapDescriptor(MapType mapType, Descriptor<?, ?> valueDescriptor) {
    super(Map.class);
    this.mapType = mapType;
    this.valueDescriptor = (Descriptor<Object, Json.Value>) valueDescriptor;
  }

  @Override
  public boolean isInlineable() {
    return valueDescriptor.isInlineable();
  }

  @Override
  public String toString() {
    return "Map<" + valueDescriptor.toString() + ">";
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
      for (Entry<String, Object> e : map.entrySet()) {
        o.put(
            Json.string(e.getKey()),
            valueDescriptor.marshall(e.getValue(), view));
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
      while (i.hasNext()) {
        Json.String key = i.next();
        map.put(key.getString(), valueDescriptor.unmarshall(
            object.get(key), view));
      }
      return map;
    }
  }

  Descriptor<Object, Json.Value> getValueDescriptor() {
    return valueDescriptor;
  }

}
