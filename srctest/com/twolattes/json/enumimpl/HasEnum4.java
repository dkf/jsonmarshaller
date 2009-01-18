package com.twolattes.json.enumimpl;

import static com.twolattes.json.Json.string;
import static com.twolattes.json.enumimpl.Abc.A;

import com.twolattes.json.Entity;
import com.twolattes.json.Json;
import com.twolattes.json.Value;
import com.twolattes.json.types.JsonType;

@Entity
public class HasEnum4 {
  @Value(type = HasEnum4.Type.class) Abc abc;

  static class Type implements JsonType<Abc, Json.String> {

    public Class<Abc> getReturnedClass() {
      return Abc.class;
    }

    public Json.String marshall(Abc entity) {
      return string("myvalue");
    }

    public Abc unmarshall(Json.String object) {
      return A;
    }

  }
}
