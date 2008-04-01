package com.twolattes.json;

import static com.twolattes.json.CollectionType.LIST;
import static com.twolattes.json.CollectionType.SET;
import static com.twolattes.json.CollectionType.SORTED_SET;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

public class CollectionTypeTest {

  @Test
  public void fromClassList() throws Exception {
    assertEquals(LIST, CollectionType.fromClass(Collection.class));
    assertEquals(LIST, CollectionType.fromClass(List.class));
    assertEquals(LIST, CollectionType.fromClass(ArrayList.class));
    assertEquals(LIST, CollectionType.fromClass(LinkedList.class));
  }

  @Test
  public void fromClassSet() throws Exception {
    assertEquals(SET, CollectionType.fromClass(Set.class));
    assertEquals(SET, CollectionType.fromClass(HashSet.class));
    assertEquals(SET, CollectionType.fromClass(LinkedHashSet.class));
  }

  @Test
  public void fromClassSortedSet() throws Exception {
    assertEquals(SORTED_SET, CollectionType.fromClass(SortedSet.class));
    assertEquals(SORTED_SET, CollectionType.fromClass(TreeSet.class));
  }

  @Test
  public void toClass() throws Exception {
    assertEquals(Set.class, SET.toClass());
    assertEquals(SortedSet.class, SORTED_SET.toClass());
    assertEquals(List.class, LIST.toClass());
  }

}
