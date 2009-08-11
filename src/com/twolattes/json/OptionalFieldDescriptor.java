package com.twolattes.json;

import static com.twolattes.json.Json.string;

import com.twolattes.json.types.JsonType;

class OptionalFieldDescriptor implements FieldDescriptor {

  final FieldDescriptor delegate;

  OptionalFieldDescriptor(FieldDescriptor delegate) {
    this.delegate = delegate;
  }

  @SuppressWarnings("unchecked")
  public void marshall(Object entity, String view,
      com.twolattes.json.Json.Object jsonObject) {
    if (isInView(view)) {
      Object fieldValue = getFieldValue(entity);
      if (fieldValue != null) {
        Descriptor descriptor = getDescriptor();
        jsonObject.put(
            string(getJsonName()), descriptor.marshall(fieldValue, view));
      }
    }
  }

  public void unmarshall(Object entity, String view,
      com.twolattes.json.Json.Object jsonObject) {
    if (jsonObject.containsKey(string(getJsonName()))) {
      delegate.unmarshall(entity, view, jsonObject);
    }
  }

  public Descriptor<?, ?> getDescriptor() {
    return delegate.getDescriptor();
  }

  public String getFieldName() {
    return delegate.getFieldName();
  }

  public Object getFieldValue(Object entity) {
    return delegate.getFieldValue(entity);
  }

  public String getJsonName() {
    return delegate.getJsonName();
  }

  public JsonType<?, ?> getType() {
    return delegate.getType();
  }

  public boolean isInView(String view) {
    return delegate.isInView(view);
  }

  public void setFieldValue(Object entity, Object value) {
    delegate.setFieldValue(entity, value);
  }

  public String toString(int pad) {
    return delegate.toString(pad);
  }

  public boolean useOrdinal() {
    return delegate.useOrdinal();
  }

}
