package com.twolattes.json.collection;

import java.util.Map;
import java.util.SortedMap;

import com.twolattes.json.Entity;
import com.twolattes.json.Value;

@Entity
public class NormalAndSortedMaps {

  @Value Map<String, Double> map1;

  @Value SortedMap<String, Double> map2;

}
