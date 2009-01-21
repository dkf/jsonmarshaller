package com.twolattes.json;

import static com.twolattes.json.MapType.MAP;
import static com.twolattes.json.MapType.SORTED_MAP;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.Test;

public class MapTypeTest {

  @Test
  public void fromClass() throws Exception {
    assertEquals(MAP, MapType.fromClass(Map.class));
    assertEquals(MAP, MapType.fromClass(HashMap.class));

    assertEquals(SORTED_MAP, MapType.fromClass(SortedMap.class));
    assertEquals(SORTED_MAP, MapType.fromClass(TreeMap.class));
  }

  @Test
  public void map() throws Exception {
    assertEquals(Map.class, MAP.toClass());
    assertEquals(HashMap.class, MAP.newMap().getClass());
  }

  @Test
  public void sortedMap() throws Exception {
    assertEquals(SortedMap.class, SORTED_MAP.toClass());
    assertEquals(TreeMap.class, SORTED_MAP.newMap().getClass());
  }

}
