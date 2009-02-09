package com.twolattes.json.types;

import static com.twolattes.json.Json.number;
import static com.twolattes.json.Json.string;

import com.twolattes.json.Entity;
import com.twolattes.json.Json;
import com.twolattes.json.Value;

@Entity
public class EntityRequiringTypeRegistration2 {

  @Value(optional = true)
  Id<EntityRequiringTypeRegistration2> id;

  @Value
  int[] array;

  static class Id<T> {
    long id;
  }

  @SuppressWarnings("unchecked")
  static class IdJsonType extends NullSafeType<Id<?>, Json.Number> {

    @Override
    protected Json.Number nullSafeMarshall(Id<?> entity) {
      return number(entity.id);
    }

    @Override
    protected Id<?> nullSafeUnmarshall(final Json.Number object) {
      return new Id() {{ this.id = object.getNumber().longValue(); }};
    }

  }

  static class ArrayJsonType extends NullSafeType<int[], Json.String> {

    @Override
    protected Json.String nullSafeMarshall(int[] entity) {
      return string("foobar");
    }

    @Override
    protected int[] nullSafeUnmarshall(Json.String object) {
      return new int[] { 8 };
    }

  }

}
