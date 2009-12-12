package com.twolattes.json;

import java.net.MalformedURLException;
import java.net.URL;

import com.twolattes.json.types.URLType;

@Entity
public class TypeOnGetter {

  private URL url;

  public TypeOnGetter() {
    try {
      url = new URL("http://www.kaching.com");
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  @Value(type = URLType.class)
  public URL getUrl() {
    return url;
  }

}
