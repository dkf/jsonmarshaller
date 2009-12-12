package com.twolattes.json.inheritance1;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity(discriminator = "c")
public class C extends A {
  @Value int age;
}
