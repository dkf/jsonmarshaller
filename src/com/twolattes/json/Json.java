package com.twolattes.json;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * JSON value.
 *
 * @see http://www.json.org
 */
public abstract class Json {

  /**
   * An object, i.e. {@code {"hello":"world"}}.
   */
  public static class Object extends Json implements Map<String, Json> {

    private final Map<Json.String, Json> delegate = new TreeMap<String, Json>();

    @Override
    public void write(Writer writer) throws IOException {
      writer.append('{');
      java.lang.String separator = "";
      for (Json.String key : delegate.keySet()) {
        writer.append(separator);
        separator = ",";
        key.write(writer);
        writer.append(':');
        delegate.get(key).write(writer);
      }
      writer.append('}');
    }

    @Override
    public <T> T visit(JsonVisitor<T> visitor) {
      return visitor.caseObject(this);
    }

    public void clear() {
      delegate.clear();
    }

    public boolean containsKey(java.lang.Object key) {
      return delegate.containsKey(key);
    }

    public boolean containsValue(java.lang.Object value) {
      return delegate.containsValue(value);
    }

    public Set<Entry<String, Json>> entrySet() {
      return delegate.entrySet();
    }

    @Override
    public boolean equals(java.lang.Object o) {
      return delegate.equals(o);
    }

    public Json get(java.lang.Object key) {
      return delegate.get(key);
    }

    @Override
    public int hashCode() {
      return delegate.hashCode();
    }

    public boolean isEmpty() {
      return delegate.isEmpty();
    }

    public Set<String> keySet() {
      return delegate.keySet();
    }

    public Json put(String key, Json value) {
      return delegate.put(key, value);
    }

    public void putAll(Map<? extends String, ? extends Json> m) {
      delegate.putAll(m);
    }

    public Json remove(java.lang.Object key) {
      return delegate.remove(key);
    }

    public int size() {
      return delegate.size();
    }

    public Collection<Json> values() {
      return delegate.values();
    }

  }

  /**
   * An array, i.e {@code [0, 1, 2, 3, 4, 5]}.
   */
  public static class Array extends Json implements List<Json> {

    private final List<Json> delegate = new LinkedList<Json>();

    public Array(Json... values) {
      for (Json value : values) {
        delegate.add(value);
      }
    }

    @Override
    public void write(Writer writer) throws IOException {
      writer.append('[');
      java.lang.String separator = "";
      for (Json value : delegate) {
        writer.append(separator);
        separator = ",";
        value.write(writer);
      }
      writer.append(']');
    }

    @Override
    public <T> T visit(JsonVisitor<T> visitor) {
      return visitor.caseArray(this);
    }

    public void add(int index, Json element) {
      delegate.add(index, element);
    }

    public boolean add(Json e) {
      return delegate.add(e);
    }

    public boolean addAll(Collection<? extends Json> c) {
      return delegate.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends Json> c) {
      return delegate.addAll(index, c);
    }

    public void clear() {
      delegate.clear();
    }

    public boolean contains(java.lang.Object o) {
      return delegate.contains(o);
    }

    public boolean containsAll(Collection<?> c) {
      return delegate.containsAll(c);
    }

    @Override
    public boolean equals(java.lang.Object o) {
      return delegate.equals(o);
    }

    public Json get(int index) {
      return delegate.get(index);
    }

    @Override
    public int hashCode() {
      return delegate.hashCode();
    }

    public int indexOf(java.lang.Object o) {
      return delegate.indexOf(o);
    }

    public boolean isEmpty() {
      return delegate.isEmpty();
    }

    public Iterator<Json> iterator() {
      return delegate.iterator();
    }

    public int lastIndexOf(java.lang.Object o) {
      return delegate.lastIndexOf(o);
    }

    public ListIterator<Json> listIterator() {
      return delegate.listIterator();
    }

    public ListIterator<Json> listIterator(int index) {
      return delegate.listIterator(index);
    }

    public Json remove(int index) {
      return delegate.remove(index);
    }

    public boolean remove(java.lang.Object o) {
      return delegate.remove(o);
    }

    public boolean removeAll(Collection<?> c) {
      return delegate.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
      return delegate.retainAll(c);
    }

    public Json set(int index, Json element) {
      return delegate.set(index, element);
    }

    public int size() {
      return delegate.size();
    }

    public List<Json> subList(int fromIndex, int toIndex) {
      return delegate.subList(fromIndex, toIndex);
    }

    public java.lang.Object[] toArray() {
      return delegate.toArray();
    }

    public <T> T[] toArray(T[] a) {
      return delegate.toArray(a);
    }

  }

  /**
   * A boolean, i.e {@code true} or {@code false}.
   */
  public static class Boolean extends Json {

    private final boolean b;

    public Boolean(boolean b) {
      this.b = b;
    }

    @Override
    public void write(Writer writer) throws IOException {
      writer.append(java.lang.Boolean.toString(b));
    }

    @Override
    public <T> T visit(JsonVisitor<T> visitor) {
      return visitor.caseBoolean(this);
    }

    @Override
    public boolean equals(java.lang.Object obj) {
      if (!(obj instanceof Boolean)) {
        return false;
      } else {
        return !(this.b ^ ((Boolean) obj).b);
      }
    }

    @Override
    public int hashCode() {
      if (b) {
        return 982451653;
      } else {
        return 941083987;
      }
    }

    public boolean get() {
      return b;
    }

  }

  /**
   * A number, i.e. {@code 5}, {@code 78.90} or {@code 12728971932}.
   */
  public static class Number extends Json {

    private final BigDecimal number;

    public Number(double number) {
      this.number = BigDecimal.valueOf(number);
    }

    public Number(BigDecimal number) {
      this.number = number;
    }

    @Override
    public void write(Writer writer) throws IOException {
      writer.append(number.toPlainString());
    }

    @Override
    public <T> T visit(JsonVisitor<T> visitor) {
      return visitor.caseNumber(this);
    }

    @Override
    public boolean equals(java.lang.Object obj) {
      if (!(obj instanceof Number)) {
        return false;
      } else {
        return this.number.compareTo(((Number) obj).number) == 0;
      }
    }

    @Override
    public int hashCode() {
      return (int) number.doubleValue();
    }

    public BigDecimal get() {
      return number;
    }

  }

  /**
   * A string, i.e. {@code "hello"}.
   */
  public static class String extends Json implements Comparable<Json.String> {

    private final java.lang.String string;

    public String(java.lang.String string) {
      this.string = string;
    }

    @Override
    public void write(Writer writer) throws IOException {
      writer.append('"');
      int length = string.length();
      char c;
      boolean safe = true;
      for (int i = 0; safe && i < length; i++) {
        c = string.charAt(i);
        safe = c != '"' && c != '\\' && c != '\b' && c != '\n' && c != '\f' && c != '\r' && c != '\t';
      }
      if (safe) {
        writer.append(string);
      } else {
        for (int i = 0; i < length; i++) {
          switch (c = string.charAt(i)) {
            case '\b': writer.append("\\b"); break;
            case '\n': writer.append("\\n"); break;
            case '\f': writer.append("\\f"); break;
            case '\r': writer.append("\\r"); break;
            case '\t': writer.append("\\t"); break;
            case '"':
            case '\\':
              writer.append('\\');
            default:
              writer.append(c);
          }
        }
      }
      writer.append('"');
    }

    @Override
    public <T> T visit(JsonVisitor<T> visitor) {
      return visitor.caseString(this);
    }

    public int compareTo(Json.String that) {
      return this.string.compareTo(that.string);
    }

    @Override
    public boolean equals(java.lang.Object obj) {
      if (!(obj instanceof Json.String)) {
        return false;
      } else {
        return this.string.equals(((Json.String) obj).string);
      }
    }

    @Override
    public int hashCode() {
      return string.hashCode();
    }

    public java.lang.String get() {
      return string;
    }

  }

  /**
   * Null, i.e. {@code null}.
   */
  public static class Null extends Json {

    @Override
    public void write(Writer writer) throws IOException {
      writer.append("null");
    }

    @Override
    public <T> T visit(JsonVisitor<T> visitor) {
      return visitor.caseNull();
    }

    @Override
    public boolean equals(java.lang.Object obj) {
      return obj instanceof Json.Null;
    }

    @Override
    public int hashCode() {
      return 900772187;
    }

  }

  /**
   * {@code new Json.Null()}.
   */
  public static final Json.Null NULL = new Json.Null();

  /**
   * {@code new Json.Boolean(true)}.
   */
  public static final Json.Boolean TRUE = new Json.Boolean(true);

  /**
   * {@code new Json.Boolean(false)}.
   */
  public static final Json.Boolean FALSE = new Json.Boolean(false);

  @Override
  public java.lang.String toString() {
    StringWriter writer = new StringWriter();
    try {
      write(writer);
    } catch (IOException e) {
      // unreachable
      throw new RuntimeException(e);
    }
    return writer.toString();
  }

  /**
   * Write this value to a writer.
   */
  public abstract void write(Writer writer) throws IOException;

  /**
   * Read a JSON value from a reader.
   */
  public static Json read(Reader reader) throws IOException {
    CharStream stream = new CharStream(reader);
    return read(stream, skip(stream));
  }

  private static int skip(CharStream reader) throws IOException {
    int c;
    while (Character.isWhitespace(c = reader.read())) {
    }
    return c;
  }

  private static Json read(CharStream reader, int c) throws IOException {
    StringBuilder sb;
    switch (c) {
      // null
      case 'n': {
        int u = reader.read(), l1 = reader.read(), l2 = reader.read();
        if (u == 'u' && l1 == 'l' && l2 == 'l') {
          return Json.NULL;
        } else {
          throw new IllegalArgumentException("null expected");
        }
      }

      // true
      case 't': {
        int r = reader.read(), u = reader.read(), e = reader.read();
        if (r == 'r' && u == 'u' && e == 'e') {
          return Json.TRUE;
        } else {
          throw new IllegalArgumentException("true expected");
        }
      }

      // false
      case 'f': {
        int a = reader.read(), l = reader.read(), s = reader.read(), e = reader.read();
        if (a == 'a' && l == 'l' && s == 's' && e == 'e') {
          return Json.FALSE;
        } else {
          throw new IllegalArgumentException("false expected");
        }
      }

      // object
      case '{':
        Json.Object object = new Json.Object();
        // Unrolling to avoid state, note that the first time around we must not
        // skip when reading the key of the object.
        c = skip(reader);
        if (c == '}') {
          return object;
        }
        Json.String key = (Json.String) read(reader, c);
        if (skip(reader) != ':') {
          throw new IllegalArgumentException(": expected");
        }
        object.put(key, read(reader, skip(reader)));
        c = skip(reader);
        do {
          if (c == '}') {
            return object;
          }
          key = (Json.String) read(reader, skip(reader));
          if (skip(reader) != ':') {
            throw new IllegalArgumentException(": expected");
          }
          object.put(key, read(reader, skip(reader)));
        } while ((c = skip(reader)) == ',' || c == '}');
        throw new IllegalArgumentException("non terminated object literal");

      // array
      case '[':
        Json.Array array = new Json.Array();
        // Unrolling to avoid state, note that the first time around we must not
        // skip when reading the value added to the array.
        c = skip(reader);
        if (c == ']') {
          return array;
        }
        array.add(read(reader, c));
        c = skip(reader);
        do {
          if (c == ']') {
            return array;
          }
          array.add(read(reader, skip(reader)));
        } while ((c = skip(reader)) == ',' || c == ']');
        throw new IllegalArgumentException("non terminated array literal");

      // string
      case '"':
        sb = new StringBuilder();
        do {
          switch (c = reader.read()) {
            case '"':
              return new Json.String(sb.toString());
            case '\\':
              switch (c = reader.read()) {
                case '"':
                case '\\':
                case '/':
                  sb.append((char) c);
                  break;
                case 'b':
                  sb.append('\b');
                  break;
                case 'f':
                  sb.append('\f');
                  break;
                case 'n':
                  sb.append('\n');
                  break;
                case 'r':
                  sb.append('\r');
                  break;
                case 't':
                  sb.append('\t');
                  break;
                case 'u':
                  int h1 = reader.read(), h2 = reader.read(), h3 = reader.read(), h4 = reader.read();
                  if (('a' <= h1 && h1 <= 'f' || 'A' <= h1 && h1 <= 'F' || '0' <= h1 && h1 <= '9') &&
                      ('a' <= h2 && h2 <= 'f' || 'A' <= h2 && h2 <= 'F' || '0' <= h2 && h2 <= '9') &&
                      ('a' <= h3 && h3 <= 'f' || 'A' <= h3 && h3 <= 'F' || '0' <= h3 && h3 <= '9') &&
                      ('a' <= h4 && h4 <= 'f' || 'A' <= h4 && h4 <= 'F' || '0' <= h4 && h4 <= '9')) {
                    sb.append((char) (fromHex(h1) << 12 | fromHex(h2) << 8 | fromHex(h3) << 4 | fromHex(h4)));
                  } else {
                    throw new IllegalArgumentException("invalid hex code");
                  }
              }
              break;
            case -1:
              throw new IllegalArgumentException("non terminated string");
            default:
              sb.append((char) c);
          }
        } while (true);

      // number
      case '-':
      case '0':
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
        sb = new StringBuilder();
        do {
          sb.append((char) c);
        } while ((c = reader.read()) <= '9' && '0' <= c);
        switch (c) {
          case '.':
            do {
              sb.append((char) c);
            } while ((c = reader.read()) <= '9' && '0' <= c);
          case 'e':
          case 'E':
            if (c != 'e' && c != 'E') {
              reader.unread(c);
              return new Json.Number(new BigDecimal(sb.toString()));
            }
            sb.append((char) c);
            c = reader.read();
            if (c != '+' && c != '-' && c < '0' && '9' < c) {
              reader.unread(c);
              return new Json.Number(new BigDecimal(sb.toString()));
            }
            do {
              sb.append((char) c);
            } while ((c = reader.read()) <= '9' && '0' <= c);
          default:
            reader.unread(c);
            return new Json.Number(new BigDecimal(sb.toString()));
        }

      // error
      default:
        throw new IllegalArgumentException("illegal character " + (char) c);
    }
  }

  static int fromHex(int codePoint) {
    // '9' = 57, 'F' = 70, 'f' = 102
    return (codePoint <= '9') ? codePoint - '0' :
        (codePoint <= 'F') ? codePoint - 'A' + 10 : codePoint - 'a' + 10;
  }

  /**
   * Create a JSON value from a string.
   */
  public static Json fromString(java.lang.String input) {
    try {
      return read(new StringReader(input));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Visit this value.
   */
  public abstract <T> T visit(JsonVisitor<T> visitor);

}
