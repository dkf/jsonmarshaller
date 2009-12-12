package com.twolattes.json;

import java.net.URL;

@Entity
public class EntityWithURL {
  @Value(type = com.twolattes.json.types.URLType.class)
  private URL url;

  public URL getUrl() {
    return url;
  }

  public void setUrl(URL url) {
    this.url = url;
  }
}
