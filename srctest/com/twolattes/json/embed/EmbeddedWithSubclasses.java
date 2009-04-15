package com.twolattes.json.embed;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity(discriminatorName = "t", subclasses = {
    EmbeddedWithSubclasses.FirstEmbeddedSubclass.class,
    EmbeddedWithSubclasses.SecondEmbeddedSubclass.class
})
public class EmbeddedWithSubclasses {

  @Entity(discriminator = "first")
  static public class FirstEmbeddedSubclass extends EmbeddedWithSubclasses {

    @Value
    public int a;

  }

  @Entity(discriminator = "second")
  static public class SecondEmbeddedSubclass extends EmbeddedWithSubclasses {

    @Value
    public int b;

  }

}
