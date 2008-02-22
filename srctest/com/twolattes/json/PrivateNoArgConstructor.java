package com.twolattes.json;

@Entity
public class PrivateNoArgConstructor {
  private @Value String foo;

  @SuppressWarnings("unused")
  private PrivateNoArgConstructor() {
  }
  
  PrivateNoArgConstructor(String foo) {
    this.foo = foo;
  }
  
  String getFoo() {
    return foo;
  }
}
