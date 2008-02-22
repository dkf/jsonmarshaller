package com.twolattes.json;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

class PolymorphicEntityDescriptor<T> implements EntityDescriptor<T> {
  /**
   * Subclasses' descriptors used if the described entity is a polymorphic
   * entity. The keys are discriminators values.
   */
  private final Map<String, EntityDescriptor<?>> subDescriptorsByDisciminator;
  
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
    this.subDescriptorsByDisciminator = new HashMap<String, EntityDescriptor<?>>();
    for (EntityDescriptor<?> descriptor : descriptors) {
      subDescriptorsByClass.put(descriptor.getReturnedClass(), descriptor);
      subDescriptorsByDisciminator.put(
          descriptor.getDiscriminator(), descriptor);
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

  public void init(boolean cyclic) {
    for (EntityDescriptor<?> descriptor : subDescriptorsByClass.values()) {
      descriptor.init(cyclic);
    }
  }

  public Class<?> getReturnedClass() {
    return returnedClass;
  }

  public JSONObject marshall(Object entity, boolean cyclic) {
    return marshall(entity, cyclic, null);
  }

  public JSONObject marshall(Object entity, boolean cyclic, String view) {
    // null
    if (entity == null) {
      return JSONObject.NULL;
    }
    
    // null safe
    EntityDescriptor<?> descriptor =
        subDescriptorsByClass.get(entity.getClass());
    if (descriptor == null) {
      throw new IllegalArgumentException(
          "Unmarshalled entity of class " + entity.getClass() + "is not " +
          "a valid subclass entity of " + returnedClass);
    }
    JSONObject jsonObject = descriptor.marshall(entity, cyclic, view);
    try {
      jsonObject.put(discriminatorName, descriptor.getDiscriminator());
    } catch (JSONException e) {
      throw new IllegalStateException();
    }
    return jsonObject;
  }

  public Object marshallInline(Object entity, boolean cyclic) {
    throw new UnsupportedOperationException();
  }

  public T unmarshall(Object marshalled, boolean cyclic) {
    return unmarshall(marshalled, cyclic, null);
  }

  @SuppressWarnings("unchecked")
  public T unmarshall(Object object, boolean cyclic, String view) {
    // null
    if (JSONObject.NULL.equals(object)) {
      return null;
    }
    
    // null safe
    JSONObject jsonObject = (JSONObject) object;
    String discriminator;
    try {
      discriminator = jsonObject.getString(discriminatorName);
    } catch (JSONException e) {
      throw new IllegalArgumentException(
          "Unmarhsalling polymorphic entity which does not contain the " +
          "discriminator: " + discriminatorName);
    }
    
    // getting the concrete descriptor
    EntityDescriptor<?> descriptor =
        subDescriptorsByDisciminator.get(discriminator);
    return (T) descriptor.unmarshall(object, cyclic, view);
  }

  public T unmarshallInline(Object entity, boolean cyclic) {
    throw new UnsupportedOperationException();
  }
}
