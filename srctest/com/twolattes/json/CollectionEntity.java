package com.twolattes.json;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CollectionEntity {
  @Value(name = "friends")
  private List<String> buddies;

  public List<String> getBuddies() {
    return buddies;
  }
  
  public static class Factory {
    public CollectionEntity create(List<String> buddies) {
      CollectionEntity e = new CollectionEntity();
      
      e.buddies = new ArrayList<String>(buddies);
      
      return e;
    }
  }
}
