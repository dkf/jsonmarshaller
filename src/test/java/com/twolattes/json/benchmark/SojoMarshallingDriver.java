package com.twolattes.json.benchmark;

import net.sf.sojo.interchange.json.JsonSerializer;

import com.google.common.base.Function;
import com.sun.japex.TestCase;

public class SojoMarshallingDriver extends MarshallingDriver {
  @Override
  Function<Object, String> createMarshaller(TestCase testCase) {
    final JsonSerializer serializer = new JsonSerializer();
    return new Function<Object, String>() {
      public String apply(Object entity) {
        return serializer.serialize(entity).toString();
      }
    };
  }
}
