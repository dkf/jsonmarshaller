package com.twolattes.json;

import java.util.List;
import java.util.Set;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class CollectionInCollection {
  @Value
  private List<Set<String>> data;

  public List<Set<String>> getData() {
    return data;
  }
}
