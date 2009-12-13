package com.twolattes.json.benchmark;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sun.japex.ParamsImpl;
import com.sun.japex.TestCase;
import com.sun.japex.TestCaseImpl;


public class BenchmarkTest {
  @Test
  public void testFlexjson() throws Exception {
    testDriver(new FlexjsonMarshallingDriver(),
      "{\"a\":9,\"b\":67.9103,\"c\":\"f\"," +
      "\"class\":\"com.twolattes.json.benchmark.EntityA\"," +
      "\"d\":\"foo, bar\",\"e\":8.0}");
  }

  @Test
  public void testXStream() throws Exception {
    testDriver(new XStreamMarshallingDriver(),
      "{\"com.twolattes.json.benchmark.EntityA\": " +
      "{\n  \"a\": 9,\n  \"b\": {\"67.9103\"},\n  \"c\": {\"f\"},\n" +
      "  \"d\": \"foo, bar\",\n  \"e\": {\"8.0\"}\n}}");
  }

  @Test
  public void testJsonMarshaller() throws Exception {
    testDriver(new JsonMarshallerMarshallingDriver(),
      "{\"d\":\"foo, bar\",\"a\":9,\"c\":'f',\"b\":67.9103,\"e\":8}");
  }

  @Test
  public void testSojo() throws Exception {
    testDriver(new SojoMarshallingDriver(),
      "{\"d\":\"foo, bar\",\"a\":9,\"c\":\"f\",\"class\":" +
      "\"com.twolattes.json.benchmark.EntityA\"," +
      "\"~unique-id~\":\"0\",\"b\":67.9103,\"e\":8.0}");
  }

  private void testDriver(MarshallingDriver driver, String expected) {
    TestCase testCase = new TestCaseImpl("unit test", new ParamsImpl());
    testCase.setParam(MarshallingDriver.ENTITY_CLASS,
        "com.twolattes.json.benchmark.EntityA");
    driver.prepare(testCase);
    assertEquals(expected, driver.serialize());
  }
}
