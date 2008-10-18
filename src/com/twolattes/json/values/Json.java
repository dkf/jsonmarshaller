package com.twolattes.json.values;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

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

    private final Map<Json.String, Json> delegate = new HashMap<String, Json>();

    @Override
    <T> T visitImpl(JsonVisitor<T> visitor) throws Exception {
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

    private final List<Json> delegate = new ArrayList<Json>();

    public Array(Json... values) {
      for (Json value : values) {
        delegate.add(value);
      }
    }

    @Override
    <T> T visitImpl(JsonVisitor<T> visitor) throws Exception {
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
    <T> T visitImpl(JsonVisitor<T> visitor) throws Exception {
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
    <T> T visitImpl(JsonVisitor<T> visitor) throws Exception {
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
  public static class String extends Json {

    private final java.lang.String string;

    public String(java.lang.String string) {
      this.string = string;
    }

    @Override
    <T> T visitImpl(JsonVisitor<T> visitor) throws Exception {
      return visitor.caseString(this);
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
    <T> T visitImpl(JsonVisitor<T> visitor) throws Exception {
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

  /**
   * Visit this JSON value.
   */
  abstract <T> T visitImpl(JsonVisitor<T> visitor) throws Exception;

  /**
   * Visit this JSON value.
   */
  public final <T> T visit(JsonVisitor<T> visitor) {
    try {
      return visitImpl(visitor);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public java.lang.String toString() {
    StringWriter writer = new StringWriter();
    visit(new WriteVisitor(writer));
    return writer.toString();
  }

  /**
   * Write this value to a writer.
   */
  public void write(Writer writer) throws IOException {
    try {
      visitImpl(new WriteVisitor(writer));
    } catch (IOException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
