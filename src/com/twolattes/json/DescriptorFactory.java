package com.twolattes.json;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.signature.SignatureReader;

/**
 * An {@link EntityDescriptor} factory.
 */
class DescriptorFactory {

  /**
   * Creates an {@link EntityDescriptor} based on a {@link Class} object.
   */
  @SuppressWarnings("unchecked")
  <T> Pair<? extends EntityDescriptor, Entity> create(
      Class<?> c, EntityDescriptorStore store,
      Map<Type, Class<?>> types) throws IOException {

    // verifying that the class is an entity
    Entity annotation = c.getAnnotation(Entity.class);
    if (annotation == null) {
      throw new IllegalArgumentException(c + " is not an entity. Entities must"
          + " be annotated with @Entity.");
    }

    // may be creating it already
    if (store.contains(c)) {
      return Pair.of(store.get(c), annotation);
    }

    // weak reference
    store.put(c);

    // polymorphic entity
    int subclassesLength = annotation.subclasses().length;
    String discriminatorName = annotation.discriminatorName();
    if (subclassesLength > 0) {
      if (discriminatorName == null || discriminatorName.length() == 0) {
        throw new IllegalArgumentException(
            "The discriminatorName option must be used in conjunction of the " +
            "subclasses option: " + c);
      }

      // getting all the concrete descriptors
      Map<String, EntityDescriptor<?>> subclassesDescriptor =
          new HashMap<String, EntityDescriptor<?>>(subclassesLength);
      for (Class<?> subclass : annotation.subclasses()) {
        if (c.isAssignableFrom(subclass)) {
          ConcreteEntityDescriptor<Object> concreteEntityDescriptor =
              createConcreteEntityDescriptor(subclass, store, types);
          String discriminator = concreteEntityDescriptor.getDiscriminator();
          if (subclassesDescriptor.put(
              discriminator, concreteEntityDescriptor) != null) {
            throw new IllegalArgumentException(
                "The discriminator " + discriminator + " is already used by" +
                " the entity " + concreteEntityDescriptor.getReturnedClass() + ".");
          }
        } else {
          throw new IllegalArgumentException(
              "The class " + subclass + " is not a subclass of the" +
              " polymorphic entity " + c + ".");
        }
      }

      return Pair.of(
          createPolymorphicEntityDescriptor(c, store,
              new HashSet<EntityDescriptor<?>>(subclassesDescriptor.values())),
          annotation);
    } else if (discriminatorName != null && discriminatorName.length() > 0) {
      throw new IllegalArgumentException(
          "The subclasses option must be used in conjunction of the " +
          "discriminatorName option: " + c);
    } else {
      return Pair.of(
          createConcreteEntityDescriptor(c, store, types),
          annotation);
    }
  }

  @SuppressWarnings("unchecked")
  private <T> ConcreteEntityDescriptor<T> createConcreteEntityDescriptor(
      Class<?> c, EntityDescriptorStore store,
      Map<Type, Class<?>> types) throws IOException {
    // parent of the entity
    Class<?> parentClass = c.getSuperclass();
    ConcreteEntityDescriptor<?> parent = null;
    while (parentClass != null) {
      Entity parentAnnotation = parentClass.getAnnotation(Entity.class);
      if (parentAnnotation != null) {
        parent = createConcreteEntityDescriptor(parentClass, store, types);
        break;
      }
      parentClass = parentClass.getSuperclass();
    }

    // @Entity
    Entity annotation = c.getAnnotation(Entity.class);

    InputStream in = null;
    try {
      // bug 1739760
      in = c.getResourceAsStream("/" + c.getName().replace('.', '/') + ".class");
      if (in == null) {
        throw new IllegalArgumentException("cannot find bytecode for " + c);
      }
      ClassReader reader = new ClassReader(in);
      EntityClassVisitor visitor =
        new EntityClassVisitor(c, store, types);
      reader.accept(visitor, true);
      visitor.verify();

      // getting the descriptor
      EntityDescriptor<?> descriptor = visitor.getDescriptor(parent);

      store.put(c, descriptor);

      // is this entity inlineable?
      if (annotation.inline()) {
        if (!descriptor.isInlineable()) {
          throw new IllegalArgumentException(
              "entity  '" + descriptor.getReturnedClass() + "' is not inlineable." +
          " An entity is inlineable only if it has one property.");
        }
      }

      return (ConcreteEntityDescriptor<T>) descriptor;
    } finally {
      if (in != null) {
        in.close();
      }
    }
  }

  private <T> EntityDescriptor<T> createPolymorphicEntityDescriptor(Class<?> c,
      EntityDescriptorStore store, Set<EntityDescriptor<?>> descriptors) {
    Entity annotation = c.getAnnotation(Entity.class);
    PolymorphicEntityDescriptor<T> descriptor = new PolymorphicEntityDescriptor<T>(
        c, annotation.discriminatorName(), descriptors);
    store.put(c, descriptor);
    return descriptor;
  }

  /**
   * Creates a {@link Descriptor} based on a signature (such as
   * {@code Ljava/lang/String;}.
   */
  @SuppressWarnings("unchecked")
  Pair<Descriptor, Entity> create(String signature, EntityDescriptorStore store,
      FieldDescriptor fieldDescriptor, Map<Type, Class<?>> types) {
    SignatureReader r = new SignatureReader(signature);
    EntitySignatureVisitor entitySignatureVisitor =
        new EntitySignatureVisitor(signature, store, fieldDescriptor, types);
    r.accept(entitySignatureVisitor);
    return entitySignatureVisitor.getDescriptor();
  }

  static class EntityDescriptorStore {
    private final Map<Class<?>, EntityDescriptor<?>> descriptors =
        new HashMap<Class<?>, EntityDescriptor<?>>();
    void put(Class<?> c) {
      descriptors.put(c, null);
    }
    void put(Class<?> c, EntityDescriptor<?> descriptor) {
      descriptors.put(c, descriptor);
    }
    boolean contains(Class<?> c) {
      return descriptors.containsKey(c);
    }
    @SuppressWarnings("unchecked")
    EntityDescriptor get(final Class<?> c) {
      EntityDescriptor<?> descriptor = descriptors.get(c);
      if (descriptor == null) {
        throw new IllegalStateException("descriptor of " + c + " is weakly stored");
      }
      return descriptor;
    }
  }
}
