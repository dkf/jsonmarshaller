package com.twolattes.json;

import org.json.JSONObject;

import com.google.common.base.Preconditions;

/**
 * Descriptor for the {@link Boolean} type.
 * 
 * @author pascal
 */
class BooleanDescriptor extends AbstractDescriptor<Boolean, Object> {
  BooleanDescriptor() {
    super(Boolean.class);
  }

  public final Object marshall(Boolean entity, boolean cyclic) {
    if (entity == null) {
      return JSONObject.NULL;
    } else {
      Preconditions.checkState(getReturnedClass().isAssignableFrom(entity.getClass()));
      return entity;
    }
  }

  public final Boolean unmarshall(Object marshalled, boolean cyclic) {
    return (Boolean) marshalled;
  }
}
