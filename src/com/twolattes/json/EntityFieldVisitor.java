package com.twolattes.json;

import java.lang.reflect.Field;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.commons.EmptyVisitor;

import com.twolattes.json.DescriptorFactory.EntityDescriptorStore;
import com.twolattes.json.FieldDescriptor.DirectAccessFieldDescriptor;
import com.twolattes.json.types.JsonType;

class EntityFieldVisitor extends EmptyVisitor implements FieldVisitor {
  private final static String VALUE_ANNOTATION_DESCRIPTION = "L" + Value.class.getName().replace('.', '/') + ";";
  private final Field field;
  private final String signature;
  private final EntityClassVisitor classVisitor;
  private boolean isJsonValue = false;
  private final EntityDescriptorStore store;
  private final FieldDescriptor fieldDescriptor;

  public EntityFieldVisitor(EntityClassVisitor visitor, Field field, String signature, EntityDescriptorStore store) {
    this.classVisitor = visitor;
    this.field = field;
    this.signature = signature;
    this.store = store;
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
      return new ValueAnnotationVisitor(fieldDescriptor);
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
      if (type == null) {
        entityDescriptor =
            new DescriptorFactory().create(signature, store);
      } else {
        entityDescriptor = new UserTypeDescriptor(type);
      }
      Boolean shouldInline = fieldDescriptor.getShouldInline();
      if (shouldInline != null && shouldInline) {
        if (!entityDescriptor.isInlineable()) {
          throw new IllegalArgumentException(
              "entity of the field  '" + field.getName() + "' is not inlineable");
        }
      }
      fieldDescriptor.setDescriptor(entityDescriptor);
      classVisitor.add(fieldDescriptor);
    }
  }
}
