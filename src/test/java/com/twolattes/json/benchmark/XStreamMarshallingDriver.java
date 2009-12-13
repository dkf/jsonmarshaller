package com.twolattes.json.benchmark;

import com.google.common.base.Function;
import com.sun.japex.TestCase;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

public class XStreamMarshallingDriver extends MarshallingDriver {
  @Override
  public Function<Object, String> createMarshaller(TestCase testCase) {
    final XStream serializer = new XStream(new JsonHierarchicalStreamDriver());
    return new Function<Object, String>() {
      public String apply(Object entity) {
        return serializer.toXML(entity);
      }
    };
  }
}
