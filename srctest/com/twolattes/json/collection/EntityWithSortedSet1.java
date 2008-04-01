package com.twolattes.json.collection;

import java.util.SortedSet;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class EntityWithSortedSet1 {

  @Value
  SortedSet<String> names;

}
