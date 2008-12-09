package com.twolattes.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.twolattes.json.types.JsonType;

/**
 * A JSON value.
 * @author Pascal
 * @version $Id$
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.METHOD})
public @interface Value {
  /**
   * The JSON name. By default, the field name is chosen.
   */
  String name() default "";
  
  /**
   * Whether to inline the value or not. Only base types and single valued
   * entities can be inlided.
   */
  boolean inline() default false;

  /** The type used to marshall and unmarshall this value.
   */
  @SuppressWarnings("unchecked")
  Class<? extends JsonType> type() default JsonType.class;
  
  /**
   * Whether the value is optional or not.
   */
  boolean optional() default false;
  
  /**
   * Views in which this value should be included.
   */
  String[] views() default {};
}
