package com.twolattes.json;

import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.commons.EmptyVisitor;

import com.twolattes.json.types.JsonType;

class ValueAnnotationVisitor extends EmptyVisitor implements AnnotationVisitor {

  // TODO: reflect on the Value annotation to find those values... instead of copying them!
  private static final String VALUE_ANNOTATION_NAME = "name";
  private static final String VALUE_ANNOTATION_INLINE = "inline";
  private static final String VALUE_ANNOTATION_EMBED = "embed";
  private static final String VALUE_ANNOTATION_OPTIONAL = "optional";
  private static final String VALUE_ANNOTATION_TYPE = "type";
  private static final String VALUE_ANNOTATION_VIEWS = "views";
  private static final String VALUE_ANNOTATION_ORDINAL = "ordinal";
  private static final String VALUE_ANNOTATION_NAME_DEFAULT = "";
  private boolean visitingViews = false;
  private Set<String> views = null;
  private final EntityFieldVisitor entityFieldVisitor;

  ValueAnnotationVisitor(EntityFieldVisitor entityFieldVisitor) {
    this.entityFieldVisitor = entityFieldVisitor;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void visit(String name, Object value) {
    if (visitingViews) {
      views.add((String) value);
    }
    if (VALUE_ANNOTATION_NAME.equals(name) && !VALUE_ANNOTATION_NAME_DEFAULT.equals(value)) {
      entityFieldVisitor.fieldDescriptor.setJsonName((String) value);
    } else if (VALUE_ANNOTATION_INLINE.equals(name)) {
      entityFieldVisitor.shouldInline = (Boolean) value;
    } else if (VALUE_ANNOTATION_EMBED.equals(name)) {
      entityFieldVisitor.shouldEmbed = (Boolean) value;
    } else if (VALUE_ANNOTATION_OPTIONAL.equals(name)) {
      entityFieldVisitor.isOptional = (Boolean) value;
    } else if (VALUE_ANNOTATION_ORDINAL.equals(name)) {
      entityFieldVisitor.fieldDescriptor.setOrdinal((Boolean) value);
    } else if (VALUE_ANNOTATION_TYPE.equals(name)) {
      String className = ((org.objectweb.asm.Type) value).getClassName();
      try {
        entityFieldVisitor.fieldDescriptor.setType(
            (JsonType) Instantiator.newInstance(Class.forName(className)));
      } catch (ClassNotFoundException e) {
        throw new IllegalStateException("type not found " + className);
      }
    }
  }

  @Override
  public AnnotationVisitor visitArray(String name) {
    if (VALUE_ANNOTATION_VIEWS.equals(name)) {
      this.visitingViews = true;
      this.views = new HashSet<String>();
      return this;
    } else {
      return super.visitArray(name);
    }
  }

  @Override
  public void visitEnd() {
    if (visitingViews) {
      for (String view : views) {
        entityFieldVisitor.fieldDescriptor.addView(view);
      }
      visitingViews = false;
    }
  }

}
