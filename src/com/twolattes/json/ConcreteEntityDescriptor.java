package com.twolattes.json;

import static com.twolattes.json.Json.NULL;
import static com.twolattes.json.Json.object;
import static com.twolattes.json.Json.string;
import static java.lang.String.format;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An entity descriptor.
 */
final class ConcreteEntityDescriptor<T> extends AbstractDescriptor<T, Json.Value>
    implements EntityDescriptor<T> {

  private final Class<T> entity;
  private final Set<FieldDescriptor> fieldDescriptors;
  private final String discriminator;
  private final ConcreteEntityDescriptor<?> parent;
  private final Constructor<T> constructor;

  /**
   * Creates an entity descriptor.
   * @param entity the described entity's type
   * @param fieldDescriptors the entity's field descriptors
   */
  @SuppressWarnings("unchecked")
  ConcreteEntityDescriptor(Class<T> entity,
      Set<FieldDescriptor> fieldDescriptors,
      ConcreteEntityDescriptor<?> parent) {
    super(entity);
    Entity annotation = entity.getAnnotation(Entity.class);
    this.discriminator = annotation.discriminator();
    this.entity = entity;
    this.fieldDescriptors = fieldDescriptors;
    this.parent = parent;

    // implementation of the entity
    Class<?> implementedBy = annotation.implementedBy();
    if (!implementedBy.equals(Object.class)) {
      if (!entity.isAssignableFrom(implementedBy)) {
        throw new IllegalArgumentException(
            format("%s's implementedBy must reference a subclass.",
                entity.toString()));
      }
    } else {
      implementedBy = entity;
    }

    // getting the no arg constructor and making it accessible
    try {
      this.constructor = (Constructor<T>) implementedBy.getDeclaredConstructor();
      this.constructor.setAccessible(true);
    } catch (SecurityException e) {
      throw new IllegalArgumentException(
          format("%s's constructor cannot be accessed.", implementedBy));
    } catch (NoSuchMethodException e) {
      throw new IllegalArgumentException(
          format("%s does not have a no argument constructor.", implementedBy));
    }

    // name conflicts
    Set<String> names = new HashSet<String>();
    parent = this;
    while (parent != null) {
      for (FieldDescriptor descriptor : parent.fieldDescriptors) {
        if (names.contains(descriptor.getJsonName())) {
          throw new IllegalArgumentException(
              format("Field with JSON name %s in %s collides with field in " +
              		"class %s", descriptor.getJsonName(), entity, parent.getReturnedClass()));
        } else {
          names.add(descriptor.getJsonName());
        }
      }
      parent = parent.parent;
    }
  }

  @Override
  public Class<?> getReturnedClass() {
    return entity;
  }

  /**
   * Gets the field descriptors of this entity.
   */
  public Set<FieldDescriptor> getFieldDescriptors() {
    return fieldDescriptors;
  }

  @SuppressWarnings("unchecked")
  public Set<FieldDescriptor> getAllFieldDescriptors() {
    Set<FieldDescriptor> result = new HashSet<FieldDescriptor>();
    for (FieldDescriptor fieldDescriptor : fieldDescriptors) {
      if (fieldDescriptor instanceof EmbeddedFieldDescriptor) {
        result.addAll(
            ((EntityDescriptor) fieldDescriptor.getDescriptor())
                .getFieldDescriptors());
      } else {
        result.add(fieldDescriptor);
      }
    }
    return result;
  }

  @Override
  public boolean isInlineable() {
    return fieldDescriptors.size() == 1;
  }

  public Json.Object marshall(Object entity) {
    return marshall(entity, null);
  }

  public Json.Object marshall(Object entity, String view) {
    if (entity == null) {
      return Json.NULL;
    }

    // entity is not null
    Json.Object jsonObject = Json.object();
    marshallFields(entity, view, jsonObject);
    return jsonObject;
  }

  private void marshallFields(
      Object entity, String view, Json.Object jsonObject) {
    if (parent != null) {
      parent.marshallFields(entity, view, jsonObject);
    }
    for (FieldDescriptor d : getFieldDescriptors()) {
      d.marshall(entity, view, jsonObject);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public Json.Value marshallInline(Object entity, String view) {
    if (entity == null) {
      return Json.NULL;
    }
    FieldDescriptor d = fieldDescriptors.iterator().next();
    Descriptor descriptor = d.getDescriptor();
    return descriptor.marshall(d.getFieldValue(entity), view);
  }

  public T unmarshall(Json.Value object) {
    return unmarshall(object, null);
  }

  @SuppressWarnings("unchecked")
  public T unmarshall(Json.Value object, String view) {
    if (Json.NULL.equals(object)) {
      return null;
    }
    try {
      // is this a polymorphic entity?
      Object entity = constructor.newInstance();
      unmarshallFields((Json.Object) object, entity, view);
      return (T) entity;
    } catch (InstantiationException e) {
      throw new IllegalStateException(e);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException(e);
    } catch (SecurityException e) {
      throw new IllegalStateException(e);
    } catch (InvocationTargetException e) {
      throw new IllegalStateException(e);
    }
  }

 /**
  * If the getter/setter for a field has a different @Value(name) annotation than the actual field, the getter/setter
  * will have a different name, and looking for it by the field name will return a null, and trying to invoke the
  * getter/setter will generate a NullPointerException.
  * Using only descriptors for actual fields guarantees that it will be possible to set the field's value.
  *
  * @return a set of <code>FieldDescriptor</code>s that correspond to actual fields.
  */
  private Set<FieldDescriptor> getDescriptorsByFieldName() {
      Map<String, FieldDescriptor> descriptors = new HashMap<String, FieldDescriptor>();
      for (FieldDescriptor d : getFieldDescriptors()) {
          if (!descriptors.containsKey(d.getFieldName()) ) {
              descriptors.put(d.getFieldName(), d);
          }
      }
      return new HashSet<FieldDescriptor>(descriptors.values());
  }

  private void unmarshallFields(Json.Object jsonObject, Object entity, String view) {
    if (parent != null) {
      parent.unmarshallFields(jsonObject, entity, view);
    }
    for (FieldDescriptor d : getDescriptorsByFieldName()) {
      d.unmarshall(entity, view, jsonObject);
    }
  }

  @Override
  public T unmarshallInline(final Json.Value entity, String view) {
    if (NULL.equals(entity)) {
      return null;
    } else {
      Json.Object jsonObject = object();
    	FieldDescriptor d = fieldDescriptors.iterator().next();
      jsonObject.put(string(d.getJsonName()), entity);
      return unmarshall(jsonObject);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof EntityDescriptor) {
      EntityDescriptor<?> that = (EntityDescriptor<?>) obj;
      for (FieldDescriptor fd : this.getFieldDescriptors()) {
        if (!that.getFieldDescriptors().contains(fd)) {
          return false;
        }
      }
      return
        this.getFieldDescriptors().size() == that.getFieldDescriptors().size() &&
        this.getReturnedClass().equals(that.getReturnedClass());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return getReturnedClass().hashCode();
  }

  @Override
  public String toString() {
    return toString(0);
  }

  @Override
  public String toString(int pad) {
    StringBuilder builder = new StringBuilder();
    builder.append(
        format("ConcreteEntityDescriptor<%s> {\n",
            getReturnedClass().getSimpleName()));
    for (FieldDescriptor f : fieldDescriptors) {
      for (int i = 0; i < pad + 2; i++) {
        builder.append(" ");
      }
      builder.append(f.toString(pad + 2) + "\n");
    }
    for (int i = 0; i < pad; i++) {
      builder.append(" ");
    }
    builder.append("}");
    return builder.toString();
  }

  public String getDiscriminator() {
    if (discriminator == null || discriminator.length() == 0) {
      throw new IllegalArgumentException(
          format("The discriminator option is not defined on this entity: %s",
              getReturnedClass()));
    } else {
      return discriminator;
    }
  }

}
