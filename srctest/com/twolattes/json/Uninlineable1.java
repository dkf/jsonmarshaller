package com.twolattes.json;

@Entity
public class Uninlineable1 {
  @Value(inline = true)
  BaseTypeEntity notInteresting;
}
