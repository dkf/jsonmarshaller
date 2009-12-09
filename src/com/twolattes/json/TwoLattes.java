package com.twolattes.json;

import static com.twolattes.json.BigDecimalDescriptor.BIG_DECIMAL_DESC;
import static com.twolattes.json.BooleanDescriptor.BOOLEAN_DESC;
import static com.twolattes.json.ByteDescriptor.BYTE_DESC;
import static com.twolattes.json.CharacterDescriptor.CHARARACTER_DESC;
import static com.twolattes.json.DoubleDescriptor.DOUBLE_DESC;
import static com.twolattes.json.FloatDescriptor.FLOAT_DESC;
import static com.twolattes.json.IntegerDescriptor.INT_DESC;
import static com.twolattes.json.JsonVisitor.BOOLEANS_ONLY;
import static com.twolattes.json.JsonVisitor.NUMBERS_ONLY;
import static com.twolattes.json.JsonVisitor.STRINGS_ONLY;
import static com.twolattes.json.LongDescriptor.LONG_DESC;
import static com.twolattes.json.ShortDescriptor.SHORT_DESC;
import static com.twolattes.json.StringDescriptor.STRING_DESC;
import static com.twolattes.json.Unification.extractRawType;
import static com.twolattes.json.Unification.getActualTypeArgument;
import static java.lang.String.format;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.twolattes.json.types.JsonType;

/**
 * The entry point to the JsonMarshaller. Creates {@link Marshaller} instances
 * for specific types.
 */
public final class TwoLattes {

  /* don't instantiate */ private TwoLattes() {}

  private static final Map<Class<?>, Pair<? extends Descriptor<?, ?>, ? extends JsonVisitor<?>>> map = makeMap();
  private static Map<Class<?>, Pair<? extends Descriptor<?, ?>, ? extends JsonVisitor<?>>> makeMap() {
    Map<Class<?>, Pair<? extends Descriptor<?, ?>, ? extends JsonVisitor<?>>> map =
      new HashMap<Class<?>, Pair<? extends Descriptor<?, ?>, ? extends JsonVisitor<?>>>();
    map.put(Byte.class, Pair.of(BYTE_DESC, NUMBERS_ONLY));
    map.put(Short.class, Pair.of(SHORT_DESC, NUMBERS_ONLY));
    map.put(Integer.class, Pair.of(INT_DESC, NUMBERS_ONLY));
    map.put(Long.class, Pair.of(LONG_DESC, NUMBERS_ONLY));
    map.put(BigDecimal.class, Pair.of(BIG_DECIMAL_DESC, NUMBERS_ONLY));
    map.put(Float.class, Pair.of(FLOAT_DESC, NUMBERS_ONLY));
    map.put(Double.class, Pair.of(DOUBLE_DESC, NUMBERS_ONLY));
    map.put(String.class, Pair.of(STRING_DESC, STRINGS_ONLY));
    map.put(Character.class, Pair.of(CHARARACTER_DESC, STRINGS_ONLY));
    map.put(Boolean.class, Pair.of(BOOLEAN_DESC, BOOLEANS_ONLY));
    return map;
  }

  /**
   * Creates a {@link Marshaller} for a specific type.
   *
   * @param clazz the {@link Byte}, {@link Short}, {@link Integer},
   *   {@link Long}, {@link BigInteger}, {@link BigDecimal}, {@link Float},
   *   {@link Double}, {@link String}, or {@link Boolean} class, an {@link Enum}
   *   type, or a class annotated with {@link Entity @Entity}
   */
  @SuppressWarnings("unchecked")
  public static <T> Marshaller<T> createMarshaller(Class<T> clazz) {
    Pair<? extends Descriptor<?, ?>, ? extends JsonVisitor<?>> p = map.get(clazz);
    if (p != null) {
      return new DescriptorBackedMarshaller(p.left, p.right);
    }
    if (Enum.class.isAssignableFrom(clazz)) {
      return new DescriptorBackedMarshaller(
          new EnumNameDescriptor((Class<? extends Enum>) clazz), STRINGS_ONLY);
    }
    return createEntityMarshaller(clazz);
  }

  /**
   * Creates an {@link EntityMarshaller} for a specific entity type.
   *
   * @param clazz A class annotated with {@link Entity @Entity}
   */
  public static <T> EntityMarshaller<T> createEntityMarshaller(Class<T> clazz) {
    return new Builder().createMarshaller(clazz);
  }

  public static Builder withType(Class<? extends JsonType<?, ?>> clazz) {
    return new Builder().withType(clazz);
  }

  /**
   * {@link EntityMarshaller} builder.
   */
  public static class Builder {

    private final Map<Type, Class<?>> types = new HashMap<Type, Class<?>>();

    public Builder withType(Class<? extends JsonType<?, ?>> clazz) {
      Class<?> rawType = extractRawType(
          getActualTypeArgument(clazz, JsonType.class, 0));
      if (rawType.equals(Array.class)) {
        throw new IllegalArgumentException(
            format(
                "%s overriding array's marshalling behavior cannot be registered",
                JsonType.class.getSimpleName()));
      }

      types.put(rawType, clazz);
      return this;
    }

    public <T> EntityMarshaller<T> createMarshaller(Class<T> clazz) {
      return new EntityMarshallerImpl<T>(clazz, types);
    }

    Class<?> get(Type type) {
      return types.get(type);
    }

  }

}
