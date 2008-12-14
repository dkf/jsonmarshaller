package com.twolattes.json.types;

import com.twolattes.json.Json;

/**
 * JSON type to extend basic marshalling.
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
   * @return a {@link Json.Value}
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
