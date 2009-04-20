package com.twolattes.json.embed;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class EmbeddingWithGetterSetter {

  public EmbeddedOnEntity embedded;

  @Value
  public int c;

  @Value
  public EmbeddedOnEntity getFoo() {
    return embedded;
  }

  @Value
  public void setFoo(EmbeddedOnEntity embedded) {
    this.embedded = embedded;
  }

  @Entity(embed = true)
  static public class EmbeddedOnEntity {

    @Value
    public int a;

    @Value
    public int b;

  }

}
