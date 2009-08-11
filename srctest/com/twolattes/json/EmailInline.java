package com.twolattes.json;

@Entity(inline = true)
public class EmailInline {

  @Value
  public String email;

  public EmailInline() {
  }

  public EmailInline(String email) {
    this.email = email;
  }

}
