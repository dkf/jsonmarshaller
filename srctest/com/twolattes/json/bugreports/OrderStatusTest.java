/**
 * Copyright 2009 KaChing Group Inc. Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.twolattes.json.bugreports;

import static com.twolattes.json.TwoLattes.createMarshaller;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.twolattes.json.Json;

public class OrderStatusTest {

  @Test
  public void testUnmarshal() throws Exception {
    String order = "{\"order\":{\"action\":\"buy\",\"commission\":0.04," +
        "\"id\":\"87005\",\"placed\":\"2009-03-23T20:25:43Z\",\"quantity\":" +
        "1.0,\"symbol\":\"GOOG\",\"type\":\"market-order\"}," +
        "\"type\":\"order-status-warning\"," +
        "\"warning\":{\"type\":\"market-closed\"}}";
      OrderStatus os = createMarshaller(OrderStatus.class).unmarshall(
          (Json.Object) Json.fromString(order));
      assertTrue(os instanceof OrderStatusWarning);
  }

}
