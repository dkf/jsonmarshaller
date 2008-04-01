package com.twolattes.json.collection;

import com.twolattes.json.Entity;

@Entity(discriminator = "child")
public class ChildPolymorphicEntity extends PolymorphicEntity {
}
