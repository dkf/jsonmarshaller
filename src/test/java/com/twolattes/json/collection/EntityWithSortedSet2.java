package com.twolattes.json.collection;

import java.util.SortedSet;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class EntityWithSortedSet2 {

  SortedSet<PolymorphicEntity> entities;

  @Value
  public SortedSet<PolymorphicEntity> getEntities() {
    return entities;
  }

  @Value
  public void setEntities(SortedSet<PolymorphicEntity> entities) {
    this.entities = entities;
  }

}
