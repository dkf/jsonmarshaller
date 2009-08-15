package com.twolattes.json;

import java.util.Map.Entry;

import com.twolattes.json.AbstractFieldDescriptor.GetSetFieldDescriptor;
import com.twolattes.json.types.JsonType;

class EmbeddedFieldDescriptor extends DefaultBoxingFieldDescriptor {

  final FieldDescriptor delegate;

  EmbeddedFieldDescriptor(FieldDescriptor fieldDescriptor) {
    this.delegate = fieldDescriptor;
  }

  @SuppressWarnings("unchecked")
  public void marshall(Object entity, String view, Json.Object jsonObject) {
    if (isInView(view)) {
      Object fieldValue = getFieldValue(entity);
      Descriptor descriptor = getDescriptor();
      Json.Value value = descriptor.marshall(fieldValue, view);
      if (!value.equals(Json.NULL)) {
        if (value instanceof Json.Object) {
          Json.Object object = (Json.Object) value;
          for (Entry<Json.String, Json.Value> entry : object.entrySet()) {
            jsonObject.put(entry.getKey(), entry.getValue());
          }
        }
      }
    }
  }

  @SuppressWarnings("unchecked")
  public void unmarshall(Object entity, String view, Json.Object jsonObject) {
    if (isInView(view)) {
      Object value = ((EntityDescriptor) delegate.getDescriptor())
          .unmarshall(jsonObject, view);
      setFieldValue(entity, value);
    }
  }

  public Descriptor<?, ?> getDescriptor() {
    return delegate.getDescriptor();
  }

  public Json.String getFieldName() {
    return delegate.getFieldName();
  }

  public Object getFieldValue(Object entity) {
    return delegate.getFieldValue(entity);
  }

  public Json.String getJsonName() {
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

  public boolean useOrdinal() {
    return delegate.useOrdinal();
  }

  FieldDescriptor getFieldDescriptor() {
    return delegate;
  }

  boolean isGetSetFieldDescriptor() {
    return delegate instanceof GetSetFieldDescriptor;
  }

  @Override
  public String toString() {
    return toString(0);
  }

  public String toString(int pad) {
    StringBuilder builder = new StringBuilder();
    builder.append("embed ");
    builder.append(delegate.toString(pad));
    for (int i = 0; i < pad; i++) {
      builder.append(" ");
    }
    return builder.toString();
  }

}
