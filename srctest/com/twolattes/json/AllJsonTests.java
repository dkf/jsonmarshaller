package com.twolattes.json;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.twolattes.json.collection.CollectionTest;
import com.twolattes.json.enumimpl.EnumTest;
import com.twolattes.json.gettersetter.GetterSetterTest;
import com.twolattes.json.inheritance1.Inheritance1Test;
import com.twolattes.json.inheritance2.Inheritance2Test;
import com.twolattes.json.inheritance3.Inheritance3Test;
import com.twolattes.json.inheritanceerror.InheritanceErrorTest;
import com.twolattes.json.nativetypes.BigDecimalTest;
import com.twolattes.json.nativetypes.LiteralsTest;
import com.twolattes.json.types.TypesTest;
import com.twolattes.json.types.URLTypeTest;
import com.twolattes.json.views.ViewsTest;
import com.twolattes.json.visibility1.Visibility1Test;
import com.twolattes.json.visibility2.Visibility2Test;

@RunWith(value = Suite.class)
@SuiteClasses(value = {
  DescriptorsEqualityTest.class,
  DescriptorFactoryTest.class,
  MarshallerTest.class,
  MarshallingTest.class,
  UnmarshallingTest.class,
  EntityClassVisitorTest.class,
  TypesTest.class,
  URLTypeTest.class,
  Inheritance1Test.class,
  Inheritance2Test.class,
  Inheritance3Test.class,
  InheritanceErrorTest.class,
  Visibility1Test.class,
  Visibility2Test.class,
  GetterSetterTest.class,
  ViewsTest.class,
  CollectionTest.class,
  CollectionTypeTest.class,
  MapTypeTest.class,
  EnumTest.class,
  LiteralsTest.class,
  BigDecimalTest.class,

  // Json
  JsonTest.class,
  OrgJsonAssert.class
})
public class AllJsonTests {
}
