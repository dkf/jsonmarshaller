package com.twolattes.json.inheritanceerror;

import com.twolattes.json.Entity;

@Entity(discriminatorName = "t", subclasses = {
    DiscriminatorUsedTwice.A.class,
    DiscriminatorUsedTwice.B.class})
public class DiscriminatorUsedTwice {

  @Entity(discriminator = "a")
  static class A extends DiscriminatorUsedTwice {
  }

  @Entity(discriminator = "a")
  static class B extends DiscriminatorUsedTwice {
  }

}
