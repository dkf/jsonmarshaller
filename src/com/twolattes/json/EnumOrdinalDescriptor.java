package com.twolattes.json;

import static com.twolattes.json.Json.NULL;
import static com.twolattes.json.Json.number;

import com.twolattes.json.Json.Number;

/**
 * Descriptor for the {@link Enum} type.
 */
@SuppressWarnings("unchecked")
class EnumOrdinalDescriptor extends AbstractDescriptor<Enum, Json.Number> {

  private final Class<? extends Enum> enumClass;

  private final Enum[] constants;

  EnumOrdinalDescriptor(Class<? extends Enum> c) {
    super(Enum.class);
    enumClass = c;
    constants = enumClass.getEnumConstants();
  }

  public Number marshall(Enum entity, String view) {
    return entity == null ? NULL : number(entity.ordinal());
  }

  public Enum<?> unmarshall(Number marshalled, String view) {
    int index;
    if (NULL.equals(marshalled)) {
      return null;
    } else {
      try {
        index = marshalled.getNumber().intValueExact();
      } catch (ArithmeticException ex) {
        throw new IllegalArgumentException("The ordinal value " + marshalled
            + "is not an integer.");
      }
      if (index < 0 || index >= constants.length) {
        throw new IllegalArgumentException("Oridinal value of " + marshalled
            + "is not valid for Enum " + enumClass.getName());
      }
      return constants[index];
    }
  }
}