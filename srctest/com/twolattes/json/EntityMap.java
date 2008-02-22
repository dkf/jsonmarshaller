package com.twolattes.json;

import java.util.HashMap;
import java.util.Map;

@Entity
public class EntityMap {
  @Value
  private final Map<String, Email> emails = new HashMap<String, Email>();
  
  public void addEmail(String name, Email email) {
    emails.put(name, email);
  }

  public int numberOfEmails() {
    return emails.size();
  }
  
  public Email get(String name) {
    return emails.get(name);
  }

  public Map<String, Email> getEmails() {
    return emails;
  }
}
