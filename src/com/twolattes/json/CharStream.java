package com.twolattes.json;

import java.io.IOException;
import java.io.Reader;

class CharStream {

  private SimpleCharReader delegate;

  CharStream(final Reader reader) {
    this.delegate = new SimpleCharReader() {
      @Override
      int read() throws IOException {
        return reader.read();
      }
    };
  }

  int read() throws IOException {
    return delegate.read();
  }

  void unread(final int codePoint) {
    if (0 <= codePoint) {
      final SimpleCharReader readingDelegate = delegate;
      delegate = new SimpleCharReader() {
        @Override
        int read() throws IOException {
          try {
            return codePoint;
          } finally {
            delegate = readingDelegate;
          }
        }
      };
    }
  }

  private abstract static class SimpleCharReader {
    abstract int read() throws IOException;
  }

}
