package com.twolattes.json.embed;

import java.util.List;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class ListOfEmbeddings {
  @Value List<EmbeddingOnValue> embeddings;
}
