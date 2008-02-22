package com.twolattes.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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
  private boolean isSet;
  private boolean isSetValid = false;
  @SuppressWarnings("unchecked")
  private final Class<? extends Collection> collectionType;
  final Descriptor<Object, Object> collectionDescriptor;

  @SuppressWarnings("unchecked")
  CollectionDescriptor(Class<? extends Collection> collectionType,
      Descriptor<? extends Object, ? extends Object> collectionDescriptor) {
    super(Collection.class);
    this.collectionDescriptor = (Descriptor<Object, Object>) collectionDescriptor;
    this.collectionType = collectionType;
  }

  @Override
  public final boolean isInlineable() {
    return collectionDescriptor.isInlineable();
  }

  private final boolean isSet() {
    if (!isSetValid) {
      isSet = Set.class.isAssignableFrom(getCollectionType());
    }
    return isSet;
  }

  @SuppressWarnings("unchecked")
  Class<? extends Collection> getCollectionType() {
    return collectionType;
  }

  public Object marshall(Collection entity, boolean cyclic) {
    if (entity == null) {
      return JSONArray.NULL;
    } else {
      JSONArray jsonArray = new JSONArray();
      if (collectionDescriptor.shouldInline()) {
        for (Object o : entity) {
          jsonArray.put(collectionDescriptor.marshallInline(o, cyclic));
        }
      } else {
        for (Object o : entity) {
          jsonArray.put(collectionDescriptor.marshall(o, cyclic));
        }
      }
      return jsonArray;
    }
  }

  public Collection<?> unmarshall(Object object, boolean cyclic) {
    if (JSONObject.NULL.equals(object)) {
      return null;
    } else {
      Preconditions.checkState(object instanceof JSONArray);
      JSONArray jsonArray = (JSONArray) object;
      Collection<Object> collection;
      if (isSet()) {
        collection = new HashSet<Object>();
      } else {
        collection = new ArrayList<Object>();
      }
      try {
        if (collectionDescriptor.shouldInline()) {
          for (int i = 0; i < jsonArray.length(); i++) {
            collection.add(collectionDescriptor.unmarshallInline(jsonArray.get(i), cyclic));
          }
        } else {
          for (int i = 0; i < jsonArray.length(); i++) {
            collection.add(collectionDescriptor.unmarshall(jsonArray.get(i), cyclic));
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
    if (isSet()) {
      return Set.class;
    } else {
      return List.class;
    }
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
