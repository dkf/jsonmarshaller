package com.twolattes.json;

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

}
