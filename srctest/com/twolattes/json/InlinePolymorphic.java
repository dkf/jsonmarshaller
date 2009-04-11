package com.twolattes.json;

@Entity
public class InlinePolymorphic {

  @Value(inline = true, optional = true)
  Polymorphic inlineMe;

  @Value(optional = true)
  Polymorphic doNotInlineMe;

  @Entity(
      subclasses = Polymorphic.class,
      discriminator = "bar",
      discriminatorName = "foo")
  static class Polymorphic {
  }

}
