package com.twolattes.json;

import com.twolattes.json.types.Type;

public class BadType implements Type<String> {
  public BadType(String shouldBeNoArgConstuctor) {
  }
  
  public Class<String> getReturnedClass() {
    throw new UnsupportedOperationException();
  }

  public Object marshall(String entity) {
    throw new UnsupportedOperationException();
  }

  public String unmarshall(Object object) {
    throw new UnsupportedOperationException();
  }
}
