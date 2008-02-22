package com.twolattes.json.benchmark;

import java.util.HashSet;
import java.util.Set;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class EntityB {
  @Value
  int number = 7;

  @Value
  String description = "Hello World!";

  @Value
  Set<Double> doubles;

  public EntityB() {
    doubles = new HashSet<Double>();
    doubles.add(6.8);
    doubles.add(9.1);
    doubles.add(-102.4);
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<Double> getDoubles() {
    return doubles;
  }

  public void setDoubles(Set<Double> doubles) {
    this.doubles = doubles;
  }
}
