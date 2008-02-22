package com.twolattes.json.inheritanceerror;

import com.twolattes.json.Entity;

@Entity(subclasses = NoDiscriminator2Subclass.class,
    discriminatorName = "foo")
public class NoDiscriminator2 {
}
