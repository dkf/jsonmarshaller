package com.twolattes.json;

/**
 * Descriptor used as a synthesis of an object. An object can be an entity,
 * a collection, a map, an array or a user defined type.
 *
 * @param <E> the type of the entity being described
 * @param <J> the type of the marsahlled entity
 */
interface Descriptor<E, J extends Json.Value> {
  /**
   * Returns the {@link Class} of the object described.
   */
  Class<?> getReturnedClass();

  /**
   * Returns whether the described object is inlineable.
   * @return {@code true} if the object is inlineable, {@code false} otherwise
   */
  boolean isInlineable();

  /**
   * Marshall the described object.
   * @param entity an instance of the described object
   * @param cyclic {@code true} if this instance should be marshalled using
   *     PJSON, {@code false} otherwise
   * @return the marshalled object
   */
  J marshall(E entity, String view);

  J marshallArray(Object array, int index, String view);

  Json.Value marshall(
      FieldDescriptor fieldDescriptor, Object entity, String view);

  J marshallInline(E entity, String view);

  /**
   * Unmarshall the described object.
   * @param marshalled the marshalled object
   * @param cyclic {@code true} if the marshalled object is represented using
   *     PJSON, {@code false} otherwise
   */
  E unmarshall(J marshalled, String view);

  void unmarshallArray(
      Object array, Json.Value value, int index, String view);

  void unmarshall(Object entity,
      FieldDescriptor fieldDescriptor, Json.Value marshalled, String view);

  E unmarshallInline(J entity, String view);

  String toString(int pad);

}
