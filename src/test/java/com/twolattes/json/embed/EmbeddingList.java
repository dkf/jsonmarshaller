package com.twolattes.json.embed;

import java.util.List;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class EmbeddingList {
  @Value(embed = true) List<Embedded> embeddeds;
}
