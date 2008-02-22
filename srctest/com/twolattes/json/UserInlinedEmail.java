package com.twolattes.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
class UserInlinedEmail {
  @Value(views = "1")
  EmailInline email;
  
  @Value(views = "2")
  Map<String, EmailInline> emails = new HashMap<String, EmailInline>();
  
  @Value(views = "3", inline = false)
  EmailInline emailNoInline;
  
  @Value(views = "4")
  EmailInline[] emailsArray;
  
  @Value(views = "5")
  List<EmailInline> emailsList = new ArrayList<EmailInline>();
}
