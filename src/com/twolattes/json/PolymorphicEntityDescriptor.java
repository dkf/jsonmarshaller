package com.twolattes.json;

import static com.twolattes.json.Json.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class PolymorphicEntityDescriptor<T> implements EntityDescriptor<T> {
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

  public boolean isInlineable() {
    return false;
  }

  public boolean shouldInline() {
    return false;
  }

  public Class<?> getReturnedClass() {
    return returnedClass;
  }

  @SuppressWarnings("unchecked")
  public Json.Object marshall(T entity, String view) {
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

  public Json.Object marshallInline(T entity, String view) {
    throw new UnsupportedOperationException();
  }

  @SuppressWarnings("unchecked")
  public T unmarshall(Json.Value value, final String view) {
    return value.visit(new JsonVisitor.Empty<T>() {
      @Override
      public T caseNull() {
        return null;
      }
      @Override
      public T caseObject(Json.Object object) {
        if (!object.containsKey(string(discriminatorName))) {
          throw new IllegalArgumentException(
              "Unmarhsalling polymorphic entity which does not contain the " +
              "discriminator: " + discriminatorName);
        }
        EntityDescriptor<?> descriptor =
            subDescriptorsByDisciminator.get(object.get(string(discriminatorName)));
        return (T) descriptor.unmarshall(object, view);
      }
    });
  }

  public T unmarshallInline(Json.Value entity, String view) {
    throw new UnsupportedOperationException();
  }

}
