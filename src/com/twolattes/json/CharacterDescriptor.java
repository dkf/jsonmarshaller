package com.twolattes.json;

import org.json.JSONObject;

import com.google.common.base.Preconditions;

/**
 * Descriptor for the {@link Character} type.
 *
 * @author pascal
 */
class CharacterDescriptor extends AbstractDescriptor<Character, Object> {
  CharacterDescriptor() {
    super(Character.class);
  }

  public final Object marshall(Character entity, boolean cyclic, String view) {
    if (entity == null) {
      return JSONObject.NULL;
    } else {
      Preconditions.checkState(getReturnedClass().isAssignableFrom(entity.getClass()));
      return entity;
    }
  }

  public final Character unmarshall(Object marshalled, boolean cyclic, String view) {
    String character = (String) marshalled;
    if (character.length() == 1) {
      return character.charAt(0);
    } else {
      throw new RuntimeException("charcter expected, string found " + character);
    }
  }
}
