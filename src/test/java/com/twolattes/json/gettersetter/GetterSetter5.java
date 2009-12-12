package com.twolattes.json.gettersetter;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity(inline = true)
public class GetterSetter5 {

  String data;

  @Value
  public String getData(int i) {
    return data;
  }

  @Value
  public void setData(String data) {
    this.data = data;
  }

}
