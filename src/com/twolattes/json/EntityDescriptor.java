package com.twolattes.json;

import java.util.Set;

/**
 * Entity descriptor describing entities which are instances of {@code T}.
 *
 * @author pascallouis
 */
interface EntityDescriptor<T> extends Descriptor<T, Json.Value> {
  /**
   * Gets the set of field descriptors describing fields of this entity.
   */
  Set<FieldDescriptor> getFieldDescriptors();

  /**
   * Gets this entitie's discriminator. This is an optional operation which
   * should be defined for entities mentioned in a
   * {@link Entity#subclasses()} list.
   */
  String getDiscriminator();
}
