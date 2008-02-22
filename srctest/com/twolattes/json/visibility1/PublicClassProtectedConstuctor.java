package com.twolattes.json.visibility1;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class PublicClassProtectedConstuctor {
  @Value
  public int value = 9;
  
  protected PublicClassProtectedConstuctor() {
  }

  public static PublicClassProtectedConstuctor create() {
    return new PublicClassProtectedConstuctor();
  }
}
