package com.twolattes.json.types;

import static com.twolattes.json.Json.string;

import java.net.MalformedURLException;
import java.net.URL;

import com.twolattes.json.Json;
import com.twolattes.json.Value;

/**
 * A type converter for {@link URL} objects. This converter may be used with
 * the {@link Value#type()} option.
 */
public class URLType extends NullSafeType<URL, Json.String> {
  @Override
  protected Json.String nullSafeMarshall(URL entity) {
    return string(entity.toString());
  }

  @Override
  protected URL nullSafeUnmarshall(Json.String object) {
    try {
      return new URL(object.getString());
    } catch (MalformedURLException e) {
      throw new IllegalStateException();
    }
  }

  public Class<URL> getReturnedClass() {
    return URL.class;
  }
}
