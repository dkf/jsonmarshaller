package com.twolattes.json.embed;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class EmbeddingWithConflictOnDiscriminator {

  @Value
  public int t;

  @Value(embed = true)
  public EmbeddedWithSubclasses embedded;

}
