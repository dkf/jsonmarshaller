package com.twolattes.json;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.twolattes.json.Json.Value;

/**
 * A thread-safe <a href="http://www.json.org/">JSON</a> marshaller.
 *
 * @param T the type that this marshaller marshalls and unmarshalls
 */
public interface Marshaller<T> {

  /**
   * Marshalls a {@code T} to its JSON representation.
   * @param object an object to marshall
   * @return a JSON value
   */
  Json.Value marshall(T object);

  /**
   * Marshalls a particular view of a {@code T} to its JSON representation.
   * @param object an object to marshall
   * @param view a view name (see {@link Value#views}) or {@code null}
   * @return a JSON value
   */
  Json.Value marshall(T object, String view);

  /**
   * Marshalls a collection of {@code T} to its JSON array representation.
   * Preserves ordering.
   * @param objects the objects to marshall
   * @return a JSON array
   */
  Json.Array marshallList(Collection<? extends T> objects);

  /**
   * Marshalls a collection of {@code T} to its JSON array representation.
   * Preserves ordering.
   * @param objects the objects to marshall
   * @param view a view name (see {@link Value#views}) or {@code null}
   * @return a JSON array
   */
  Json.Array marshallList(Collection<? extends T> objects, String view);

  /**
   * Marshalls a string-keyed map of {@code T} to its JSON object representation.
   * @param map the map to marshall. Values may be {@code null} but keys may not.
   * @return a JSON object
   */
  Json.Object marshallMap(Map<String, ? extends T> map);

  /**
   * Marshalls a string-keyed map of {@code T} to its JSON object representation.
   * @param map the map to marshall. Values may be {@code null} but keys may not.
   * @param view a view name (see {@link Value#views}) or {@code null}
   * @return a JSON object
   */
  Json.Object marshallMap(Map<String, ? extends T> map, String view);

  /**
   * Unmarshalls the JSON representation of a {@code T}.
   * @param value a JSON value
   * @return the unmarshalled object
   */
  T unmarshall(Json.Value value);

  /**
   * Unmarshalls the JSON representation of a particular view of a {@code T}.
   * @param value a JSON value
   * @param view a view name (see {@link Value#views}) or {@code null}
   * @return the unmarshalled object
   */
  T unmarshall(Json.Value value, String view);

  /**
   * Unmarshalls a JSON array representation of a list of {@code T}. Preserves
   * ordering.
   * @param array a JSON array of entities
   * @return the unmarshalled list
   */
  List<T> unmarshallList(Json.Array array);

  /**
   * Unmarshalls a JSON array representation of a list of {@code T}. Preserves
   * ordering.
   * @param array a JSON array of entities
   * @param view a view name (see {@link Value#views}) or {@code null}
   * @return the unmarshalled list
   */
  List<T> unmarshallList(Json.Array array, String view);

  /**
   * Unmarshalls a JSON object representation of a string-keyed map of {@code T}.
   * @param object a JSON object
   * @return the unmarshalled map
   */
  Map<String, T> unmarshallMap(Json.Object object);

  /**
   * Unmarshalls a JSON object representation of a string-keyed map of {@code T}.
   * @param object a JSON object
   * @param view a view name (see {@link Value#views}) or {@code null}
   * @return the unmarshalled map
   */
  Map<String, T> unmarshallMap(Json.Object object, String view);

}
