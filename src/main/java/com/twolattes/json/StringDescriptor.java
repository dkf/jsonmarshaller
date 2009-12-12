package com.twolattes.json;

import static com.twolattes.json.Json.NULL;
import static com.twolattes.json.Json.string;

/**
 * Descriptor for the {@link String} type.
 */
class StringDescriptor extends AbstractDescriptor<String, Json.String> {

  final static StringDescriptor STRING_DESC = new StringDescriptor();

  private StringDescriptor() {
    super(String.class);
  }

  public final Json.String marshall(String entity, String view) {
    return entity == null ? NULL : string(entity);
  }

  public final String unmarshall(Json.String marshalled, String view) {
    return NULL.equals(marshalled) ? null : marshalled.getString();
  }

}
