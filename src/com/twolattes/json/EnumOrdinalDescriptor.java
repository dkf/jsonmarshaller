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

  EnumOrdinalDescriptor(Class<? extends Enum> c) {
    super(Enum.class);
    enumClass = c;
  }

  public Number marshall(Enum entity, String view) {
    return entity == null ? NULL : number(entity.ordinal());
  }

  public Enum<?> unmarshall(Number marshalled, String view) {
    if (NULL.equals(marshalled)) {
      return null;
    } else {
      try {
        return enumClass.getEnumConstants()[marshalled.getNumber()
            .intValueExact()];
      } catch (ArrayIndexOutOfBoundsException out) {
        throw new IllegalArgumentException("Oridinal value of " + marshalled
            + "is not " + "valid for Enum " + enumClass.getName());
      }
    }
  }

}
