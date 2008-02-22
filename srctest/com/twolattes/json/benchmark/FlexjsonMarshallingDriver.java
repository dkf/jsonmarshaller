package com.twolattes.json.benchmark;

import com.google.common.base.Function;
import com.sun.japex.TestCase;

import flexjson.JSONSerializer;

public class FlexjsonMarshallingDriver extends MarshallingDriver {
  @Override
  public Function<Object, String> createMarshaller(TestCase testCase) {
    final String includes = testCase.getParam(MarshallingDriver.FLEXJSON_INCLUDES);
    final JSONSerializer serializer = new JSONSerializer();
    if (includes == null || includes.length() == 0) {
      return new Function<Object, String>() {
        public String apply(Object entity) {
          return serializer.deepSerialize(entity);
        }
      };
    } else {
      return new Function<Object, String>() {
        public String apply(Object entity) {
          return serializer.include(includes).serialize(entity);
        }
      };
    }
  }
}
