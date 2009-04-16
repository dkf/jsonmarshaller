package com.twolattes.json;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.twolattes.json.types.JsonType;

/**
 * An entity's field descriptor.
 *
 */
abstract class AbstractFieldDescriptor implements FieldDescriptor {

  private final String fieldName;

  // fields with a default value
  private Set<String> views = null;

  private Boolean shouldInline = null;

  private boolean optional = false;

  private String jsonName = null;

  private boolean ordinal = false;

  // fields that MUST be defined before the FieldDescriptor can (un)marshall
  private Descriptor<?, ?> descriptor;

  private JsonType<?, ?> type;

  AbstractFieldDescriptor(String fieldName) {
    this.fieldName = fieldName;
  }

  public final String getFieldName() {
    return fieldName;
  }

  public final String getJsonName() {
    return (jsonName == null) ? fieldName : jsonName;
  }

  public final Boolean getShouldInline() {
    return shouldInline;
  }

  public final boolean isOptional() {
    return optional;
  }

  public final boolean useOrdinal() {
    return ordinal;
  }

  public final JsonType<?, ?> getType() {
    return type;
  }

  public final Descriptor<?, ?> getDescriptor() {
    return descriptor;
  }

  public final boolean isInView(String view) {
    if (views == null) {
      return true;
    }
    return views.contains(view);
  }

  void addView(String view) {
    if (views == null) {
      views = new HashSet<String>();
    }
    views.add(view);
  }

  void setJsonName(String jsonName) {
    if (jsonName != null && jsonName.length() > 0) {
      this.jsonName = jsonName;
    } else {
      this.jsonName = null;
    }
  }

  void setShouldInline(Boolean shouldInline) {
    this.shouldInline = shouldInline;
  }

  void setOptional(boolean optional) {
    this.optional = optional;
  }

  void setOrdinal(boolean ordinal) {
    this.ordinal = ordinal;
  }

  void setType(JsonType<?, ?> type) {
    this.type = type;
  }

  void setDescriptor(Descriptor<?, ?> descriptor) {
    this.descriptor = descriptor;
  }

  @Override
  public final String toString() {
    return getFieldName() + ", " + getJsonName() + ": "
        + getDescriptor().toString();
  }

  /**
   * A field descriptor using direct field access via a {@link Field} instance.
   */
  static class DirectAccessFieldDescriptor extends AbstractFieldDescriptor {
    private final Field field;

    DirectAccessFieldDescriptor(Field field) {
      super(field.getName());
      this.field = field;
    }

    public Object getFieldValue(Object instance) {
      try {
        return field.get(instance);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException("cannot access " + instance.getClass()
            + " field");
      }
    }

    public void setFieldValue(Object instance, Object value) {
      try {
        field.set(instance, value);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException("cannot access " + instance.getClass()
            + " field");
      }
    }

    @Override
    public int hashCode() {
      return field.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if (obj != null && obj.getClass().equals(this.getClass())) {
        DirectAccessFieldDescriptor that = (DirectAccessFieldDescriptor) obj;
        return this.field.equals(that.field);
      } else {
        return false;
      }
    }
  }

  /**
   * A field descriptor accessing the value via a getter and setter.
   */
  static class GetSetFieldDescriptor extends AbstractFieldDescriptor {
    enum Type {
      GETTER {
        @Override
        String name(Method m) {
          if (m.getName().charAt(0) == 'i') {
            StringBuilder builder = new StringBuilder();
            builder.append(Character.toLowerCase(m.getName().charAt(2)));
            builder.append(m.getName().substring(3));
            return builder.toString();
          } else {
            return SETTER.name(m);
          }
        }

        @Override
        void store(GetSetFieldDescriptor a, Method m) {
          m.setAccessible(true);
          a.getter = m;
        }
      },

      SETTER {
        @Override
        String name(Method m) {
          StringBuilder builder = new StringBuilder();
          builder.append(Character.toLowerCase(m.getName().charAt(3)));
          builder.append(m.getName().substring(4));
          return builder.toString();
        }

        @Override
        void store(GetSetFieldDescriptor a, Method m) {
          m.setAccessible(true);
          a.setter = m;
        }
      };

      abstract String name(Method m);

      abstract void store(GetSetFieldDescriptor a, Method m);
    }

    private Method getter;

    private Method setter;

    GetSetFieldDescriptor(Type t, Method m) {
      super(t.name(m));
      t.store(this, m);
    }

    public Object getFieldValue(Object instance) {
      try {
        return getter.invoke(instance);
      } catch (IllegalArgumentException e) {
        throw new IllegalStateException(e);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(e);
      } catch (InvocationTargetException e) {
        throw new IllegalStateException(e);
      }
    }

    public void setFieldValue(Object instance, Object value) {
      try {
        setter.invoke(instance, value);
      } catch (IllegalArgumentException e) {
        throw new IllegalStateException(e);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(e);
      } catch (InvocationTargetException e) {
        throw new IllegalStateException(e);
      }
    }

    void setGetter(Method getter) {
      Type.GETTER.store(this, getter);
    }

    void setSetter(Method setter) {
      Type.SETTER.store(this, setter);
    }
  }

}
