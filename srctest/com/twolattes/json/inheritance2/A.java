package com.twolattes.json.inheritance2;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity(subclasses = {B.class, A.class},
    discriminatorName = "type",
    discriminator = "a")
public class A {
  @Value int age;
}
