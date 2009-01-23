package com.twolattes.json;

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
  @SuppressWarnings("unchecked")
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

    private final Map<Type, Class<?>> types = new HashMap<Type, Class<?>>();

    Builder() {
    }

    public Builder withType(Class<? extends JsonType<?, ?>> clazz) {
      types.put(
          Unification.getActualTypeArgument(clazz, JsonType.class, 0),
          clazz);
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
