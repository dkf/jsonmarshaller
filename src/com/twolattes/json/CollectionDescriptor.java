package com.twolattes.json;

import static com.twolattes.json.CollectionType.fromClass;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Descriptor for {@link Set}s or {@link List}s.
 *
 * @author pascallouis
 */
@SuppressWarnings("unchecked")
class CollectionDescriptor extends AbstractDescriptor<Collection, Json.Array> {
  private final CollectionType collectionType;
  private final Descriptor<Object, Json.Value> collectionDescriptor;

  @SuppressWarnings("unchecked")
  CollectionDescriptor(Class<? extends Collection> collectionClass,
      Descriptor<? extends Object, ? extends Object> collectionDescriptor) {
    super(Collection.class);
    this.collectionDescriptor = (Descriptor<Object, Json.Value>) collectionDescriptor;
    this.collectionType = fromClass(collectionClass);
  }

  @Override
  public final boolean isInlineable() {
    return collectionDescriptor.isInlineable();
  }

  public Json.Array marshall(Collection entity, String view) {
    if (entity == null) {
      return Json.NULL;
    } else {
      Json.Array jsonArray = Json.array();
      if (collectionDescriptor.shouldInline()) {
        for (Object o : entity) {
          jsonArray.add(collectionDescriptor.marshallInline(o, view));
        }
      } else {
        for (Object o : entity) {
          jsonArray.add(collectionDescriptor.marshall(o, view));
        }
      }
      return jsonArray;
    }
  }

  public Collection<?> unmarshall(Json.Array jsonArray, String view) {
    if (Json.NULL.equals(jsonArray)) {
      return null;
    } else {
      Collection<Object> collection = collectionType.newCollection();
      if (collectionDescriptor.shouldInline()) {
        for (int i = 0; i < jsonArray.size(); i++) {
          collection.add(collectionDescriptor.unmarshallInline(jsonArray.get(i), view));
        }
      } else {
        for (int i = 0; i < jsonArray.size(); i++) {
          collection.add(collectionDescriptor.unmarshall(jsonArray.get(i), view));
        }
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
