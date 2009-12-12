package com.twolattes.json.gettersetter;

import com.twolattes.json.Email;
import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class GetterSetter3 {
  private Email email;

  @Value(optional = true)
  public Email getEmail() {
    return email;
  }

  @Value(optional = true)
  public void setEmail(Email email) {
    this.email = email;
  }
}
