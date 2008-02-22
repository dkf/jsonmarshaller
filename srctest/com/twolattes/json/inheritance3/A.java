package com.twolattes.json.inheritance3;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity(subclasses = {C1.class, C2.class},
    discriminatorName = "discriminator")
public class A {
  @Value String a = "class A";
}
