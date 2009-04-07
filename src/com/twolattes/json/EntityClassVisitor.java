package com.twolattes.json;

import static com.twolattes.json.FieldDescriptor.GetSetFieldDescriptor.Type.GETTER;
import static com.twolattes.json.FieldDescriptor.GetSetFieldDescriptor.Type.SETTER;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Pattern;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.EmptyVisitor;

import com.twolattes.json.DescriptorFactory.EntityDescriptorStore;
import com.twolattes.json.FieldDescriptor.GetSetFieldDescriptor;
import com.twolattes.json.types.JsonType;

class EntityClassVisitor extends EmptyVisitor {
  private static final Pattern GETTER_PATTERN = Pattern.compile("^(get|is)[A-Z0-9_].*$");
  private static final Pattern GETTER_SIGNATURE = Pattern.compile("^\\(\\).*$");
  private static final Pattern SETTER_PATTERN = Pattern.compile("^set[A-Z0-9_].*$");
  private static final Pattern SETTER_SIGNATURE = Pattern.compile("^\\([^\\)]+\\)V$");

  private final Map<String, FieldDescriptor> fieldDescriptors;
  private final Class<?> entityClass;
  private final EntityDescriptorStore store;
  private final boolean shouldInline;
  private final Map<String, Method> methods;
  private final Map<Type, Class<?>> types;

  public EntityClassVisitor(Class<?> entityClass, EntityDescriptorStore store,
      boolean shouldInline, Map<Type, Class<?>> types) {
    this.fieldDescriptors = new HashMap<String, FieldDescriptor>();
    this.entityClass = entityClass;
    this.store = store;
    this.shouldInline = shouldInline;
    this.types = types;
    this.methods = new HashMap<String, Method>();

    for (Method m : entityClass.getDeclaredMethods()) {
      Value annotation = m.getAnnotation(Value.class);
      if (annotation != null) {
        String name = m.getName();
        if (isGetterName(name)) {
          if (methods.containsKey(name)) {
            throw new IllegalArgumentException("Non-unique getter name: " + name);
          } else {
            methods.put(name, m);
          }
        } else if (isSetterName(name)) {
          if (methods.containsKey(name)) {
            throw new IllegalArgumentException("Non-unique setter name: " + name);
          } else {
            methods.put(name, m);
          }
        }
      }
    }
  }

  @Override
  public FieldVisitor visitField(int access, String name, String desc,
      String signature, Object value) {
    try {
      if (signature != null) {
        return new EntityFieldVisitor(
            this, entityClass.getDeclaredField(name), signature, store, types);
      } else {
        return new EntityFieldVisitor(
            this, entityClass.getDeclaredField(name), desc, store, types);
      }
    } catch (NoSuchFieldException e) {
      throw new IllegalStateException();
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public MethodVisitor visitMethod(int access, String name, String desc,
      String signature, String[] exceptions) {
    Method method = methods.get(name);
    if (method == null) {
      return null;
    }

    signature = (signature != null) ? signature : desc;
    Value annotation = method.getAnnotation(Value.class);
    if (isGetterName(name) && isGetterSignature(signature)) {
      // creating, checking against discovered descriptors
      GetSetFieldDescriptor descriptor = new GetSetFieldDescriptor(GETTER, method);
      FieldDescriptor potentialDescriptor = fieldDescriptors.get(descriptor.getFieldName());
      if (potentialDescriptor != null) {
        if (potentialDescriptor instanceof GetSetFieldDescriptor) {
          descriptor = (GetSetFieldDescriptor) potentialDescriptor;
          descriptor.setGetter(method);
        } else {
          throw new IllegalArgumentException("Value with name "
              + descriptor.getFieldName() + " is described multiple times.");
        }
      } else {
        fieldDescriptors.put(descriptor.getFieldName(), descriptor);
      }

      // type
      Class<? extends JsonType> type = annotation.type();
      if (!type.equals(com.twolattes.json.types.JsonType.class)) {
        descriptor.setDescriptor(
            new UserTypeDescriptor(Instantiator.newInstance(type)));
      } else if (descriptor.getDescriptor() == null) {
        descriptor.setDescriptor(
            new DescriptorFactory().create(
                signature.substring(2), store, descriptor, types));
      }

      // using annotation to populate the descriptor
      descriptor.setJsonName(annotation.name());
      descriptor.setOptional(annotation.optional());
      descriptor.setShouldInline(annotation.inline());

      for (String view : annotation.views()) {
        descriptor.addView(view);
      }

      methods.remove(name);
    } else if (isSetterName(name) && isSetterSignature(signature)) {
      // creating, checking against discovered descriptors
      GetSetFieldDescriptor descriptor = new GetSetFieldDescriptor(SETTER, method);
      FieldDescriptor potentialDescriptor = fieldDescriptors.get(descriptor.getFieldName());
      if (potentialDescriptor==null) {
        for (String fieldName : fieldDescriptors.keySet()) {
          if (descriptor.getFieldName().startsWith(fieldName)) {
            potentialDescriptor = fieldDescriptors.get(fieldName);
          }
        }
      }
      if (potentialDescriptor != null) {
        if (potentialDescriptor instanceof GetSetFieldDescriptor) {
          descriptor = (GetSetFieldDescriptor) potentialDescriptor;
          descriptor.setSetter(method);
        } else {
          throw new IllegalArgumentException("Value with name "
              + descriptor.getFieldName() + " is described multiple times.");
        }
      } else {
        fieldDescriptors.put(descriptor.getFieldName(), descriptor);
      }

      // type
      if (!annotation.type().equals(com.twolattes.json.types.JsonType.class)) {
        descriptor.setDescriptor(
            new UserTypeDescriptor(Instantiator.newInstance(annotation.type())));
      } else if (descriptor.getDescriptor() == null) {
        descriptor.setDescriptor(
            new DescriptorFactory().create(
                signature.substring(1, signature.length() - 2),
                store, descriptor, types));
      }

      // using annotation to populate the descriptor
      descriptor.setJsonName(annotation.name());
      descriptor.setOptional(annotation.optional());
      descriptor.setShouldInline(annotation.inline());

      for (String view : annotation.views()) {
        descriptor.addView(view);
      }

      methods.remove(name);
    }
    return null;
  }

  void verify() {
    // It would be nicer if this verification could be done in visitEnd().
    // Unfortunately, org.objectweb.asm.ClassReader.accept(ClassVisitor) calls
    // visitEnd() multiple times, at seemingly random times, and more often than
    // it calls visit(...). Its behavior varies widely from its Javadoc.
    if (!methods.isEmpty()) {
      String name = methods.keySet().iterator().next();
      String type = isGetterName(name) ? "getter" : "setter";
      throw new IllegalArgumentException(
        "Method " + name + " has an invalid " + type + " signature");
    }
  }

  protected void add(FieldDescriptor fieldDescriptor) {
    // if the annotated name is different than the actual field name, use the annotated name
    String fieldName = fieldDescriptor.getFieldName();
    String jsonName = fieldDescriptor.getJsonName();
    String name = fieldName.equals(jsonName) ? fieldName : jsonName;
    if (fieldDescriptors.containsKey(name)) {
      throw new IllegalArgumentException(
          "Value with name " + name + " is described multiple times.");
    }
    fieldDescriptors.put(name, fieldDescriptor);
  }

  @SuppressWarnings("unchecked")
  EntityDescriptor<?> getDescriptor(ConcreteEntityDescriptor<?> parent) {
    return new ConcreteEntityDescriptor(entityClass,
        new HashSet<FieldDescriptor>(fieldDescriptors.values()),
        shouldInline, parent);
  }

  boolean isGetterName(String name) {
    return GETTER_PATTERN.matcher(name).matches();
  }

  boolean isGetterSignature(String desc) {
    return GETTER_SIGNATURE.matcher(desc).matches();
  }

  boolean isSetterName(String name) {
    return SETTER_PATTERN.matcher(name).matches();
  }

  boolean isSetterSignature(String desc) {
    return SETTER_SIGNATURE.matcher(desc).matches();
  }
}
