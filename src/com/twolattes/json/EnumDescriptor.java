package com.twolattes.json;

/**
 * Descriptor for the {@link Enum} type.
 */

@SuppressWarnings("unchecked")
class EnumDescriptor extends AbstractDescriptor<Enum, Json.String> {

  private final Class<? extends Enum> enumClass;

  EnumDescriptor(Class<?> c) {
    super(Enum.class);
    enumClass = (Class<? extends Enum>) c;
  }

  public final Json.String marshall(Enum entity, String view) {
    if (entity == null) {
      return Json.NULL;
    } else {
      return Json.string(entity.name());
    }
  }

  public final Enum<?> unmarshall(Json.String marshalled, String view) {
    return Json.NULL.equals(marshalled) ? null : Enum.valueOf(enumClass,
        marshalled.getString());
  }
}
