package com.twolattes.json;

import com.twolattes.json.types.JsonType;

public class BadType implements JsonType<String, Json.Value> {
  public BadType(String shouldBeNoArgConstuctor) {
  }

  public Class<String> getReturnedClass() {
    throw new UnsupportedOperationException();
  }

  public Json.Value marshall(String entity) {
    throw new UnsupportedOperationException();
  }

  public String unmarshall(Json.Value object) {
    throw new UnsupportedOperationException();
  }
}
