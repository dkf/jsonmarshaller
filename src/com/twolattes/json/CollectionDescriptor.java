package com.twolattes.json;

import static com.twolattes.json.CollectionType.fromClass;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.base.Preconditions;

/**
 * Descriptor for {@link Set}s or {@link List}s.
 *
 * @author pascallouis
 */
@SuppressWarnings("unchecked")
class CollectionDescriptor extends AbstractDescriptor<Collection, Object> {
  private final CollectionType collectionType;
  private final Descriptor<Object, Object> collectionDescriptor;

  @SuppressWarnings("unchecked")
  CollectionDescriptor(Class<? extends Collection> collectionClass,
      Descriptor<? extends Object, ? extends Object> collectionDescriptor) {
    super(Collection.class);
    this.collectionDescriptor = (Descriptor<Object, Object>) collectionDescriptor;
    this.collectionType = fromClass(collectionClass);
  }

  @Override
  public final boolean isInlineable() {
    return collectionDescriptor.isInlineable();
  }

  public Object marshall(Collection entity, boolean cyclic, String view) {
    if (entity == null) {
      return JSONArray.NULL;
    } else {
      JSONArray jsonArray = new JSONArray();
      if (collectionDescriptor.shouldInline()) {
        for (Object o : entity) {
          jsonArray.put(collectionDescriptor.marshallInline(o, cyclic, view));
        }
      } else {
        for (Object o : entity) {
          jsonArray.put(collectionDescriptor.marshall(o, cyclic, view));
        }
      }
      return jsonArray;
    }
  }

  public Collection<?> unmarshall(Object object, boolean cyclic, String view) {
    if (JSONObject.NULL.equals(object)) {
      return null;
    } else {
      Preconditions.checkState(object instanceof JSONArray);
      JSONArray jsonArray = (JSONArray) object;
      Collection<Object> collection = collectionType.newCollection();
      try {
        if (collectionDescriptor.shouldInline()) {
          for (int i = 0; i < jsonArray.length(); i++) {
            collection.add(collectionDescriptor.unmarshallInline(jsonArray.get(i), cyclic, view));
          }
        } else {
          for (int i = 0; i < jsonArray.length(); i++) {
            collection.add(collectionDescriptor.unmarshall(jsonArray.get(i), cyclic, view));
          }
        }
      } catch (JSONException e) {
        throw new IllegalStateException(e);
      }
      return collection;
    }
  }

  @Override
  public Class<?> getReturnedClass() {
    return collectionType.toClass();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof CollectionDescriptor) {
      CollectionDescriptor that = (CollectionDescriptor) obj;
      return this.collectionDescriptor.equals(that.collectionDescriptor);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return collectionDescriptor.hashCode();
  }
}
