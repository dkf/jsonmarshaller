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

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity(discriminator = "order-status-warning")
public class OrderStatusWarning extends OrderStatus {

  @Value
  private Order order;

  @Value
  private Warning warning;

  /* JSON */ @SuppressWarnings("unused") private OrderStatusWarning() {}

  public OrderStatusWarning(Warning warning, Order order) {
    this.warning = warning;
    this.order = order;
  }

  public Order getOrder() {
    return order;
  }

  public Warning getWarning() {
    return warning;
  }

  @Entity(discriminatorName = "type", subclasses = {
      OrderStatusWarning.UnknownSecurity.class,
      OrderStatusWarning.MarketClosed.class,
      OrderStatusWarning.NotEnoughCash.class,
      OrderStatusWarning.NotEnoughEquity.class,
      OrderStatusWarning.NotTradedToday.class,
      OrderStatusWarning.LimitOrderOnly.class
  })
  public static class Warning {
  }

  @Entity(discriminator = "market-closed")
  public static class MarketClosed extends Warning {
  }

  @Entity(discriminator = "unknown-security")
  public static class UnknownSecurity extends Warning {
  }

  @Entity(discriminator = "limit-order-only")
  public static class LimitOrderOnly extends Warning {
  }

  @Entity(discriminator = "not-enough-equity")
  public static class NotEnoughEquity extends Warning {

    @Value
    private int allowedShares;

    /* JSON */ @SuppressWarnings("unused") private NotEnoughEquity() {}

    public NotEnoughEquity(int allowedShares) {
      this.allowedShares = allowedShares;
    }

    public double getAllowedShares() {
      return allowedShares;
    }

  }

  @Entity(discriminator = "not-enough-cash")
  public static class NotEnoughCash extends Warning {

    @Value
    private int allowedShares;

    /* JSON */ @SuppressWarnings("unused") private NotEnoughCash() {}

    public NotEnoughCash(int allowedShares) {
      this.allowedShares = allowedShares;
    }

    public double getAllowedShares() {
      return allowedShares;
    }

  }

  @Entity(discriminator = "not-traded-today")
  public static class NotTradedToday extends Warning {
  }

}
