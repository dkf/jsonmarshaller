package com.twolattes.json;

import static com.twolattes.json.FieldDescriptor.GetSetFieldDescriptor.Type.GETTER;
import static com.twolattes.json.FieldDescriptor.GetSetFieldDescriptor.Type.SETTER;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.EmptyVisitor;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.twolattes.json.DescriptorFactory.EntityDescriptorStore;
import com.twolattes.json.FieldDescriptor.GetSetFieldDescriptor;

class EntityClassVisitor extends EmptyVisitor {
  private static final Pattern GETTER_PATTERN = Pattern.compile("^(get|is)[A-Z0-9_].*$");
  private static final Pattern GETTER_SIGNATURE = Pattern.compile("^\\(\\).*$");
  private static final Pattern SETTER_PATTERN = Pattern.compile("^set[A-Z0-9_].*$");
  private static final Pattern SETTER_SIGNATURE = Pattern.compile("^\\([^\\)]+\\)V$");

  private Map<String, FieldDescriptor> fieldDescriptors = Maps.newHashMap();
  private final Class<?> entityClass;
  private final EntityDescriptorStore store;
  private final boolean shouldInline;
  private final Map<String, Set<Method>> methods = Maps.newHashMap();

  public EntityClassVisitor(Class<?> entityClass, EntityDescriptorStore store, boolean shouldInline) {
    this.entityClass = entityClass;
    this.store = store;
    this.shouldInline = shouldInline;
    methods2map(entityClass.getDeclaredMethods());
  }

  private void methods2map(Method[] declaredMethods) {
    for (Method m : declaredMethods) {
      String name = m.getName();
      Set<Method> set = methods.get(name);
      if (set == null) {
        set = Sets.newHashSet();
        methods.put(name, set);
      }
      set.add(m);
    }
  }

  @Override
  public FieldVisitor visitField(int access, String name, String desc,
      String signature, Object value) {
    try {
      if (signature != null) {
        return new EntityFieldVisitor(this, entityClass.getDeclaredField(name), signature, store);
      } else {
        return new EntityFieldVisitor(this, entityClass.getDeclaredField(name), desc, store);
      }
    } catch (NoSuchFieldException e) {
      throw new IllegalStateException();
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public MethodVisitor visitMethod(int access, String name, String desc,
      String signature, String[] exceptions) {
    signature = (signature != null) ? signature : desc;
    if (isGetterName(name) &&
        isGetterSignature(signature) &&
        methods.get(name).size() == 1) {
      Method getter = methods.get(name).iterator().next();
      Value annotation = getter.getAnnotation(Value.class);
      if (annotation != null) {
        // creating, checking against discovered descriptors
        GetSetFieldDescriptor descriptor = new GetSetFieldDescriptor(GETTER, getter);
        FieldDescriptor potentialDescriptor = fieldDescriptors.get(descriptor.getFieldName());
        if (potentialDescriptor != null) {
          if (potentialDescriptor instanceof GetSetFieldDescriptor) {
            descriptor = (GetSetFieldDescriptor) potentialDescriptor;
            descriptor.setGetter(getter);
          } else {
            throw new IllegalArgumentException("Value with name "
                + descriptor.getFieldName() + " is described multiple times.");
          }
        } else {
          fieldDescriptors.put(descriptor.getFieldName(), descriptor);
        }

        // type
        if (!annotation.type().equals(com.twolattes.json.types.JsonType.class)) {
          try {
            descriptor.setDescriptor(
                new UserTypeDescriptor(annotation.type().newInstance()));
    		  } catch (InstantiationException e) {
    		    throw new IllegalStateException("could not instanciate " + annotation.type());
    		  } catch (IllegalAccessException e) {
    			throw new IllegalStateException("could not access " + annotation.type());
    		  }
        } else if (descriptor.getDescriptor() == null) {
          descriptor.setDescriptor(
              new DescriptorFactory().create(signature.substring(2), store));
        }

        // using annotation to populate the descriptor
        descriptor.setJsonName(annotation.name());
        descriptor.setOptional(annotation.optional());
        descriptor.setShouldInline(annotation.inline());

        for (String view : annotation.views()) {
          descriptor.addView(view);
        }
      }
    } else if (isSetterName(name) &&
        isSetterSignature(signature) &&
        methods.get(name).size() == 1) {
      Method setter = methods.get(name).iterator().next();
      Value annotation = setter.getAnnotation(Value.class);
      if (annotation != null) {
        // creating, checking against discovered descriptors
        GetSetFieldDescriptor descriptor = new GetSetFieldDescriptor(SETTER, setter);
        FieldDescriptor potentialDescriptor = fieldDescriptors.get(descriptor.getFieldName());
        if (potentialDescriptor != null) {
          if (potentialDescriptor instanceof GetSetFieldDescriptor) {
            descriptor = (GetSetFieldDescriptor) potentialDescriptor;
            descriptor.setSetter(setter);
          } else {
            throw new IllegalArgumentException("Value with name "
                + descriptor.getFieldName() + " is described multiple times.");
          }
        } else {
          fieldDescriptors.put(descriptor.getFieldName(), descriptor);
        }

        // type
        if (!annotation.type().equals(com.twolattes.json.types.JsonType.class)) {
          try {
            descriptor.setDescriptor(
                new UserTypeDescriptor(annotation.type().newInstance()));
    		  } catch (InstantiationException e) {
    		    throw new IllegalStateException("could not instanciate " + annotation.type());
    		  } catch (IllegalAccessException e) {
    			throw new IllegalStateException("could not access " + annotation.type());
    		  }
        } else if (descriptor.getDescriptor() == null) {
          descriptor.setDescriptor(
              new DescriptorFactory().create(signature.substring(1, signature.length() - 2), store));
        }

        // using annotation to populate the descriptor
        descriptor.setJsonName(annotation.name());
        descriptor.setOptional(annotation.optional());
        descriptor.setShouldInline(annotation.inline());

        for (String view : annotation.views()) {
          descriptor.addView(view);
        }
      }
    }
    return null;
  }

  protected void add(FieldDescriptor fieldDescriptor) {
    fieldDescriptors.put(fieldDescriptor.getFieldName(), fieldDescriptor);
  }

  @SuppressWarnings("unchecked")
  EntityDescriptor<?> getDescriptor(ConcreteEntityDescriptor<?> parent) {
    return new ConcreteEntityDescriptor(entityClass,
        Sets.newHashSet(fieldDescriptors.values()), shouldInline, parent);
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
