package com.twolattes.json.embed;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class EmbeddingWithInlinedValue {

  @Value int a;

  JustB b;

  @Value(embed = true)
  public JustB getB() {
    return b;
  }

  @Value(embed = true)
  public void setB(JustB b) {
    this.b = b;
  }

  @Entity
  static class JustA {
    @Value int a;
  }

  @Entity
  static class JustB {
    @Value(inline = true) JustA b;
  }

}
