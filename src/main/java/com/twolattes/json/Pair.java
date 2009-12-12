package com.twolattes.json;

class Pair<S, T> {

  final S left;
  final T right;

  Pair(S left, T right) {
    this.left = left;
    this.right = right;
  }

  static <S, T> Pair<S, T> of(S left, T right) {
    return new Pair<S, T>(left, right);
  }

}
