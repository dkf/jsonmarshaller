package com.twolattes.json;

import java.lang.reflect.Array;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.base.Preconditions;

/**
 * A descriptor for arrays.
 *
 * @author pascallouis
 */
class ArrayDescriptor extends AbstractDescriptor<Object, Object> {
  private final Descriptor<Object, Object> elementsDescriptor;

  @SuppressWarnings("unchecked")
  ArrayDescriptor(Descriptor elementsDescriptor) {
    super(Array.class);
    this.elementsDescriptor = elementsDescriptor;
  }

  @Override
  public boolean isInlineable() {
    return elementsDescriptor.isInlineable();
  }

  public Object marshall(Object entity, boolean cyclic) {
    if (entity == null) {
      return JSONArray.NULL;
    }
    Preconditions.checkState(entity instanceof Object[], "array expected");
    JSONArray jsonArray = new JSONArray();
    int l = Array.getLength(entity);
    if (elementsDescriptor.shouldInline()) {
      for (int i = 0; i < l; i++) {
        jsonArray.put(elementsDescriptor.marshallInline(Array.get(entity, i), cyclic));
      }
    } else {
      for (int i = 0; i < l; i++) {
        jsonArray.put(elementsDescriptor.marshall(Array.get(entity, i), cyclic));
      }
    }
    return jsonArray;
  }

  public Object unmarshall(Object object, boolean cyclic) {
    if (JSONObject.NULL.equals(object)) {
      return null;
    }
    Preconditions.checkState(object instanceof JSONArray);
    JSONArray jsonArray = (JSONArray) object;
    Object[] array = (Object[]) Array.newInstance(elementsDescriptor.getReturnedClass(), jsonArray.length());
    try {
      if (elementsDescriptor.shouldInline()) {
        for (int i = 0; i < array.length; i++) {
          array[i] = elementsDescriptor.unmarshallInline(jsonArray.get(i), cyclic);
        }
      } else {
        for (int i = 0; i < array.length; i++) {
          array[i] = elementsDescriptor.unmarshall(jsonArray.get(i), cyclic);
        }
      }
    } catch (JSONException e) {
      throw new IllegalStateException(e);
    }
    return array;
  }

  @Override
  public String toString() {
    return elementsDescriptor + "[]";
  }
}