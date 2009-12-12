package com.twolattes.json.embed;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
class EmbeddingWithSubclasses {
  @Value String c;
  @Value(embed = true) EmbeddedWithSubclasses embedded;
}
