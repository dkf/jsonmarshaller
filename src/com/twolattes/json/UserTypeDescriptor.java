package com.twolattes.json;

import org.json.JSONObject;

import com.twolattes.json.types.Type;

/**
 * Descriptor wrapping used defined {@link Type}s.
 *
 * @author pascallouis
 */
class UserTypeDescriptor<T> extends AbstractDescriptor<T, Object> {
  private final Type<T> type;

  public UserTypeDescriptor(Type<T> type) {
    super(type.getReturnedClass());
    this.type = type;
  }

  @Override
  public boolean isInlineable() {
    return false;
  }

  public Object marshall(T entity, String view) {
    if (entity == null) {
      return JSONObject.NULL;
    } else {
      return type.marshall(type.getReturnedClass().cast(entity));
    }
  }

  public T unmarshall(Object marshalled, String view) {
    if (marshalled.equals(JSONObject.NULL)) {
      return null;
    } else {
      return type.unmarshall(marshalled);
    }
  }

  @Override
  public Class<T> getReturnedClass() {
    return type.getReturnedClass();
  }

  @Override
  public boolean shouldInline() {
    return false;
  }
}
