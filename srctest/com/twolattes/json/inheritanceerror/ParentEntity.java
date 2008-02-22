package com.twolattes.json.inheritanceerror;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity(subclasses = ChildEntityOverridingValue.class,
    discriminatorName = "typopyt")
public class ParentEntity {
  @Value(name = "a") String conflict;
}
