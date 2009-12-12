package com.twolattes.json.embed;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
class TwoEmbeddingClasses {
  @Value int d;
  @Value(embed = true) Foo foo;

  @Entity
  static class Foo {
    @Value int c;
    @Value Bar bar;
  }

  @Entity
  static class Bar {
    @Value int b;
    @Value Baz baz;
  }

  @Entity(embed = true)
  static class Baz {
    @Value int a;
  }

}
