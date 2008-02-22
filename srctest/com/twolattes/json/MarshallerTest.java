package com.twolattes.json;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MarshallerTest {
  @Test
  public void testInterning() throws Exception {
    for (int i = 0; i < 100; i ++) {
      new Thread() {
        @Override
        public void run() {
          for (int i = 0 ; i < 100; i++) {
            Marshaller<A> m1 = Marshaller.create(A.class);
            Marshaller<A> m2 = Marshaller.create(A.class);
            assertTrue(m1 != null);
            assertTrue(m1 == m2);
          }
        }
      }.start();
    }
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testBadEntityBecauseBadType() throws Exception {
    Marshaller.create(BadEntityBecauseBadType.class);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testCollidingValues() throws Exception {
    Marshaller.create(CollidingValues.class);
  }
}
