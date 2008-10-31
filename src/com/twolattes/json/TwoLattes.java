package com.twolattes.json;

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
    return new MarshallerImpl(clazz);
  }

}
