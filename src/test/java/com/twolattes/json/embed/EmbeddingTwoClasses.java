package com.twolattes.json.embed;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class EmbeddingTwoClasses {

  @Value
  public FirstEmbeddedClass first;

  @Value
  public SecondEmbeddedClass second;

  @Value
  public int c;

  @Entity(embed = true)
  static public class FirstEmbeddedClass {

    @Value
    public int a;

  }

  @Entity(embed = true)
  static public class SecondEmbeddedClass {

    @Value
    public int b;

  }

}
