package com.twolattes.json.gettersetter;

import com.twolattes.json.Email;
import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class GetterSetter2 {
  private Email email;

  @Value(name = "foobar")
  public Email getEmail() {
    return email;
  }

  @Value(name = "foobar")
  public void setEmail(Email email) {
    this.email = email;
  }
}
