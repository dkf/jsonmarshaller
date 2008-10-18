package com.twolattes.json.values;

/**
 * A visitor for {@link Json} values.
 */
public interface JsonVisitor<T> {

  T caseObject(Json.Object object) throws Exception;

  T caseArray(Json.Array array) throws Exception;

  T caseNumber(Json.Number number) throws Exception;

  T caseBoolean(Json.Boolean b) throws Exception;

  T caseString(Json.String string) throws Exception;

  T caseNull() throws Exception;

}
