package com.twolattes.json.inheritance3;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity(discriminator = "2")
public class C2 extends B {
  @Value String c2 = "class C2";
}
