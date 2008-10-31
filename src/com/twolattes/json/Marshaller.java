package com.twolattes.json;

import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * JSON Marshaller.
 */
public interface Marshaller<T> {

  /**
   * Marhsall an entity instance to a JSON object.
   * @param entity the entity's instance
   * @return a JSON object
   */
  JSONObject marshall(T entity);

  /**
   * Marhsall an entity instance to a JSON object.
   * @param entity the entity's instance
   * @return a JSON object
   */
  JSONObject marshall(T entity, String view);

  /**
   * Marshall a collection of entities.
   * @param entities the entities to marshall
   * @return a JSONArray containing the marshalled entities
   */
  JSONArray marshallList(Collection<? extends T> entities);

  /**
   * Marshall a collection of entities.
   * @param entities the entities to marshall
   * @return a JSONArray containing the marshalled entities
   */
  JSONArray marshallList(Collection<? extends T> entities, String view);

  /**
   * Unmarshalls a JSON object into a entity.
   * @param entity the JSON object
   * @return an entity
   */
  T unmarshall(JSONObject entity);

  /**
   * Unmarshalls an entity's view.
   * @param entity the entity
   * @param view the view
   * @return an entity
   */
  T unmarshall(JSONObject entity, String view);

  /**
   * Unmarshalls a list of entities.
   * @param array the JSON array containing the entitties
   * @return the list of entities unmarshalled. The order is preserved
   */
  List<T> unmarshallList(JSONArray array);

  /**
   * Unmarshalls a list of entities.
   * @param array the JSON array containing the entitties
   * @return the list of entities unmarshalled. The order is preserved
   * @param view the view
   */
  List<T> unmarshallList(JSONArray array, String view);

}
