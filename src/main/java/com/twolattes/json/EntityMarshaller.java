package com.twolattes.json;

public interface EntityMarshaller<T> extends Marshaller<T> {

  Json.Object marshall(T object);

  Json.Object marshall(T object, String view);

}
