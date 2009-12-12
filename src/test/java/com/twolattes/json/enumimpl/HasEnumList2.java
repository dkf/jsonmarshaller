package com.twolattes.json.enumimpl;

import java.util.List;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class HasEnumList2 {
  @Value(ordinal = true)
  List<Abc> abcs;
}
