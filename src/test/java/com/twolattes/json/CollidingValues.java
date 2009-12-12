package com.twolattes.json;

@Entity
public class CollidingValues {
  @Value(name = "a") String foo;
  @Value(name = "a") String bar;
}
