package com.twolattes.json;

import static com.twolattes.json.AbstractFieldDescriptor.GetSetFieldDescriptor.Type.GETTER;
import static com.twolattes.json.AbstractFieldDescriptor.GetSetFieldDescriptor.Type.SETTER;
import static java.lang.String.format;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.EmptyVisitor;

import com.twolattes.json.AbstractFieldDescriptor.GetSetFieldDescriptor;
import com.twolattes.json.DescriptorFactory.EntityDescriptorStore;
import com.twolattes.json.types.JsonType;

class EntityClassVisitor extends EmptyVisitor {
  private static final Pattern GETTER_PATTERN = Pattern.compile("^(get|is)[A-Z0-9_].*$");
  private static final Pattern GETTER_SIGNATURE = Pattern.compile("^\\(\\).*$");
  private static final Pattern SETTER_PATTERN = Pattern.compile("^set[A-Z0-9_].*$");
  private static final Pattern SETTER_SIGNATURE = Pattern.compile("^\\([^\\)]+\\)V$");

  private final Map<String, FieldDescriptor> fieldDescriptors;
  private final Class<?> entityClass;
  private final EntityDescriptorStore store;
  private final Map<String, Method> methods;
  private final Map<Type, Class<?>> types;

  public EntityClassVisitor(Class<?> entityClass, EntityDescriptorStore store,
      Map<Type, Class<?>> types) {
    this.fieldDescriptors = new HashMap<String, FieldDescriptor>();
    this.entityClass = entityClass;
    this.store = store;
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

  @Override
  public MethodVisitor visitMethod(int access, String name, String desc,
      String signature, String[] exceptions) {
    Method method = methods.get(name);
    if (method == null) {
      return null;
    }

    signature = (signature != null) ? signature : desc;

    if (isGetterName(name) && isGetterSignature(signature)) {
      registerFieldDescriptor(name, method, signature, GETTER);
    } else if (isSetterName(name) && isSetterSignature(signature)) {
      registerFieldDescriptor(name, method, signature, SETTER);
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  private void registerFieldDescriptor(String name, Method method,
      String signature, GetSetFieldDescriptor.Type type) {
    Value annotation = method.getAnnotation(Value.class);

    GetSetFieldDescriptor fieldDescriptor =
        new GetSetFieldDescriptor(type, method);
    FieldDescriptor potentialDescriptor = get(fieldDescriptor, annotation.name());
    if (potentialDescriptor != null) {
      // we've seen the setter already, get the field descriptor
      if (potentialDescriptor instanceof GetSetFieldDescriptor) {
        fieldDescriptor = (GetSetFieldDescriptor) potentialDescriptor;
      } else if ((potentialDescriptor instanceof EmbeddedFieldDescriptor &&
          ((EmbeddedFieldDescriptor) potentialDescriptor).getFieldDescriptor()
          instanceof GetSetFieldDescriptor)) {
        fieldDescriptor = (GetSetFieldDescriptor)
            ((EmbeddedFieldDescriptor) potentialDescriptor).getFieldDescriptor();
      } else {
        throw new IllegalArgumentException(format(
            "Value with name %s is described multiple times.",
            fieldDescriptor.getFieldName()));
      }
    }

    Descriptor descriptor = null;
    Boolean inlineEntity = null;
    Boolean embedEntity = null;
    Class<? extends JsonType> jsonType = annotation.type();
    if (!jsonType.equals(com.twolattes.json.types.JsonType.class)) {
      descriptor = new UserTypeDescriptor(Instantiator.newInstance(jsonType));
      inlineEntity = false;
    } else {
      Pair<Descriptor, Entity> pair = new DescriptorFactory().create(
          extractSignature(type, signature), store, fieldDescriptor, types);
      descriptor = pair.left;
      inlineEntity = (pair.right != null) ? pair.right.inline() : null;
      embedEntity = (pair.right != null) ? pair.right.embed() : null;
    }

    if (fieldDescriptor.getDescriptor() == null) {
      fieldDescriptor.setDescriptor(descriptor);
    }

    boolean inline = false;
    boolean embed = false;

    inline = annotation.inline()
        || (inlineEntity == null ? false : inlineEntity);
    embed = annotation.embed()
        || (embedEntity == null ? false : embedEntity);

    fieldDescriptor.setJsonName(annotation.name());
    fieldDescriptor.setOptional(annotation.optional());
    for (String view : annotation.views()) {
      fieldDescriptor.addView(view);
    }

    if (potentialDescriptor != null) {
      // we've seen the setter already
      if (potentialDescriptor instanceof GetSetFieldDescriptor) {
        if (inline &&
            !(((GetSetFieldDescriptor) potentialDescriptor)
                .getDescriptor() instanceof InlinedEntityDescriptor)) {
          throw new IllegalArgumentException(format(
              "The %s for %s is inlined but the %s is not.",
              type,
              fieldDescriptor.getFieldName(),
              type == GETTER ? SETTER : GETTER));
        } else if (!inline &&
            (((GetSetFieldDescriptor) potentialDescriptor)
                .getDescriptor() instanceof InlinedEntityDescriptor)) {
          throw new IllegalArgumentException(format(
              "The %s for %s is inlined but the %s is not.",
              type == GETTER ? SETTER : GETTER,
              fieldDescriptor.getFieldName(),
              type));
        }
        setGetterOrSetter(fieldDescriptor, method, type);
      } else if ((potentialDescriptor instanceof EmbeddedFieldDescriptor &&
          ((EmbeddedFieldDescriptor) potentialDescriptor).getFieldDescriptor()
          instanceof GetSetFieldDescriptor)) {
        fieldDescriptor = (GetSetFieldDescriptor)
            ((EmbeddedFieldDescriptor) potentialDescriptor).getFieldDescriptor();
        // TODO handle inconsistency between getter and setter

        setGetterOrSetter(fieldDescriptor, method, type);
      } else {
        throw new IllegalStateException();
      }
    } else {
      // register the field descriptor
      if (embed && descriptor instanceof EntityDescriptor) {
        fieldDescriptor.setDescriptor(descriptor);
        add(new EmbeddedFieldDescriptor(fieldDescriptor));
      } else {
        if (inline && descriptor instanceof EntityDescriptor) {
          fieldDescriptor.setDescriptor(
              new InlinedEntityDescriptor((EntityDescriptor) descriptor));
        }
        add(fieldDescriptor);
      }
    }

    methods.remove(name);
  }

  private String extractSignature(
      GetSetFieldDescriptor.Type type, String signature) {
    if (type.equals(GETTER)) {
      return signature.substring(2);
    } else {
      return signature.substring(1, signature.length() - 2);
    }
  }

  void setGetterOrSetter(GetSetFieldDescriptor fieldDescriptor, Method method,
      GetSetFieldDescriptor.Type type) {
    if (type.equals(GETTER)) {
      fieldDescriptor.setGetter(method);
    } else {
      fieldDescriptor.setSetter(method);
    }
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


  protected FieldDescriptor get(String name) {
    Map<String, FieldDescriptor> map = new HashMap<String, FieldDescriptor>();
    for (Map.Entry<String, FieldDescriptor> entry : fieldDescriptors.entrySet()) {
      if (entry.getValue() instanceof EmbeddedFieldDescriptor) {
        Set<FieldDescriptor> set =
            ((EntityDescriptor) entry.getValue().getDescriptor()).getAllFieldDescriptors();
        for (FieldDescriptor current : set) {
          map.put(current.getJsonName().equals("") ?
              current.getFieldName() : current.getJsonName(), current);
        }
      }
      map.put(entry.getKey(), entry.getValue());
    }

    return map.get(name);
  }

  protected FieldDescriptor get(FieldDescriptor fieldDescriptor, String jsonName) {
    if (jsonName.equals("")) {
      return get(fieldDescriptor.getFieldName());
    } else {
      return get(jsonName);
    }
  }

  protected void add(FieldDescriptor fieldDescriptor) {
    String name;
    if (fieldDescriptor.getJsonName().equals("")) {
      name = fieldDescriptor.getFieldName();
    } else {
      name = fieldDescriptor.getJsonName();
    }

    if (fieldDescriptors.containsKey(name)) {
      throw new IllegalArgumentException(
          "Value with name " + name + " is described multiple times.");
    }
    fieldDescriptors.put(name, fieldDescriptor);
  }

  @SuppressWarnings("unchecked")
  EntityDescriptor<?> getDescriptor(ConcreteEntityDescriptor<?> parent) {
    return new ConcreteEntityDescriptor(entityClass,
        new HashSet<FieldDescriptor>(fieldDescriptors.values()), parent);
  }

  Collection<FieldDescriptor> getFieldDescriptors() {
    return fieldDescriptors.values();
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

  public Class<?> getEntityClass() {
    return entityClass;
  }
}
