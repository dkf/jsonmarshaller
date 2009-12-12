package com.twolattes.json.benchmark;

import com.google.common.base.Function;
import com.sun.japex.TestCase;
import com.twolattes.json.Marshaller;
import com.twolattes.json.TwoLattes;

public class JsonMarshallerMarshallingDriver extends MarshallingDriver {
  @Override
  @SuppressWarnings("unchecked")
  public Function<Object, String> createMarshaller(TestCase testCase) {
    String className = testCase.getParam(MarshallingDriver.ENTITY_CLASS);
    Class<?> entityClass;
    try {
      entityClass = JsonMarshallerMarshallingDriver.class.getClassLoader().loadClass(className);
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException(e);
    }
    final Marshaller<Object> serializer = (Marshaller<Object>) TwoLattes.createMarshaller(entityClass);
    return new Function<Object, String>() {
      public String apply(Object entity) {
        return serializer.marshall(entity).toString();
      }
    };
  }
}
