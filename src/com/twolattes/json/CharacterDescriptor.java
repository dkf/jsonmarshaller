package com.twolattes.json;

import static com.twolattes.json.Json.NULL;
import static com.twolattes.json.Json.string;

/**
 * Descriptor for the {@link Character} type.
 */
class CharacterDescriptor extends AbstractDescriptor<Character, Json.String> {

  final static CharacterDescriptor CHARARACTER_DESC = new CharacterDescriptor(Character.class);
  final static CharacterDescriptor CHAR_DESC = new CharacterDescriptor(Character.TYPE);

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
