package com.twolattes.json;

@Entity
public class Email {

  @Value
  public String email;

  public Email() {
  }

  public Email(String email) {
    this.email = email;
  }

}
