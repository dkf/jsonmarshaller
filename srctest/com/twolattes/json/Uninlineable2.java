package com.twolattes.json;

@Entity
@SuppressWarnings("unused")
public class Uninlineable2 {
  @Value
  private UnlineableAnnotated notImportant;
  
  @Entity(inline = true)
  public class UnlineableAnnotated {
    @Value
    private String one;
    
    @Value
    private String two;
  }
}
