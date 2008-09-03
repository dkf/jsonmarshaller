package com.twolattes.json.gettersetter;

import java.net.URL;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;
import com.twolattes.json.types.URLType;

@Entity(inline = true)
public class GetterSetter4 {

  URL data;

  void getData(String goo) {
  }

  void setData() {
  }

  @Value(type = URLType.class) public URL getData() { return data; }

  @Value(type = URLType.class) public void setData(URL data) { this.data = data; }

}
