package com.twolattes.json.embed;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class EmbeddingWithConflictInSubclasses {

  @Value
  public String a;

  @Value(embed = true)
  public EmbeddedWithSubclasses embedded;

}
