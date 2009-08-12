package com.twolattes.json;

import com.twolattes.json.types.JsonType;

interface FieldDescriptor {

  void marshall(Object entity, String view, Json.Object jsonObject);

  void unmarshall(Object entity, String view, Json.Object jsonObject);

  /**
   * Gets the field's descriptor.
   */
  Descriptor<?, ?> getDescriptor();

  /**
   * Gets the field's Java name.
   */
  Json.String getFieldName();

  /**
   * Gets the field's JSON name.
   */
  Json.String getJsonName();

  /**
   * Gets the described field's value.
   */
  Object getFieldValue(Object entity);

  /**
   * Sets the described field's value.
   */
  void setFieldValue(Object entity, Object value);

  /**
   * Whether or not to use the {@link Enum#ordinal()} value to represent enum
   * constants if the field is of type {@link Enum}.
   *
   * @return returns {@code true} if the ordinal representation should be used,
   *         {@code false} otherwise.
   */
  boolean useOrdinal();

  /**
   * The type of the field.
   */
  JsonType<?, ?> getType();

  /**
   * Tests whether the field is in a specific view.
   */
  boolean isInView(String view);

  /**
   * Pretty prints the descriptor.
   */
  String toString(int pad);

}
