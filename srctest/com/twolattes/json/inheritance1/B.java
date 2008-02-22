package com.twolattes.json.inheritance1;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity(discriminator = "b")
public class B extends A{
  @Value String name;
}
