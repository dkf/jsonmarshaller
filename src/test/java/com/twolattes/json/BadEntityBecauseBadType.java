package com.twolattes.json;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class BadEntityBecauseBadType {
  @Value(type = BadType.class) String bad;
}
