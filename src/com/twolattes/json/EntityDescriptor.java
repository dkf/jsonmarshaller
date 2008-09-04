package com.twolattes.json;

import java.util.Set;

import org.json.JSONObject;

/**
 * Entity descriptor describing entities which are instances of {@code T}.
 *
 * @author pascallouis
 */
interface EntityDescriptor<T> extends Descriptor<T, Object> {
  /**
   * Gets the set of field descriptors describing fields of this entity.
   */
  Set<FieldDescriptor> getFieldDescriptors();

  /**
   * Marshals an entity using a particular view.
   * See {@link #marshall(Object, boolean)}.
   */
  JSONObject marshall(Object entity, String view);

  /**
   * Unmarshals an entity using a particular a view.
   */
  T unmarshall(Object object, String view);

  /**
   * Gets this entitie's discriminator. This is an optional operation which
   * should be defined for entities mentioned in a
   * {@link Entity#subclasses()} list.
   */
  String getDiscriminator();
}
