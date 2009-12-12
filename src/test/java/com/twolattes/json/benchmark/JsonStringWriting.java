package com.twolattes.json.benchmark;

import static com.twolattes.json.Json.string;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.twolattes.json.Json;

public class JsonStringWriting {

  public static void main(String[] args) throws IOException {
    Json.String string = string("{\"a\":9,\"b\":67.9103,\"c\":\"f\"," +
        "\"class\":\"com.twolattes.json.benchmark.EntityA\"," +
        "\"d\":\"foo, bar\",\"e\":8.0}" +
        "{\"a\":9,\"b\":67.9103,\"c\":\"f\"," +
        "\"class\":\"com.twolattes.json.benchmark.EntityA\"," +
        "\"d\":\"foo, bar\",\"e\":8.0}" +
        "{\"a\":9,\"b\":67.9103,\"c\":\"f\"," +
        "\"class\":\"com.twolattes.json.benchmark.EntityA\"," +
        "\"d\":\"foo, bar\",\"e\":8.0}" +
        "{\"a\":9,\"b\":67.9103,\"c\":\"f\"," +
        "\"class\":\"com.twolattes.json.benchmark.EntityA\"," +
        "\"d\":\"foo, bar\",\"e\":8.0}");

    long start = System.currentTimeMillis();
    for (int i = 0; i < 100000; i++) {
      OutputStreamWriter out = new OutputStreamWriter(new ByteArrayOutputStream());
      string.write(out);
      out.flush();
    }
    long end = System.currentTimeMillis();
    System.out.println("total " + (end - start) + "ms");
  }

}
