package com.twolattes.json;

/**
 * Created by IntelliJ IDEA.
 * User: drorik
 * Date: Feb 28, 2009
 * Time: 6:17:03 PM
 */
@Entity
 public class Foo {
   @Value(name = "bar")
   int foo;

   @Value int getFoo() { return foo; }

}


