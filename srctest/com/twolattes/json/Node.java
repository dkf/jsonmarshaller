package com.twolattes.json;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Node {
  @Value
  private Set<Node> neighbors = new HashSet<Node>();

  void addNeighbor(Node n) {
    neighbors.add(n);
  }

  public Set<Node> neighbors() {
    return Collections.unmodifiableSet(neighbors);
  }
}
