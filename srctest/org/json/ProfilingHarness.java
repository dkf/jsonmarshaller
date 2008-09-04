package org.json;

import java.io.StringWriter;

public class ProfilingHarness {

  public static void main(String[] args) {
    StringWriter writer = new StringWriter();
    JSONObject o = new JSONObject("{a:'5',b:1,c:[1,2,3,[5,6,0]]}");

    for (int i = 0; i < 5000000; i++) {
      writer.append(o.toString());
    }
  }

}
