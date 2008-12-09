package com.twolattes.json;


/**
 * Descriptor for the {@link Character} type.
 *
 * @author pascal
 */
class CharacterDescriptor extends AbstractDescriptor<Character, Json.String> {
  CharacterDescriptor() {
    super(Character.class);
  }

  public final Json.String marshall(Character entity, String view) {
    return entity == null ? Json.NULL : Json.string(entity.toString());
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
