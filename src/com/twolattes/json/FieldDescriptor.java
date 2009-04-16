package com.twolattes.json;

import com.twolattes.json.types.JsonType;

interface FieldDescriptor {

  /**
   * Gets the field's descriptor.
   */
  Descriptor<?, ?> getDescriptor();

  /**
   * Gets the field's Java name.
   */
  String getFieldName();

  /**
   * Gets the field's JSON name.
   */
  String getJsonName();

  /**
   * Gets the described field's value.
   */
  Object getFieldValue(Object entity);

  /**
   * Sets the described field's value.
   */
  void setFieldValue(Object entity, Object value);

  /**
   * Returns whether this field should be inlined.
   *
   * @return {@code true} if this field should be inline, {@code false} if this
   *         field should not be inline, {@code null} if the
   *         {@link Value#inline()} was not set on this field
   */
  Boolean getShouldInline();

  /**
   * Whether this field should this field be inlined.
   */
  boolean isOptional();

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

}
