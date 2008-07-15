package com.twolattes.json;

import org.json.JSONObject;

import com.google.common.base.Preconditions;

/**
 * Descriptor for the {@link String} type.
 *
 * @author pascal
 */
class StringDescriptor extends AbstractDescriptor<String, Object> {
  StringDescriptor() {
    super(String.class);
  }

  public final Object marshall(String entity, boolean cyclic, String view) {
    if (entity == null) {
      return JSONObject.NULL;
    } else {
      Preconditions.checkState(getReturnedClass().isAssignableFrom(entity.getClass()));
      return entity;
    }
  }

  public final String unmarshall(Object marshalled, boolean cyclic, String view) {
    if (JSONObject.NULL.equals(marshalled)) {
      return null;
    } else {
      return (String) marshalled;
    }
  }
}
