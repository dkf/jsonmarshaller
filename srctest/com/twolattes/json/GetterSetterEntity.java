package com.twolattes.json;

@Entity
public class GetterSetterEntity {
  private String someRandomField;
  
  @Value
  String getName() {
    return someRandomField;
  }
  
  @Value
  void setName(String name) {
    this.someRandomField = name;
  }
}
