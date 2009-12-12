package com.twolattes.json;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map.Entry;

public class PrettyPrinter implements JsonVisitor<Void> {

  private final String indent;
  private final Writer writer;
  private String currentIndent = "";

  public PrettyPrinter(String indent, Writer writer) {
    this.indent = indent;
    this.writer = writer;
  }

  public Void caseArray(Json.Array array) {
    /* [
     *   v1,
     *   v2,
     *   ...,
     *   vn
     * ]
     */
    try {
      final String origCurrentIndent = currentIndent;
      writer.append("[\n");
      currentIndent = currentIndent + indent;
      Iterator<Json.Value> iterator = array.values().iterator();
      while (iterator.hasNext()) {
        Json.Value value = iterator.next();
        writer.append(currentIndent);
        value.visit(this);
        if (iterator.hasNext()) {
          writer.append(',');
        }
        writer.append("\n");
      }
      currentIndent = origCurrentIndent;
      writer.append(currentIndent);
      writer.append("]");
      return null;
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  public Void caseBoolean(Json.Boolean bool) {
    try {
      writer.append(bool.toString());
      return null;
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  public Void caseNull() {
    try {
      writer.append("null");
      return null;
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  public Void caseNumber(Json.Number number) {
    try {
      writer.append(number.toString());
      return null;
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  public Void caseObject(Json.Object object) {
    /* {
     *   "k1": v1,
     *   "k2": v2,
     *   ...
     *   "kn": vn
     * }
     */
    try {
      final String origCurrentIndent = currentIndent;
      writer.append("{\n");
      currentIndent = currentIndent + indent;
      Iterator<Entry<Json.String, Json.Value>> iterator = object.entrySet().iterator();
      while (iterator.hasNext()) {
        Entry<Json.String, Json.Value> field = iterator.next();
        writer.append(currentIndent);
        writer.append(field.getKey().toString());
        writer.append(":");
        field.getValue().visit(this);
        if (iterator.hasNext()) {
          writer.append(',');
        }
        writer.append("\n");
      }
      currentIndent = origCurrentIndent;
      writer.append(currentIndent);
      writer.append("}");
      return null;
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  public Void caseString(Json.String string) {
    try {
      writer.append(string.toString());
      return null;
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

}
