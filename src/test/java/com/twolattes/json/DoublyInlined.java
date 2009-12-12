package com.twolattes.json;

@Entity
final class DoublyInlined {
  @Value Foo foo;

  @Entity(inline = true)
  final static class Foo {
    @Value Bar bar;
  }

  @Entity(inline = true)
  final static class Bar {
    @Value String hello;
  }
}
