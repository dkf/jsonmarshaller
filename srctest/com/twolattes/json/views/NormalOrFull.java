package com.twolattes.json.views;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class NormalOrFull {
  @Value int foo = 89;

  @Value(views = "full") int bar = 78;
}
