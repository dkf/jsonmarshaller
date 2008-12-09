package com.twolattes.json.types;

import org.json.JSONArray;
import org.json.JSONObject;

import com.twolattes.json.Json;

/**
 * JSON type to extend basic marshalling.
 * @author Pascal
 * @version 1.0
 */
public interface JsonType<E, J extends Json.Value> {
  /**
   * The returned class of this type.
   * @return the returned class of this type
   */
  public Class<E> getReturnedClass();

  /**
   * Handles the marshalling on an instance of an entity.
   * @param entity the entity to marshall, it can never be <tt>null</tt>
   * @return a {@link JSONArray},  a {@link JSONObject} or a base type such as
   * {@link Integer} or {@link String} or <tt>float</tt>
   */
  public J marshall(E entity);

  /**
   * Handles the unmarshalling of an object.
   * @param object an object of the same type of the return type of
   * {@link #marshall(Object)}, it can never be <tt>null</tt>
   * @return the instance of <tt>T</tt>
   */
  public E unmarshall(J object);
}
