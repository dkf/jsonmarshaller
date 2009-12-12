package com.twolattes.json.optional;

import java.util.List;

import com.twolattes.json.Email;
import com.twolattes.json.EmailInline;
import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
class OptionalValues {

  @Value(optional = true)
  int intValue = 4;

  @Value(optional = true)
  Integer integer;

  @Value(optional = true)
  List<String> strings;

  @Value(optional = true, inline = true)
  Email emailInlined;

  @Value(optional = true)
  EmailInline emailInline;

  int intValueGs = 90;

  Integer integerGs;

  List<String> stringsGs;

  Email emailInlinedGs;

  EmailInline emailInlineGs;

  @Value(optional = true)
  public int getIntValueGs() {
    return intValueGs;
  }

  @Value(optional = true)
  public void setIntValueGs(int intValueGs) {
    this.intValueGs = intValueGs;
  }

  @Value(optional = true)
  public Integer getIntegerGs() {
    return integerGs;
  }

  @Value(optional = true)
  public void setIntegerGs(Integer integerGs) {
    this.integerGs = integerGs;
  }

  @Value(optional = true)
  public List<String> getStringsGs() {
    return stringsGs;
  }

  @Value(optional = true)
  public void setStringsGs(List<String> stringsGs) {
    this.stringsGs = stringsGs;
  }

  @Value(optional = true, inline = true)
  public Email getEmailInlinedGs() {
    return emailInlinedGs;
  }

  @Value(optional = true, inline = true)
  public void setEmailInlinedGs(Email emailInlinedGs) {
    this.emailInlinedGs = emailInlinedGs;
  }

  @Value(optional = true)
  public EmailInline getEmailInlineGs() {
    return emailInlineGs;
  }

  @Value(optional = true)
  public void setEmailInlineGs(EmailInline emailInlineGs) {
    this.emailInlineGs = emailInlineGs;
  }

}
