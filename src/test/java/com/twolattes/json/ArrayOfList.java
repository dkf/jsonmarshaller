package com.twolattes.json;

import java.util.List;

@Entity
public class ArrayOfList {
  @Value
  List<Double>[] weird;
}
