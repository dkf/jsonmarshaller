package com.twolattes.json;

import static com.twolattes.json.Json.FALSE;
import static com.twolattes.json.Json.NULL;
import static com.twolattes.json.Json.TRUE;


/**
 * Descriptor for the {@link Boolean} type.
 */
class BooleanDescriptor extends AbstractDescriptor<Boolean, Json.Boolean> {

  final static BooleanDescriptor BOOLEAN_DESC = new BooleanDescriptor(Boolean.class);
  final static BooleanDescriptor BOOLEAN_LITERAL_DESC = new BooleanDescriptor(Boolean.TYPE);

  private BooleanDescriptor(Class<Boolean> klass) {
    super(klass);
  }

  public final Json.Boolean marshall(Boolean entity, String view) {
    return entity == null ? NULL : entity ? TRUE : FALSE;
  }

  public final Boolean unmarshall(Json.Boolean marshalled, String view) {
    return marshalled.equals(NULL) ? null : marshalled.getBoolean();
  }

}
