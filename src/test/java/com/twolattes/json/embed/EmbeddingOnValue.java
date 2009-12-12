package com.twolattes.json.embed;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class EmbeddingOnValue {
  @Value(embed = true) Embedded embedded;
  @Value int c;
}
