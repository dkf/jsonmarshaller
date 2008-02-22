package com.twolattes.json.benchmark;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class EntityC {
  @Value
  private EntityA a = new EntityA();

  @Value
  private EntityB b = new EntityB();

  public EntityA getA() {
    return a;
  }

  public void setA(EntityA a) {
    this.a = a;
  }

  public EntityB getB() {
    return b;
  }

  public void setB(EntityB b) {
    this.b = b;
  }
}
