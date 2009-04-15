package com.twolattes.json.embed;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class EmbeddingWithConflictBetweenSubclassesAndEmbedded {

  @Value(embed = true)
  public EmbeddedWithSubclasses first;

  @Value(embed = true)
  public Embedded second;

}
