package com.twolattes.json.embed;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class Embedding {

  @Value
  public EmbeddedOnEntity embedded;

  @Value
  public int c;

  @Entity(embed = true)
  static public class EmbeddedOnEntity {

    @Value
    public int a;

    @Value
    public int b;

  }

}
