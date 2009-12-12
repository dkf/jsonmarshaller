package com.twolattes.json.gettersetter;

import com.twolattes.json.Email;
import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class GetterSetter6 {
  private Email email;

  @Value(inline = true)
  public Email getEmail() {
    return email;
  }

  @Value
  public void setEmail(Email email) {
    this.email = email;
  }
}
