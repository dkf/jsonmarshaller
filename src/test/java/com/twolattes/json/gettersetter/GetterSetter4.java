package com.twolattes.json.gettersetter;

import java.net.URL;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;
import com.twolattes.json.types.URLType;

@Entity(inline = true)
public class GetterSetter4 {

  URL data;

  @Value(type = URLType.class)
  public URL getData() {
    return data;
  }

  public URL getData(String s) {
    return data;
  }

  @Value(type = URLType.class)
  public void setData(URL data) {
    this.data = data;
  }

  public void setData(String data) {
  }

}
