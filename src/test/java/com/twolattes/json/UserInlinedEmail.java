package com.twolattes.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class UserInlinedEmail {
  @Value(views = "1")
  public EmailInline email;

  @Value(views = "2")
  public Map<String, EmailInline> emails = new HashMap<String, EmailInline>();

  @Value(views = "3", inline = false)
  public EmailInline emailNoInline;

  @Value(views = "4")
  public EmailInline[] emailsArray;

  @Value(views = "5")
  public List<EmailInline> emailsList = new ArrayList<EmailInline>();

  @Value(views = "6", inline = true)
  public EmailInline inlineTwice;
}
