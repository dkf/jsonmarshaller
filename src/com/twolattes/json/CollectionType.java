package com.twolattes.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public enum CollectionType {
  SET {
    @Override
    <T> Collection<T> newCollection() {
      return new HashSet<T>();
    }

    @Override
    @SuppressWarnings("unchecked")
    Class<? extends Collection> toClass() {
      return Set.class;
    }
  },

  SORTED_SET {
    @Override
    <T> Collection<T> newCollection() {
      return new TreeSet<T>();
    }

    @Override
    @SuppressWarnings("unchecked")
    Class<? extends Collection> toClass() {
      return SortedSet.class;
    }
  },

  LIST {
    @Override
    <T> Collection<T> newCollection() {
      return new ArrayList<T>();
    }

    @Override
    @SuppressWarnings("unchecked")
    Class<? extends Collection> toClass() {
      return List.class;
    }
  };

  abstract <T> Collection<T> newCollection();

  @SuppressWarnings("unchecked")
  static CollectionType fromClass(Class<? extends Collection> klass) {
    if (SortedSet.class.isAssignableFrom(klass)) {
      return SORTED_SET;
    } else if (Set.class.isAssignableFrom(klass)) {
      return SET;
    } else {
      return LIST;
    }
  }

  @SuppressWarnings("unchecked")
  abstract Class<? extends Collection> toClass();
}
