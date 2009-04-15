package com.twolattes.json.embed;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class EmbeddingWithConflict {

  @Value
  public String a;

  @Value(embed = true)
  public Embedded embedded;

}
