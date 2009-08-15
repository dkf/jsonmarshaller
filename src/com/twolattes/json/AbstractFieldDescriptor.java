package com.twolattes.json;

import static com.twolattes.json.Json.string;
import static java.lang.String.format;

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
abstract class AbstractFieldDescriptor extends DefaultBoxingFieldDescriptor {

  private final Json.String fieldName;

  // fields with a default value
  private Set<String> views = null;

  private Json.String jsonName = null;

  private boolean ordinal = false;

  // fields that MUST be defined before the FieldDescriptor can (un)marshall
  private Descriptor<?, ?> descriptor;

  private JsonType<?, ?> type;

  AbstractFieldDescriptor(String fieldName) {
    this.fieldName = string(fieldName);
  }

  @SuppressWarnings("unchecked")
  public void marshall(Object entity, String view, Json.Object jsonObject) {
    if (isInView(view)) {
      jsonObject.put(
          getJsonName(), getDescriptor().marshall(this, entity, view));
    }
  }

  @SuppressWarnings("unchecked")
  public void unmarshall(Object entity, String view, Json.Object jsonObject) {
    Json.String name = getJsonName();
    if (jsonObject.containsKey(name)) {
      if (isInView(view)) {
        getDescriptor().unmarshall(entity, this, jsonObject.get(name), view);
      }
    } else {
      if (isInView(view)) {
        if (view == null) {
          throw new IllegalStateException(
              format("The field %s whose JSON name is %s has no value. " +
                  "If this field is optional, use the @Value(optional = true) " +
                  "annotations.", getFieldName(), name));
        } else {
          throw new IllegalStateException(
              format("The field %s (in the view %s) whose JSON name is %s has " +
              		"no value. If this field is optional, use the " +
              		"@Value(optional = true) annotations.", getFieldName(), view, name));
        }
      }
    }
  }

  public final Json.String getFieldName() {
    return fieldName;
  }

  public final Json.String getJsonName() {
    return jsonName == null ? fieldName : jsonName;
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
      this.jsonName = string(jsonName);
    } else {
      this.jsonName = null;
    }
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
  public String toString() {
    return toString(0);
  }

  public String toString(int pad) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < pad; i++) {
      builder.append(" ");
    }
    builder.append(
        format("%s as \"%s\": ", getFieldName(), getJsonName()));
    builder.append(getDescriptor().toString(pad));

    return builder.toString();
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
        throw new IllegalStateException(
            format("cannot access %s field", instance.getClass()));
      }
    }

    @Override
    public byte getFieldValueByte(Object instance) {
      try {
        return field.getByte(instance);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(
            format("cannot access %s field", instance.getClass()));
      }
    }

    @Override
    public char getFieldValueChar(Object instance) {
      try {
        return field.getChar(instance);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(
            format("cannot access %s field", instance.getClass()));
      }
    }

    @Override
    public boolean getFieldValueBoolean(Object instance) {
      try {
        return field.getBoolean(instance);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(
            format("cannot access %s field", instance.getClass()));
      }
    }

    @Override
    public short getFieldValueShort(Object instance) {
      try {
        return field.getShort(instance);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(
            format("cannot access %s field", instance.getClass()));
      }
    }

    @Override
    public int getFieldValueInt(Object instance) {
      try {
        return field.getInt(instance);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(
            format("cannot access %s field", instance.getClass()));
      }
    }

    @Override
    public long getFieldValueLong(Object instance) {
      try {
        return field.getLong(instance);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(
            format("cannot access %s field", instance.getClass()));
      }
    }

    @Override
    public float getFieldValueFloat(Object instance) {
      try {
        return field.getFloat(instance);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(
            format("cannot access %s field", instance.getClass()));
      }
    }

    @Override
    public double getFieldValueDouble(Object instance) {
      try {
        return field.getDouble(instance);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(
            format("cannot access %s field", instance.getClass()));
      }
    }

    public void setFieldValue(Object instance, Object value) {
      try {
        field.set(instance, value);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(
            format("cannot access %s field", instance.getClass()));
      }
    }

    @Override
    public void setFieldValueByte(Object instance, byte value) {
      try {
        field.setByte(instance, value);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(
            format("cannot access %s field", instance.getClass()));
      }
    }

    @Override
    public void setFieldValueChar(Object instance, char value) {
      try {
        field.setChar(instance, value);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(
            format("cannot access %s field", instance.getClass()));
      }
    }

    @Override
    public void setFieldValueBoolean(Object instance, boolean value) {
      try {
        field.setBoolean(instance, value);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(
            format("cannot access %s field", instance.getClass()));
      }
    }

    @Override
    public void setFieldValueShort(Object instance, short value) {
      try {
        field.setShort(instance, value);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(
            format("cannot access %s field", instance.getClass()));
      }
    }

    @Override
    public void setFieldValueInt(Object instance, int value) {
      try {
        field.setInt(instance, value);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(
            format("cannot access %s field", instance.getClass()));
      }
    }

    @Override
    public void setFieldValueLong(Object instance, long value) {
      try {
        field.setLong(instance, value);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(
            format("cannot access %s field", instance.getClass()));
      }
    }

    @Override
    public void setFieldValueFloat(Object instance, float value) {
      try {
        field.setFloat(instance, value);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(
            format("cannot access %s field", instance.getClass()));
      }
    }

    @Override
    public void setFieldValueDouble(Object instance, double value) {
      try {
        field.set(instance, value);
      } catch (IllegalAccessException e) {
        throw new IllegalStateException(
            format("cannot access %s field", instance.getClass()));
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

    @Override
    public String toString() {
      return toString(0);
    }

    @Override
    public String toString(int pad) {
      StringBuilder builder = new StringBuilder();
      builder.append(format(
          "[%s, %s] as \"%s\": ",
          getter == null ? null : getter.getName(),
          setter == null ? null : setter.getName(),
          getJsonName()));
      builder.append(getDescriptor().toString(pad));

      return builder.toString();
    }
  }

}
