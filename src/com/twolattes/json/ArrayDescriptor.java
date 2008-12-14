package com.twolattes.json;

import java.lang.reflect.Array;

/**
 * A descriptor for arrays.
 *
 * @author pascallouis
 */
class ArrayDescriptor extends AbstractDescriptor<Object, Json.Value> {
  private final Descriptor<Object, Json.Value> elementsDescriptor;

  @SuppressWarnings("unchecked")
  ArrayDescriptor(Descriptor elementsDescriptor) {
    super(Array.class);
    this.elementsDescriptor = elementsDescriptor;
  }

  @Override
  public boolean isInlineable() {
    return elementsDescriptor.isInlineable();
  }

  public Json.Value marshall(Object entity, String view) {
    if (entity == null) {
      return Json.NULL;
    }
    Json.Array jsonArray = Json.array();
    int l = Array.getLength(entity);
    if (elementsDescriptor.shouldInline()) {
      for (int i = 0; i < l; i++) {
        jsonArray.add(elementsDescriptor.marshallInline(Array.get(entity, i), view));
      }
    } else {
      for (int i = 0; i < l; i++) {
        jsonArray.add(elementsDescriptor.marshall(Array.get(entity, i), view));
      }
    }
    return jsonArray;
  }

  public Object unmarshall(Json.Value object, String view) {
    if (Json.NULL.equals(object)) {
      return null;
    }
    Json.Array jsonArray = (Json.Array) object;
    Object array = Array.newInstance(
        elementsDescriptor.getReturnedClass(),
        jsonArray.size());
    int length = Array.getLength(array);
    if (elementsDescriptor.shouldInline()) {
      for (int i = 0; i < length; i++) {
        Array.set(array, i, elementsDescriptor.unmarshallInline(jsonArray.get(i), view));
      }
    } else {
      for (int i = 0; i < length; i++) {
        Array.set(array, i, elementsDescriptor.unmarshall(jsonArray.get(i), view));
      }
    }
    return array;
  }

  @Override
  public String toString() {
    return elementsDescriptor + "[]";
  }
}