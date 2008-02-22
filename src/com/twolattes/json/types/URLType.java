package com.twolattes.json.types;

import java.net.MalformedURLException;
import java.net.URL;

import com.twolattes.json.Value;

/**
 * A type converter for {@link URL} objects. This converter may be used with
 * the {@link Value#type()} option.
 * 
 * @author Pascal
 */
public class URLType extends NullSafeType<URL> {
  @Override
  protected Object nullSafeMarshall(URL entity) {
    return entity.toString();
  }

  @Override
  protected URL nullSafeUnmarshall(Object object) {
    try {
      return new URL((String) object);
    } catch (MalformedURLException e) {
      throw new IllegalStateException();
    }
  }

  public Class<URL> getReturnedClass() {
    return URL.class;
  }
}
