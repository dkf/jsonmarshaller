package com.twolattes.json;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.commons.EmptyVisitor;

import com.twolattes.json.AbstractFieldDescriptor.DirectAccessFieldDescriptor;
import com.twolattes.json.DescriptorFactory.EntityDescriptorStore;
import com.twolattes.json.types.JsonType;

class EntityFieldVisitor extends EmptyVisitor implements FieldVisitor {
  private final static String VALUE_ANNOTATION_DESCRIPTION = "L" + Value.class.getName().replace('.', '/') + ";";
  private final Field field;
  private final String signature;
  private final EntityClassVisitor classVisitor;
  private boolean isJsonValue = false;
  private final EntityDescriptorStore store;
  final AbstractFieldDescriptor fieldDescriptor;
  private final Map<Type, Class<?>> types;
  Boolean shouldInline;
  Boolean shouldEmbed;

  public EntityFieldVisitor(EntityClassVisitor visitor, Field field,
      String signature, EntityDescriptorStore store,
      Map<Type, Class<?>> types) {
    this.classVisitor = visitor;
    this.field = field;
    this.signature = signature;
    this.store = store;
    this.types = types;
    this.fieldDescriptor = new DirectAccessFieldDescriptor(field);

    // accessibility of the field
    if (!field.isAccessible()) {
      field.setAccessible(true);
    }
  }

  @Override
  public AnnotationVisitor visitAnnotation(String description, boolean visible) {
    if (VALUE_ANNOTATION_DESCRIPTION.equals(description)) {
      isJsonValue = true;
      return new ValueAnnotationVisitor(this);
    } else {
      return new EmptyVisitor();
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void visitEnd() {
    if (isJsonValue) {
      Descriptor entityDescriptor;
      JsonType<?, ?> type = fieldDescriptor.getType();
      if (type == null && types.containsKey(field.getType())) {
        entityDescriptor = new UserTypeDescriptor(
            (JsonType) Instantiator.newInstance(types.get(field.getType())));
      } else if (type == null) {
          Pair<Descriptor, Entity> pair = new DescriptorFactory().create(
              signature, store, fieldDescriptor, types);
          entityDescriptor = pair.left;
          if (shouldInline == null && pair.right != null) {
            shouldInline = pair.right.inline();
          }
          if (shouldEmbed == null && pair.right != null) {
            shouldEmbed = pair.right.embed();
          }
      } else {
        entityDescriptor = new UserTypeDescriptor(type);
      }
      if (shouldInline != null && shouldInline) {
        if (!entityDescriptor.isInlineable()) {
          throw new IllegalArgumentException(
              "entity of the field  '" + field.getName() + "' is not inlineable");
        }
      }
      if (shouldEmbed != null && shouldEmbed) {
        if (entityDescriptor instanceof EntityDescriptor) {
          Set<FieldDescriptor> fieldDescriptors =
              new HashSet<FieldDescriptor>(((EntityDescriptor) entityDescriptor).getFieldDescriptors());

          fieldDescriptors.addAll(
              ((EntityDescriptor) entityDescriptor).getAllFieldDescriptors());

          if (entityDescriptor instanceof PolymorphicEntityDescriptor) {
            String discriminatorName = ((PolymorphicEntityDescriptor) entityDescriptor).getDiscriminatorName();
            if (classVisitor.get(discriminatorName) != null) {
              throw new IllegalArgumentException(
                  "entity of the field  '" + field.getName() + "' is not embeddable "
                  + "because of a conflict with the discriminator name '" + discriminatorName + "'");
            }
          }

          for (FieldDescriptor fieldDescriptor : fieldDescriptors) {
            if (classVisitor.get(fieldDescriptor, fieldDescriptor.getJsonName()) != null) {
              throw new IllegalArgumentException(
                  "entity of the field  '" + field.getName() + "' is not embeddable "
                  + "because of a conflict on field '" + fieldDescriptor.getJsonName() + "'");
            }
          }
        } else {
          if (!entityDescriptor.isEmbeddable()) {
            throw new IllegalArgumentException("entity of the field  '"
                + field.getName() + "' is not embeddable");
          }
        }
        // TODO throw exception if
        // - already inlined (add test)
      }

      if (shouldInline != null && shouldInline
          && entityDescriptor instanceof EntityDescriptor) {
        fieldDescriptor.setDescriptor(
            new InlinedEntityDescriptor((EntityDescriptor) entityDescriptor));
      } else if (shouldEmbed != null && shouldEmbed
            && entityDescriptor instanceof EntityDescriptor) {
        fieldDescriptor.setDescriptor(entityDescriptor);
        classVisitor.add(new EmbeddedFieldDescriptor(fieldDescriptor));
        Set<FieldDescriptor> fieldDescriptors =
            ((EntityDescriptor) entityDescriptor).getFieldDescriptors();
        for (FieldDescriptor fieldDescriptor : fieldDescriptors) {
          if (fieldDescriptor instanceof AbstractFieldDescriptor) {
            ((AbstractFieldDescriptor) fieldDescriptor).setOptional(true);
          }
        }
        return;
      } else {
        fieldDescriptor.setDescriptor(entityDescriptor);
      }

      classVisitor.add(fieldDescriptor);
    }
  }
}
