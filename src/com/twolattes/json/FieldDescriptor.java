package com.twolattes.json;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.twolattes.json.types.JsonType;


/**
 * An entity's field descriptor.
 * @author pascal
 * @version $Id$
 */
abstract class FieldDescriptor {
  private final String fieldName;

  // fields with a default value
  private Set<String> views = null;
  private Boolean shouldInline = null;
  private boolean optional = false;
  private String jsonName = null;

  // fields that MUST be defined before the FieldDescriptor can (un)marshall
  private Descriptor<?, ?> descriptor;
  private JsonType<?, ?> type;

  FieldDescriptor(String fieldName) {
    this.fieldName = fieldName;
  }

  /**
   * Get the entitiy's field name.
   * @return the entitiy's field name
   */
  final String getFieldName() {
    return fieldName;
  }

  /**
   * Get the described field's value.
   * @param instance instance of an object on which the field is
   * @return the field's value
   */
  abstract Object getFieldValue(Object instance);

  /**
   * Set the described field's value.
   * @param instance instance of an object on which the field is
   * @param value the value to set
   */
  abstract void setFieldValue(Object instance, Object value);

  /**
   * Get the name of the field represented in JSON.
   */
  final String getJsonName() {
    return (jsonName == null) ? fieldName : jsonName;
  }

  /**
   * Returns whether this field should be inlined.
   * @return {@code true} if this field should be inline,
   *     {@code false} if this field should not be inline,
   *     {@code null} if the {@link Value#inline()} was not set on this field
   */
  final Boolean getShouldInline() {
    return shouldInline;
  }

  /** Whether this field should this field be inlined.
   */
  final boolean isOptional() {
    return optional;
  }

  /** The type of the field.
   */
  final JsonType<?, ?> getType() {
    return type;
  }

  /**
   * Get the field's descriptor.
   * @return the field's descriptor
   */
  @SuppressWarnings("unchecked")
  final Descriptor getDescriptor() {
    return descriptor;
  }

  /**
   * Tests whether the field is in a specific view.
   * @param view the view
   * @return <tt>true</tt> if the field is in the view
   */
  final boolean isInView(String view) {
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

  void setType(JsonType<?, ?> type) {
    this.type = type;
  }

  void setDescriptor(Descriptor<?, ?> descriptor) {
    this.descriptor = descriptor;
  }

  @Override
  public final String toString() {
    return getFieldName() + ", " +
    getJsonName() + ": " +
    getDescriptor().toString();
  }

  /**
   * A field descriptor using direct field access via a {@link Field} instance.
   */
  static class DirectAccessFieldDescriptor extends FieldDescriptor {
    private final Field field;

    DirectAccessFieldDescriptor(Field field) {
      super(field.getName());
      this.field = field;
    }

    @Override
    Object getFieldValue(Object instance) {
      try {
        return field.get(instance);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException("cannot access " + instance.getClass() + " field");
      }
    }

    @Override
    void setFieldValue(Object instance, Object value) {
      try {
        field.set(instance, value);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException("cannot access " + instance.getClass() + " field");
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
  static class GetSetFieldDescriptor extends FieldDescriptor {
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

    @Override
    Object getFieldValue(Object instance) {
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
    @Override
    void setFieldValue(Object instance, Object value) {
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
