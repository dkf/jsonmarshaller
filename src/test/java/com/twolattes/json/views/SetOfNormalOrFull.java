package com.twolattes.json.views;

import java.util.HashSet;
import java.util.Set;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class SetOfNormalOrFull {

  @Value Set<NormalOrFull> normalOrFulls;

  public SetOfNormalOrFull() {
    normalOrFulls = new HashSet<NormalOrFull>();
  }

}
