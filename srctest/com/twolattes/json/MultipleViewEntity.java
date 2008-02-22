package com.twolattes.json;

@Entity
public class MultipleViewEntity {
  @Value(views = {"full"})
  private String email;
  
  @Value(views = {"full"})
  private String motto;
  
  @Value(views = {"simple", "full"})
  private String name;
  
  @Value(views = {"simple"})
  private String user;
  
  @Value
  private String normal;

  public String getNormal() {
    return normal;
  }

  public void setNormal(String normal) {
    this.normal = normal;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getMotto() {
    return motto;
  }

  public void setMotto(String motto) {
    this.motto = motto;
  }
}
