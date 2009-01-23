package com.twolattes.json.types;

import static com.twolattes.json.Json.string;

import java.util.List;
import java.util.Map;

import com.twolattes.json.Entity;
import com.twolattes.json.Json;
import com.twolattes.json.Value;

@Entity
public class EntityRequiringTypeRegistration {

  @Value(optional = true)
  Weird weird1;

  @Value(type = TypePropertyHasPrecedenceOverRegisteredTypes.class, optional = true)
  Weird weird2;

  Weird weird3;

  Weird weird4;

  @Value(optional = true)
  List<Weird> list;

  @Value(optional = true)
  Map<String, Weird> map;

  @Value(optional = true)
  public Weird getWeird3() {
    return weird3;
  }

  @Value(optional = true)
  public void setWeird3(Weird weird3) {
    this.weird3 = weird3;
  }

  @Value(type = TypePropertyHasPrecedenceOverRegisteredTypes.class, optional = true)
  public Weird getWeird4() {
    return weird4;
  }

  @Value(type = TypePropertyHasPrecedenceOverRegisteredTypes.class, optional = true)
  public void setWeird4(Weird weird4) {
    this.weird4 = weird4;
  }

  static class Weird {
    String value;
    Weird(String string) {
      value = string;
    }
  }

  static class WeirdJsonType extends NullSafeType<Weird, Json.String> {

    @Override
    protected Json.String nullSafeMarshall(Weird entity) {
      return string(entity.value);
    }

    @Override
    protected Weird nullSafeUnmarshall(final Json.String object) {
      return new Weird(object.getString());
    }

    public Class<Weird> getReturnedClass() {
      return Weird.class;
    }

  }

  static class TypePropertyHasPrecedenceOverRegisteredTypes extends NullSafeType<Weird, Json.String> {

    @Override
    protected Json.String nullSafeMarshall(Weird entity) {
      return string("$" + entity.value + "$");
    }

    @Override
    protected Weird nullSafeUnmarshall(final Json.String object) {
      return new Weird("#" + object.getString() + "#");
    }

    public Class<Weird> getReturnedClass() {
      return Weird.class;
    }

  }

}
