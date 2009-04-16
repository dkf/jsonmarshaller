package com.twolattes.json;

import static com.twolattes.json.Unification.extractRawType;
import static com.twolattes.json.Unification.getActualTypeArgument;
import static java.lang.String.format;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.twolattes.json.types.JsonType;


/**
 * The entry point to the JsonMarshaller. Creates {@link Marshaller} from
 * entities' {@link Class}.
 */
public final class TwoLattes {

  /* don't instantiate that class */ private TwoLattes() {}

  /**
   * Creates a {@link Marshaller} for the given class.
   */
  public static <T> Marshaller<T> createMarshaller(Class<T> clazz) {
    return new Builder().createMarshaller(clazz);
  }

  public static Builder withType(Class<? extends JsonType<?, ?>> clazz) {
    return new Builder().withType(clazz);
  }

  /**
   * Marshaller builder.
   */
  public static class Builder {

    private final Map<Class<?>, Class<?>> types = new HashMap<Class<?>, Class<?>>();

    Builder() {
    }

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

    @SuppressWarnings("unchecked")
    public <T> Marshaller<T> createMarshaller(Class<T> clazz) {
      return new MarshallerImpl(clazz, types);
    }

    Class<?> get(Type type) {
      return types.get(type);
    }

  }

}
