package com.twolattes.json;

import static com.twolattes.json.Json.NULL;

import com.twolattes.json.types.JsonType;

/**
 * Descriptor wrapping used defined {@link JsonType}s.
 */
class UserTypeDescriptor<E, J extends Json.Value> extends AbstractDescriptor<E, J> {

  private final JsonType<E, J> type;

  public UserTypeDescriptor(JsonType<E, J> type) {
    super(type.getReturnedClass());
    this.type = type;
  }

  @Override
  public boolean isInlineable() {
    return false;
  }

  @SuppressWarnings("unchecked")
  public J marshall(E entity, String view) {
    if (entity == null) {
      // The Json.Value hierarchy is closed with Null as the bottom type.
      return (J) NULL;
    } else {
      return type.marshall(type.getReturnedClass().cast(entity));
    }
  }

  public E unmarshall(J marshalled, String view) {
    if (marshalled.equals(NULL)) {
      return null;
    } else {
      return type.unmarshall(marshalled);
    }
  }

  @Override
  public Class<E> getReturnedClass() {
    return type.getReturnedClass();
  }

  @Override
  public boolean shouldInline() {
    return false;
  }

}
