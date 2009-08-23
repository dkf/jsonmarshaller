package com.twolattes.json;

import com.twolattes.json.Json.Array;
import com.twolattes.json.Json.Boolean;
import com.twolattes.json.Json.Number;
import com.twolattes.json.Json.Object;
import com.twolattes.json.Json.String;

/**
 * Visitor for {@link Json} values.
 */
public interface JsonVisitor<T> {

  T caseNull();

  T caseNumber(Json.Number number);

  T caseString(Json.String string);

  T caseBoolean(Json.Boolean bool);

  T caseArray(Json.Array array);

  T caseObject(Json.Object object);

  public static class Empty<T> implements JsonVisitor<T> {

    public T caseArray(Array array) {
      return null;
    }

    public T caseBoolean(Boolean bool) {
      return null;
    }

    public T caseNull() {
      return null;
    }

    public T caseNumber(Number number) {
      return null;
    }

    public T caseObject(Object object) {
      return null;
    }

    public T caseString(String string) {
      return null;
    }

  }

  public static class Default<T> implements JsonVisitor<T> {

    private final T defaultValue;

    public Default(T defaultValue) {
      this.defaultValue = defaultValue;
    }

    public T caseArray(Json.Array array) {
      return defaultValue;
    }

    public T caseBoolean(Json.Boolean bool) {
      return defaultValue;
    }

    public T caseNull() {
      return defaultValue;
    }

    public T caseNumber(Json.Number number) {
      return defaultValue;
    }

    public T caseObject(Json.Object object) {
      return defaultValue;
    }

    public T caseString(Json.String string) {
      return defaultValue;
    }

  }

}
