package com.twolattes.json;

import java.net.URL;

@Entity
public class EntityWithURLNotDeclared {
  @Value
  private URL url;

  public URL getUrl() {
    return url;
  }

  public void setUrl(URL url) {
    this.url = url;
  }
}
