package com.twolattes.json.collection;

import com.twolattes.json.Entity;

@Entity(subclasses = ChildPolymorphicEntity.class, discriminatorName = "type")
public abstract class PolymorphicEntity {
}
