package com.twolattes.json.inheritance3;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity(discriminator = "1")
public class C1 extends B {
  @Value String c1 = "class C1";
}
