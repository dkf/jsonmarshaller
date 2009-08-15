package com.twolattes.json;

import static com.twolattes.json.Json.NULL;
import static com.twolattes.json.Json.string;

import java.lang.reflect.Array;

/**
 * Descriptor for the {@link Character} type.
 */
class CharacterDescriptor extends AbstractDescriptor<Character, Json.String> {

  final static CharacterDescriptor CHARARACTER_DESC = new CharacterDescriptor(Character.class);
  final static CharacterDescriptor CHAR_DESC = new CharacterDescriptor(Character.TYPE) {
    @Override
    public Json.Value marshall(
        FieldDescriptor fieldDescriptor, Object parentEntity, String view) {
      return string(Character.toString(fieldDescriptor.getFieldValueChar(parentEntity)));
    }
    @Override
    public void unmarshall(Object entity,
        FieldDescriptor fieldDescriptor, Json.Value marshalled, String view) {
      Json.String string = (Json.String) marshalled;
      if (string.getString().length() == 1) {
        fieldDescriptor.setFieldValueChar(
            entity, string.getString().charAt(0));
      } else {
        throw new RuntimeException("character expected");
      }
    }
    @Override
    public Json.String marshallArray(Object array, int index, String view) {
      return string(Character.toString(Array.getChar(array, index)));
    }
    @Override
    public void unmarshallArray(Object array,
        Json.Value value, int index, String view) {
      Json.String string = (Json.String) value;
      if (string.getString().length() == 1) {
        Array.setChar(array, index, string.getString().charAt(0));
      } else {
        throw new RuntimeException("character expected");
      }
    }
  };

  private CharacterDescriptor(Class<Character> klass) {
    super(klass);
  }

  public final Json.String marshall(Character entity, String view) {
    return entity == null ? NULL : string(entity.toString());
  }

  public final Character unmarshall(Json.String marshalled, String view) {
    if (marshalled.equals(Json.NULL)) {
      return null;
    } else {
      if (marshalled.getString().length() == 1) {
        return marshalled.getString().charAt(0);
      } else {
        throw new RuntimeException("character expected");
      }
    }
  }

}
