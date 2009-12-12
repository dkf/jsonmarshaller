package com.twolattes.json;

import static com.twolattes.json.Json.object;
import static com.twolattes.json.Json.string;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class PolymorphicEntityDescriptor<E> implements EntityDescriptor<E> {
  /**
   * Subclasses' descriptors used if the described entity is a polymorphic
   * entity. The keys are discriminators values.
   */
  private final Map<Json.String, EntityDescriptor<?>> subDescriptorsByDisciminator;

  /**
   * Subclasses' descriptors used if the described entity is a polymorphic
   * entity. The keys are discriminators values.
   */
  private final Map<Class<?>, EntityDescriptor<?>> subDescriptorsByClass;

  /**
   * The returned class of this entity. This class is the superclass of all
   * the entities produced by this polymorphic descriptor.
   */
  private final Class<?> returnedClass;

  /**
   * The name of the discriminator property.
   */
  private final String discriminatorName;

  PolymorphicEntityDescriptor(Class<?> returnedClass,
      String discriminatorName,
      Set<EntityDescriptor<?>> descriptors) {
    this.returnedClass = returnedClass;
    this.discriminatorName = discriminatorName;
    this.subDescriptorsByClass = new HashMap<Class<?>, EntityDescriptor<?>>();
    this.subDescriptorsByDisciminator = new HashMap<Json.String, EntityDescriptor<?>>();
    for (EntityDescriptor<?> descriptor : descriptors) {
      subDescriptorsByClass.put(descriptor.getReturnedClass(), descriptor);
      subDescriptorsByDisciminator.put(
          string(descriptor.getDiscriminator()), descriptor);
    }
  }

  public String getDiscriminator() {
    throw new UnsupportedOperationException();
  }

  public Set<FieldDescriptor> getFieldDescriptors() {
    return new HashSet<FieldDescriptor>();
  }

  @SuppressWarnings("unchecked")
  public Set<FieldDescriptor> getAllFieldDescriptors() {
    Set<FieldDescriptor> result = new HashSet<FieldDescriptor>();
    for (EntityDescriptor entityDescriptor : subDescriptorsByClass.values()) {
      result.addAll(entityDescriptor.getAllFieldDescriptors());
    }
    return result;
  }

  public boolean isEmbeddable() {
    return false;
  }

  public boolean isInlineable() {
    for (EntityDescriptor<?> descriptor : subDescriptorsByClass.values()) {
      if (!descriptor.getFieldDescriptors().isEmpty()) {
        return false;
      }
    }
    return true;
  }

  public boolean shouldInline() {
    return false;
  }

  public Class<?> getReturnedClass() {
    return returnedClass;
  }

  String getDiscriminatorName() {
    return discriminatorName;
  }

  @SuppressWarnings("unchecked")
  public Json.Object marshall(E entity, String view) {
    // null
    if (entity == null) {
      return Json.NULL;
    }

    // null safe
    EntityDescriptor<Object> descriptor =
        (EntityDescriptor<Object>) subDescriptorsByClass.get(entity.getClass());
    if (descriptor == null) {
      throw new IllegalArgumentException(
          "Unmarshalled entity of class " + entity.getClass() + "is not " +
          "a valid subclass entity of " + returnedClass);
    }
    Json.Object jsonObject = (Json.Object) descriptor.marshall(entity, view);
    jsonObject.put(
        string(discriminatorName),
        string(descriptor.getDiscriminator()));
    return jsonObject;
  }

  @SuppressWarnings("unchecked")
  public Json.Value marshall(
      FieldDescriptor fieldDescriptor, Object entity, String view) {
    return marshall((E) fieldDescriptor.getFieldValue(entity), view);
  }

  @SuppressWarnings("unchecked")
  public Json.Value marshallArray(Object array, int index, String view) {
    return marshall((E) Array.get(array, index), view);
  }

  @SuppressWarnings("unchecked")
  public void unmarshallArray(
      Object array, Json.Value value, int index, String view) {
    Array.set(array, index, unmarshall(value, view));
  }

  @SuppressWarnings("unchecked")
  public Json.String marshallInline(E entity, String view) {
    // null
    if (entity == null) {
      return Json.NULL;
    }

    // null safe
    EntityDescriptor<Object> descriptor =
        (EntityDescriptor<Object>) subDescriptorsByClass.get(entity.getClass());

    return string(descriptor.getDiscriminator());
  }

  @SuppressWarnings("unchecked")
  public E unmarshall(Json.Value value, final String view) {
    return value.visit(new JsonVisitor.Empty<E>() {
      @Override
      public E caseNull() {
        return null;
      }
      @Override
      public E caseObject(Json.Object object) {
        if (!object.containsKey(string(discriminatorName))) {
          throw new IllegalArgumentException(
              "Unmarhsalling polymorphic entity which does not contain the " +
              "discriminator: " + discriminatorName);
        }
        EntityDescriptor<?> descriptor =
            subDescriptorsByDisciminator.get(object.get(string(discriminatorName)));
        return (E) descriptor.unmarshall(object, view);
      }
    });
  }

  @SuppressWarnings("unchecked")
  public void unmarshall(Object entity,
      FieldDescriptor fieldDescriptor, Json.Value marshalled, String view) {
    fieldDescriptor.setFieldValue(
        entity, unmarshall(marshalled, view));
  }

  @SuppressWarnings("unchecked")
  public E unmarshallInline(Json.Value entity, final String view) {
    return entity.visit(new JsonVisitor.Empty<E>() {
      @Override
      public E caseNull() {
        return null;
      }
      @Override
      public E caseString(Json.String discriminator) {
        EntityDescriptor<?> descriptor =
            subDescriptorsByDisciminator.get(discriminator);
        return (E) descriptor.unmarshall(
            object(string(discriminatorName), discriminator), view);
      }
    });
  }

  @Override
  public String toString() {
    return toString(0);
  }

  public String toString(int pad) {
    StringBuilder builder = new StringBuilder();
    builder.append("PolymorphicEntityDescriptor<" + getReturnedClass().getSimpleName() + ">\n");
    return builder.toString();
  }

}
