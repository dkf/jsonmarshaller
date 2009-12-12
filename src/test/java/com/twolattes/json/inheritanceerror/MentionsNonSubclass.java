package com.twolattes.json.inheritanceerror;

import com.twolattes.json.Entity;

@Entity(subclasses = {MentionsNonSubclass.NotASubclass.class},
    discriminatorName = "hello")
public class MentionsNonSubclass {
  @Entity(discriminator = "invalid")
  public static class NotASubclass {
  }
}
