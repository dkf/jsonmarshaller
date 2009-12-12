package com.twolattes.json.visibility1;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
class PackagePrivateClassProtectedConstuctor {
  @Value
  int value = 9;
  
  protected PackagePrivateClassProtectedConstuctor() {
  }
}
