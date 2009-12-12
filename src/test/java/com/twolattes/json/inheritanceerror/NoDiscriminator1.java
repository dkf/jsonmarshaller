package com.twolattes.json.inheritanceerror;

import com.twolattes.json.Entity;

@Entity(subclasses = NoDiscriminator1.class,
    discriminatorName = "t")
public class NoDiscriminator1 {
}
