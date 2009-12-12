package com.twolattes.json.inheritanceerror;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity(discriminator = "doesn't matter")
public class ChildEntityOverridingValue extends ParentEntity {
  @Value(name = "a") String conflict;
}
