package com.twolattes.json;

@Entity(implementedBy = EntityInterfaceImpl.class)
public interface EntityInterface {
  @Value
  boolean isWhatever();
  
  @Value
  void setWhatever(boolean whatever);
}
