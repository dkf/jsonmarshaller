package com.twolattes.json.embed;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class EmbeddingWithSubclasses {

  @Value
  public String c;

  @Value(embed = true)
  public EmbeddedWithSubclasses embedded;

}
