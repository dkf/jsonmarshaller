package com.twolattes.json.optional;

import java.util.List;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
class OptionalValues {

  @Value(optional = true)
  int intValue = 4;

  @Value(optional = true)
  Integer integer;

  @Value(optional = true)
  List<String> strings;

}
