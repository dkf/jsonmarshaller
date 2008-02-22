package com.twolattes.json.inheritance1;

import com.twolattes.json.Entity;

@Entity(subclasses = {B.class, C.class},
    discriminatorName = "type")
abstract public class A {
}
