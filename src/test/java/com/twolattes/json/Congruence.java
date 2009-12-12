package com.twolattes.json;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newArrayListWithExpectedSize;
import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

public class Congruence {

  public static void check(Collection<? extends Object>... congruenceClasses) {
    List<List<Object>> cc =
        newArrayListWithExpectedSize(congruenceClasses.length);

    // reflexivity
    for (Collection<? extends Object> congruenceClass : congruenceClasses) {
      List<Object> c = newArrayList();
      cc.add(c);
      for (Object element : congruenceClass) {
        assertTrue(format("reflexivity of %s", element),
            element.equals(element));
        c.add(element);
      }
    }

    // equality within congruence classes
    for (List<Object> c : cc) {
      for (int i = 0; i < c.size(); i++) {
        Object e1 = c.get(i);
        for (int j = i + 1; j < c.size(); j++) {
          Object e2 = c.get(j);
          assertTrue(format("%s=%s", e1, e2), e1.equals(e2));
          assertTrue(format("%s=%s", e2, e1), e2.equals(e1));
          assertEquals(format("hashCode %s vs. %s", e1, e2), e1.hashCode(), e2.hashCode());
        }
      }
    }

    // inequality across congruence classes
    for (int i = 0; i < cc.size(); i++) {
      List<Object> c1 = cc.get(i);
      for (int j = i + 1; j < cc.size(); j++) {
        List<Object> c2 = cc.get(j);
        for (Object e1 : c1) {
          for (Object e2 : c2) {
            assertFalse(format("%s!=%s", e1, e2), e1.equals(e2));
            assertFalse(format("%s!=%s", e2, e1), e2.equals(e1));
          }
        }
      }
    }
  }

}
