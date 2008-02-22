package com.twolattes.json.inheritance3;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class B extends A {
  @Value String b = "class B";
}
