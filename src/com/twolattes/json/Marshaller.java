package com.twolattes.json;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * JSON Marshaller.
 *
 * @param T the type of entity that this marshaller marshalls and unmarshalls
 */
public interface Marshaller<T> {

  /**
   * Marshalls an entity to its JSON object representation.
   * @param entity an entity to marshall
   * @return a JSON object
   */
  Json.Object marshall(T entity);

  /**
   * Marshalls a particular view of an entity to its JSON object representation.
   * @param entity an entity to marshall
   * @param view a view name (see {@link Value#views})
   * @return a JSON object
   */
  Json.Object marshall(T entity, String view);

  /**
   * Marshalls a collection of entities to its JSON array representation.
   * Preserves ordering.
   * @param entities the entities to marshall
   * @return a JSON array
   */
  Json.Array marshallList(Collection<? extends T> entities);

  /**
   * Marshalls a collection of entities to its JSON array representation.
   * Preserves ordering.
   * @param entities the entities to marshall
   * @param view a view name (see {@link Value#views})
   * @return a JSON array
   */
  Json.Array marshallList(Collection<? extends T> entities, String view);

  /**
   * Marshalls a string-keyed map of entities to its JSON object representation.
   * @param map the map of entities to marshall
   * @return a JSON object
   */
  Json.Object marshallMap(Map<String, ? extends T> map);

  /**
   * Marshalls a string-keyed map of entities to its JSON object representation.
   * @param map the map of entities to marshall
   * @param view a view name (see {@link Value#views})
   * @return a JSON object
   */
  Json.Object marshallMap(Map<String, ? extends T> map, String view);

  /**
   * Unmarshalls the JSON representation of an entity.
   * @param entity a JSON object
   * @return the unmarshalled entity
   */
  T unmarshall(Json.Object entity);

  /**
   * Unmarshalls the JSON representation of a particular view of an entity.
   * @param entity a JSON object
   * @param view a view name (see {@link Value#views})
   * @return the unmarshalled entity
   */
  T unmarshall(Json.Object entity, String view);

  /**
   * Unmarshalls a JSON array representation of a list of entities. Preserves
   * ordering.
   * @param array a JSON array of entities
   * @return the unmarshalled list of entities
   */
  List<T> unmarshallList(Json.Array array);

  /**
   * Unmarshalls a JSON array representation of a list of entities. Preserves
   * ordering.
   * @param array a JSON array of entities
   * @param view a view name (see {@link Value#views})
   * @return the unmarshalled list of entities
   */
  List<T> unmarshallList(Json.Array array, String view);

  /**
   * Unmarshalls a JSON object representation of a string-keyed entity map.
   * @param object a JSON object whose values are entities
   * @return the unmarshalled entity map
   */
  Map<String, T> unmarshallMap(Json.Object object);

   /**
    * Unmarshalls a JSON object representation of a string-keyed entity map.
    * @param object a JSON object whose values are entities
    * @param view a view name (see {@link Value#views})
    * @return the unmarshalled entity map
    */
   Map<String, T> unmarshallMap(Json.Object object, String view);

}
