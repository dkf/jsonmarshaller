package com.twolattes.json.embed;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
class EmbeddingOptionalNull {

  @Value int c;
  @Value(embed = true, optional = true) Embedded embedded;

}
