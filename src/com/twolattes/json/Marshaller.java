package com.twolattes.json;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.twolattes.json.types.Type;

/**
 * JSON Marshaller.
 */
public final class Marshaller<T> {
  private final EntityDescriptor<T> descriptor;
  private final Class<T> clazz;
  private final static ThreadLocal<Map<Class<?>, WeakReference<Marshaller<?>>>> marhsallers =
    new ThreadLocal<Map<Class<?>, WeakReference<Marshaller<?>>>>() {
    @Override
    protected Map<Class<?>, WeakReference<Marshaller<?>>> initialValue() {
      return new HashMap<Class<?>, WeakReference<Marshaller<?>>>();
    }
  };
  private final static Map<Class<?>, Class<? extends Type<?>>> types;
  static {
    types = new HashMap<Class<?>, Class<? extends Type<?>>>();
  }

  @SuppressWarnings("unchecked")
  private Marshaller(Class<T> clazz) {
    try {
      this.clazz = clazz;
      this.descriptor = new DescriptorFactory().create(
          clazz, new DescriptorFactory.EntityDescriptorStore());
    } catch (IOException e) {
      throw new IllegalArgumentException(clazz + " unreadable");
    }
  }

  /**
   * Create a JSON Marshaller for the specific entitiy. The entity should be
   * annonated with the {@link Entity} annotation.
   * @param <E> the entitie's type
   * @param c the entities's class
   * @return a JSON Marshaller for the specific entity
   */
  @SuppressWarnings("unchecked")
  public static <E> Marshaller<E> create(Class<E> c) {
    WeakReference<Marshaller<?>> ref = marhsallers.get().get(c);
    Marshaller<E> m = null;
    if (ref != null) {
      m = (Marshaller<E>) ref.get();
    }
    if (m == null) {
      m = new Marshaller<E>(c);
      marhsallers.get().put(c, new WeakReference<Marshaller<?>>(m));
    }
    return m;
  }

  /**
   * Marhsall an entity instance to a JSON object.
   * @param entity the entity's instance
   * @return a JSON object
   */
  public JSONObject marshall(T entity) {
    return marshall(entity, null);
  }

  /**
   * Marhsall an entity instance to a JSON object.
   * @param entity the entity's instance
   * @return a JSON object
   */
  public JSONObject marshall(T entity, String view) {
    return (JSONObject) descriptor.marshall(entity, view);
  }

  /**
   * Marshall a collection of entities.
   * @param entities the entities to marshall
   * @return a JSONArray containing the marshalled entities
   */
  public JSONArray marshallList(Collection<? extends T> entities) {
    return marshallList(entities, null);
  }

  /**
   * Marshall a collection of entities.
   * @param entities the entities to marshall
   * @return a JSONArray containing the marshalled entities
   */
  public JSONArray marshallList(Collection<? extends T> entities, String view) {
    JSONArray a = new JSONArray();
    if (descriptor.shouldInline()) {
      for (T entity : entities) {
        a.put(descriptor.marshallInline(entity, view));
      }
    } else {
      for (T entity : entities) {
        a.put(marshall(entity, view));
      }
    }
    return a;
  }

  /**
   * Unmarshalls a JSON object into a entity.
   * @param entity the JSON object
   * @return an entity
   */
  public T unmarshall(JSONObject entity) {
    return unmarshall(entity, null);
  }

  /**
   * Unmarshall an entity's view.
   * @param entity the entity
   * @param view the view
   * @return an entity
   */
  public T unmarshall(JSONObject entity, String view) {
    return clazz.cast(descriptor.unmarshall(entity, view));
  }

  /**
   * Unmarshall a list of entities.
   * @param array the JSON array containing the entitties
   * @return the list of entities unmarshalled. The order is preserved
   */
  public List<T> unmarshallList(JSONArray array) {
    return unmarshallList(array, null);
  }

  /**
   * Unmarshall a list of entities.
   * @param array the JSON array containing the entitties
   * @return the list of entities unmarshalled. The order is preserved
   * @param view the view
   */
  public List<T> unmarshallList(JSONArray array, String view) {
    int length = array.length();
    List<T> list = new ArrayList<T>(length);
    try {
      if (descriptor.shouldInline()) {
        for (int i = 0; i < length; i++) {
          list.add(descriptor.unmarshallInline(array.get(i), view));
        }
      } else {
        for (int i = 0; i < length; i++) {
          list.add(unmarshall(array.getJSONObject(i), view));
        }
      }
    } catch (JSONException e) {
      throw new IllegalStateException(e);
    }
    return list;
  }

  /**
   * Register a user type instead of annotating each use in the {@link Value} annotation.
   * @param type the user type being registered
   */
  @SuppressWarnings("unchecked")
  public static <T> void register(Type<?> type) {
    types.put(type.getReturnedClass(), (Class<? extends Type<?>>) type.getClass());
  }

  /**
   * Get a type based on the returned class of the type.
   * @param returnedClass the type's returned class
   * @return an instance of a type of <tt>null</null>
   */
  static Type<?> getType(Class<?> returnedClass) {
    Class<? extends Type<?>> type = types.get(returnedClass);
    if (type != null) {
      try {
        return type.newInstance();
      } catch (InstantiationException e) {
        // ignore
      } catch (IllegalAccessException e) {
        // ignore
      }
    }
    return null;
  }
}
