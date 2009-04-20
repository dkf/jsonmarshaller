package com.twolattes.json;

@Entity
final class DoublyInlinedWithGetters {
  Foo foo;

  @Value
  public Foo getFoo() {
    return foo;
  }

  @Entity(inline = true)
  final static class Foo {
    Bar bar;

    @Value
    public Bar getBar() {
      return bar;
    }
  }

  @Entity(inline = true)
  final static class Bar {
    String hello;

    @Value
    public String getHello() {
      return hello;
    }
  }
}
