package com.twolattes.json;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class EntityInEntity {
  public static final Class<?> INNER_ENTITY = BaseTypeEntity.class;

  @Value
  private BaseTypeEntity entity;

  public BaseTypeEntity getEntity() {
    return entity;
  }
}
