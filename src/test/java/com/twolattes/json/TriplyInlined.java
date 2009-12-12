package com.twolattes.json;

@Entity
final class TriplyInlined {
  @Value Foo foo;

  @Entity(inline = true)
  final static class Foo {
    @Value Bar bar;
  }

  @Entity(inline = true)
  final static class Bar {
    @Value Baz baz;
  }

  @Entity(inline = true)
  final static class Baz {
    @Value String hello;
  }
}
