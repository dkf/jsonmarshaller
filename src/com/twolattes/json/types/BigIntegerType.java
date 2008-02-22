package com.twolattes.json.types;

import java.math.BigInteger;

public class BigIntegerType extends NullSafeType<BigInteger> {
  @Override
  protected Object nullSafeMarshall(BigInteger entity) {
    return entity.longValue();
  }

  @Override
  protected BigInteger nullSafeUnmarshall(Object object) {
    if (object instanceof Integer) {
      return BigInteger.valueOf((Integer) object);
    } else {
      return BigInteger.valueOf((Long) object);
    }
  }

  public Class<BigInteger> getReturnedClass() {
    return BigInteger.class;
  }
}
