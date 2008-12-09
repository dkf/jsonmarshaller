package com.twolattes.json;

/**
 * Descriptor for the {@link String} type.
 */
class StringDescriptor extends AbstractDescriptor<String, Json.String> {
  StringDescriptor() {
    super(String.class);
  }

  public final Json.String marshall(String entity, String view) {
    if (entity == null) {
      return Json.NULL;
    } else {
      return Json.string(entity);
    }
  }

  public final String unmarshall(Json.String marshalled, String view) {
    return Json.NULL.equals(marshalled) ? null : marshalled.getString();
  }
}
