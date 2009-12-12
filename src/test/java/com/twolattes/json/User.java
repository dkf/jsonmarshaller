package com.twolattes.json;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class User {
  @Value(inline = true)
  Email email;
}
