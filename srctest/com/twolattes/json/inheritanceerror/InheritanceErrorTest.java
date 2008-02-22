package com.twolattes.json.inheritanceerror;

import org.junit.Test;

import com.twolattes.json.Marshaller;

public class InheritanceErrorTest {
  @Test(expected = IllegalArgumentException.class)
  public void testNoDiscriminatorName() throws Exception {
    Marshaller.create(NoDiscriminatorName.class);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testNoSubclasses() throws Exception {
    Marshaller.create(NoSubclasses.class);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testSubclassesAndInlining() throws Exception {
    Marshaller.create(SubclassesAndInlining.class);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testMentionsNonSubclass() throws Exception {
    Marshaller.create(MentionsNonSubclass.class);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testNoDiscriminator1() throws Exception {
    Marshaller.create(NoDiscriminator1.class);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testNoDiscriminator2() throws Exception {
    Marshaller.create(NoDiscriminator2.class);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testParentEntity() throws Exception {
    Marshaller.create(ParentEntity.class);
  }
}
