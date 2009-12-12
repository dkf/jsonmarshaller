package com.twolattes.json.inheritanceerror;

import com.twolattes.json.Entity;

@Entity(inline = true,
    subclasses = {SubclassesAndInlining.class},
    discriminatorName = "t",
    discriminator = "me")
public class SubclassesAndInlining {
}
