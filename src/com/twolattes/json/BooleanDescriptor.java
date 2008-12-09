package com.twolattes.json;


/**
 * Descriptor for the {@link Boolean} type.
 *
 * @author pascal
 */
class BooleanDescriptor extends AbstractDescriptor<Boolean, Json.Boolean> {
  BooleanDescriptor() {
    super(Boolean.class);
  }

  public final Json.Boolean marshall(Boolean entity, String view) {
    return entity == null ? Json.NULL : entity ? Json.TRUE : Json.FALSE;
  }

  public final Boolean unmarshall(Json.Boolean marshalled, String view) {
    if (marshalled.equals(Json.NULL)) {
      return null;
    } else {
      return marshalled.getBoolean();
    }
  }

}
