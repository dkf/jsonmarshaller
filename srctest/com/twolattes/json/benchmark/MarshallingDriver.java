package com.twolattes.json.benchmark;

import com.google.common.base.Function;
import com.sun.japex.JapexDriverBase;
import com.sun.japex.TestCase;

abstract class MarshallingDriver extends JapexDriverBase {
  public static final String FLEXJSON_INCLUDES = "flexjsonIncludes";
  public static final String ENTITY_CLASS = "entityClass";

  private Object entity;
  private Function<Object, String> marshaller;

  @Override
  public final void prepare(TestCase testCase) {
    try {
      entity = newInstance(testCase.getParam(ENTITY_CLASS));
      marshaller = createMarshaller(testCase);
    } catch (InstantiationException e) {
      throw new IllegalStateException(e);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException(e);
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException(e);
    }
  }

  abstract Function<Object, String> createMarshaller(TestCase testCase);

  private Object newInstance(String klass)
  throws InstantiationException, IllegalAccessException,
  ClassNotFoundException {
    return MarshallingDriver.class.getClassLoader().loadClass(klass).newInstance();
  }

  @Override
  public final void warmup(TestCase testCase) {
    run(testCase);
  }

  @Override
  public final void run(TestCase testCase) {
    serialize();
  }

  final String serialize() {
    return marshaller.apply(entity);
  }

  @Override
  public final void finish(TestCase testCase) {
  }
}