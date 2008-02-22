package com.twolattes.json.visibility1;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
class PackagePrivateClassPrivateConstuctor {
  @Value
  int value = 9;
  
  private PackagePrivateClassPrivateConstuctor() {
  }
  
  static PackagePrivateClassPrivateConstuctor create() {
    return new PackagePrivateClassPrivateConstuctor();
  }
}
