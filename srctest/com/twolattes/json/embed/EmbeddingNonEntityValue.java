package com.twolattes.json.embed;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class EmbeddingNonEntityValue {

  @Value(embed = true)
  int i;

}
