package com.twolattes.json;

import static com.twolattes.json.Json.NULL;
import static com.twolattes.json.Json.string;

/**
 * Descriptor for the {@link Enum} type.
 */
@SuppressWarnings("unchecked")
class EnumNameDescriptor extends AbstractDescriptor<Enum, Json.String> {

  private final Class<? extends Enum> enumClass;

  EnumNameDescriptor(Class<? extends Enum> c) {
    super(Enum.class);
    enumClass = c;
  }

  public final Json.String marshall(Enum entity, String view) {
    return entity == null ? NULL : string(entity.name());
  }

  public final Enum<?> unmarshall(Json.String marshalled, String view) {
    return NULL.equals(marshalled) ? null : Enum.valueOf(enumClass, marshalled
        .getString());
  }

}
