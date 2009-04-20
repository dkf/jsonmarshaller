package com.twolattes.json.embed;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity(discriminatorName = "t", subclasses = {
    EmbeddedWithSubclasses.FirstEmbeddedSubclass.class,
    EmbeddedWithSubclasses.SecondEmbeddedSubclass.class
})
class EmbeddedWithSubclasses {

  @Entity(discriminator = "first")
  static class FirstEmbeddedSubclass extends EmbeddedWithSubclasses {
    @Value int a;
  }

  @Entity(discriminator = "second")
  static class SecondEmbeddedSubclass extends EmbeddedWithSubclasses {
    @Value int b;
  }

}
