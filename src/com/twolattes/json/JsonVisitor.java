package com.twolattes.json;

import static com.twolattes.json.Json.NULL;

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

  public static class Illegal<T> implements JsonVisitor<T> {

    public T caseArray(Json.Array array) {
      throw new IllegalArgumentException();
    }

    public T caseBoolean(Json.Boolean bool) {
      throw new IllegalArgumentException();
    }

    public T caseNull() {
      throw new IllegalArgumentException();
    }

    public T caseNumber(Json.Number number) {
      throw new IllegalArgumentException();
    }

    public T caseObject(Json.Object object) {
      throw new IllegalArgumentException();
    }

    public T caseString(Json.String string) {
      throw new IllegalArgumentException();
    }

  }

  static final JsonVisitor<Json.String> STRINGS_ONLY = new Illegal<Json.String>() {
    @Override
    public Json.String caseNull() {
      return NULL;
    }
    @Override
    public Json.String caseString(Json.String string) {
      return string;
    }
  };

  static final JsonVisitor<Json.Number> NUMBERS_ONLY = new Illegal<Json.Number>() {
    @Override
    public Json.Number caseNull() {
      return NULL;
    }
    @Override
    public Json.Number caseNumber(Json.Number number) {
      return number;
    }
  };

  static final JsonVisitor<Json.Boolean> BOOLEANS_ONLY = new Illegal<Json.Boolean>() {
    @Override
    public Json.Boolean caseNull() {
      return NULL;
    }
    @Override
    public Json.Boolean caseBoolean(Json.Boolean bool) {
      return bool;
    }
  };

}
