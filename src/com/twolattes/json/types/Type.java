package com.twolattes.json.types;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * JSON type to extend basic marshalling.
 * @author Pascal
 * @version 1.0
 */
public interface Type<T> {
  /**
   * The returned class of this type.
   * @return the returned class of this type
   */
  public Class<T> getReturnedClass();
  
  /**
   * Handles the marshalling on an instance of an entity.
   * @param entity the entity to marshall, it can never be <tt>null</tt>
   * @return a {@link JSONArray},  a {@link JSONObject} or a base type such as
   * {@link Integer} or {@link String} or <tt>float</tt> 
   */
  public Object marshall(T entity);
  
  /**
   * Handles the unmarshalling of an object.
   * @param object an object of the same type of the return type of
   * {@link #marshall(Object)}, it can never be <tt>null</tt>
   * @return the instance of <tt>T</tt>
   */
  public T unmarshall(Object object);
}
