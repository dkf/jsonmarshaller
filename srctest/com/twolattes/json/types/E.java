package com.twolattes.json.types;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class E {
  @Value(type = URLType.class, views = "url")
  URL url;

  @Value(type = BigDecimalType.class, views = "bigDecimal")
  BigDecimal bigDecimal;

  @Value(type = BigIntegerType.class, views = "bigInteger")
  BigInteger bigInteger;
}
