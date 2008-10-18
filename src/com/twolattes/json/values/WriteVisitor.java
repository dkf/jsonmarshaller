package com.twolattes.json.values;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.twolattes.json.values.Json.Array;
import com.twolattes.json.values.Json.Boolean;
import com.twolattes.json.values.Json.Number;
import com.twolattes.json.values.Json.Object;
import com.twolattes.json.values.Json.String;

class WriteVisitor implements JsonVisitor<Void> {

  private final Writer writer;

  WriteVisitor(Writer writer) {
    this.writer = writer;
  }

  @Override
  public Void caseArray(Array array) throws IOException {
    writer.append('[');
    java.lang.String separator = "";
    for (Json value : array) {
      writer.append(separator);
      separator = ",";
      value.visit(this);
    }
    writer.append(']');
    return null;
  }

  @Override
  public Void caseBoolean(Boolean b) throws IOException {
    writer.append(java.lang.Boolean.toString(b.get()));
    return null;
  }

  @Override
  public Void caseNull() throws IOException {
    writer.append("null");
    return null;
  }

  @Override
  public Void caseNumber(Number number) throws IOException {
    writer.append(number.get().toPlainString());
    return null;
  }

  @Override
  public Void caseObject(Object object) throws IOException {
    writer.append('{');
    java.lang.String separator = "";
    for (Map.Entry<String, Json> entry : object.entrySet()) {
      writer.append(separator);
      separator = ",";
      // avoiding double dispatch
      caseString(entry.getKey());
      writer.append(':');
      entry.getValue().visit(this);
    }
    writer.append('}');
    return null;
  }

  @Override
  public Void caseString(String string) throws IOException {
    writer.append('"');
    for (char c : string.get().toCharArray()) {
      if (c == '"' || c == '\\') {
        writer.append('\\');
      }
      writer.append(c);
    }
    writer.append('"');
    return null;
  }

}
