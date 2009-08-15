package com.twolattes.json;

import static com.twolattes.json.Json.FALSE;
import static com.twolattes.json.Json.NULL;
import static com.twolattes.json.Json.TRUE;

import java.lang.reflect.Array;

/**
 * Descriptor for the {@link Boolean} type.
 */
class BooleanDescriptor extends AbstractDescriptor<Boolean, Json.Boolean> {

  final static BooleanDescriptor BOOLEAN_DESC = new BooleanDescriptor(Boolean.class);
  final static BooleanDescriptor BOOLEAN_LITERAL_DESC = new BooleanDescriptor(Boolean.TYPE) {
    @Override
    public Json.Value marshall(
        FieldDescriptor fieldDescriptor, Object parentEntity, String view) {
      return fieldDescriptor.getFieldValueBoolean(parentEntity) ? TRUE : FALSE;
    }
    @Override
    public void unmarshall(Object entity,
        FieldDescriptor fieldDescriptor, Json.Value marshalled, String view) {
      fieldDescriptor.setFieldValueBoolean(
          entity, ((Json.Boolean) marshalled).getBoolean());
    }
    @Override
    public Json.Boolean marshallArray(Object array, int index, String view) {
      return Array.getBoolean(array, index) ? TRUE : FALSE;
    }
    @Override
    public void unmarshallArray(Object array,
        Json.Value value, int index, String view) {
      Array.setBoolean(array, index, ((Json.Boolean) value).getBoolean());
    }
  };

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
