package com.twolattes.json.embed;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class NestedEmbeddingWithConflict {

  @Value
  public String a;

  @Value(embed = true)
  public FirstNestedClass nested;

  @Entity
  static public class FirstNestedClass {

    @Value
    public String c;

    @Value(embed = true)
    public Embedded embedded;

  }

}
