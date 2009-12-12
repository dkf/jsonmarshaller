package com.twolattes.json.embed;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class EmbeddingTwoClassesWithConflict {

  @Value(embed = true)
  public Embedded first;

  @Value(embed = true)
  public Embedded second;

}
