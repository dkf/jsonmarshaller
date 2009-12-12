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

@Entity(discriminator = "order-status-error")
public class OrderStatusError extends OrderStatus {

  @Value
  private TheError error;

  /* JSON */ @SuppressWarnings("unused") private OrderStatusError() {}

  public OrderStatusError(TheError error) {
    this.error = error;
  }

  public TheError getError() {
    return error;
  }

  @Entity(discriminatorName = "type", subclasses = {
      OrderStatusError.UnknownSecurity.class,
      OrderStatusError.NoShares.class,
      OrderStatusError.NotEnoughCash.class,
      OrderStatusError.NotEnoughEquity.class,
      OrderStatusError.NotEnoughShares.class,
      OrderStatusError.NoVolume.class,
      OrderStatusError.OverMaxVolume.class,
      OrderStatusError.LimitedVolume.class,
      OrderStatusError.RealTimeFeedTimeout.class,
      OrderStatusError.PriceEqualOrLessThanCommission.class,
      OrderStatusError.LimitedOutstandingShares.class,
      OrderStatusError.LimitedShortInterest.class,
      OrderStatusError.LimitOrderOnly.class,
      OrderStatusError.NegativeCash.class,
      OrderStatusError.CouldNotConfirmOrder.class
  })
  public static class TheError {
  }

  @Entity(discriminator = "no-shares")
  public static class NoShares extends TheError {
  }

  @Entity(discriminator = "could-not-confirm-order")
  public static class CouldNotConfirmOrder extends TheError {
  }

  @Entity(discriminator = "unknown-security")
  public static class UnknownSecurity extends TheError {
  }

  @Entity(discriminator = "timeout")
  public static class RealTimeFeedTimeout extends TheError {
  }

  @Entity(discriminator = "price-equal-or-less-than-commission")
  public static class PriceEqualOrLessThanCommission extends TheError {
  }

  @Entity(discriminator = "negative-cash")
  public static class NegativeCash extends TheError {
  }

  @Entity(discriminator = "limit-order-only")
  public static class LimitOrderOnly extends TheError {
  }

  @Entity(discriminator = "not-enough-shares")
  public static class NotEnoughShares extends TheError {

    @Value
    private double allowedShares;

    /* JSON */ @SuppressWarnings("unused") private NotEnoughShares() {}

    public NotEnoughShares(double allowedShares) {
      this.allowedShares = allowedShares;
    }

    public double getAllowedShares() {
      return allowedShares;
    }

  }

  @Entity(discriminator = "not-enough-equity")
  public static class NotEnoughEquity extends TheError {

    @Value
    private double allowedShares;

    /* JSON */ @SuppressWarnings("unused") private NotEnoughEquity() {}

    public NotEnoughEquity(double allowedShares) {
      this.allowedShares = allowedShares;
    }

    public double getAllowedShares() {
      return allowedShares;
    }

  }

  @Entity(discriminator = "not-enough-cash")
  public static class NotEnoughCash extends TheError {

    @Value
    private double allowedShares;

    /* JSON */ @SuppressWarnings("unused") private NotEnoughCash() {}

    public NotEnoughCash(double allowedShares) {
      this.allowedShares = allowedShares;
    }

    public double getAllowedShares() {
      return allowedShares;
    }

  }

  @Entity(discriminator = "over-max-volume")
  public static class OverMaxVolume extends TheError {

    @Value
    private double allowedShares;

    /* JSON */ @SuppressWarnings("unused") private OverMaxVolume() {}

    public OverMaxVolume(double allowedShares) {
      this.allowedShares = allowedShares;
    }

    public double getAllowedShares() {
      return allowedShares;
    }

  }

  @Entity(discriminator = "limited-volume")
  public static class LimitedVolume extends TheError {

    @Value
    private double allowedShares;

    /* JSON */ @SuppressWarnings("unused") private LimitedVolume() {}

    public LimitedVolume(double allowedShares) {
      this.allowedShares = allowedShares;
    }

    public double getAllowedShares() {
      return allowedShares;
    }

  }


  @Entity(discriminator = "limited-outstanding-shares")
  public static class LimitedOutstandingShares extends TheError {

    @Value
    private double allowedShares;

    /* JSON */ @SuppressWarnings("unused") private LimitedOutstandingShares() {}

    public LimitedOutstandingShares(double allowedShares) {
      this.allowedShares = allowedShares;
    }

    public double getAllowedShares() {
      return allowedShares;
    }

  }

  @Entity(discriminator = "limited-short-interest")
  public static class LimitedShortInterest extends TheError {

    @Value
    private int allowedShares;

    /* JSON */ @SuppressWarnings("unused") private LimitedShortInterest() {}

    public LimitedShortInterest(int allowedShares) {
      this.allowedShares = allowedShares;
    }

    public double getAllowedShares() {
      return allowedShares;
    }

  }

  @Entity(discriminator = "no-volume")
  public static class NoVolume extends TheError {

    @Value
    private int allowedShares;

    /* JSON */ @SuppressWarnings("unused") private NoVolume() {}

    public NoVolume(int allowedShares) {
      this.allowedShares = allowedShares;
    }

    public double getAllowedShares() {
      return allowedShares;
    }

  }

}

