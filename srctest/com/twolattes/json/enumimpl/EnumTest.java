package com.twolattes.json.enumimpl;

import static com.twolattes.json.Json.number;
import static com.twolattes.json.Json.string;
import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.twolattes.json.Json;
import com.twolattes.json.TwoLattes;

public class EnumTest {

  @Test
  public void hasEnum1Marshall() throws Exception {
    // entity
    HasEnum1 e = new HasEnum1();
    e.abc = Abc.B;

    // marshalling
    Json.Object o = TwoLattes.createMarshaller(HasEnum1.class).marshall(e);

    // assertions
    assertEquals(1, o.size());
    assertEquals(string(Abc.B.name()), o.get(string("abc")));
  }

  @Test
  public void hasEnum1Unmarshall() throws Exception {
    // unmarshalling
    HasEnum1 e = TwoLattes.createMarshaller(HasEnum1.class).unmarshall(
        (Json.Object) Json.fromString(format("{\"abc\":\"%s\"}", Abc.B.name())));

    // assertions
    assertNotNull(e);
    Abc abc = e.abc;
    assertNotNull(abc);
    assertEquals(Abc.B, abc);
  }

  @Test
  public void hasEnumOrdinalTrueMarshall() throws Exception {
    // entity
    HasEnum2 e = new HasEnum2();
    e.abc = Abc.B;
    // marshalling
    Json.Object o = TwoLattes.createMarshaller(HasEnum2.class).marshall(e);

    // assertions
    assertEquals(1, o.size());
    assertEquals(number(e.abc.ordinal()), o.get(string("abc")));
  }

  @Test
  public void hasEnumOrdinalTrueUnmarshall() throws Exception {
    // unmarshalling
    HasEnum2 e = TwoLattes.createMarshaller(HasEnum2.class).unmarshall(
        (Json.Object) Json.fromString(format("{\"abc\":%s}", Abc.B.ordinal())));

    // assertions
    assertNotNull(e);
    Abc abc = e.abc;
    assertNotNull(abc);
    assertEquals(Abc.B, abc);
  }

  @Test
  public void hasEnum3Marshall() throws Exception {
    // entity
    HasEnum3 e = new HasEnum3();
    e.abc = Abc.B;

    // marshalling
    Json.Object o = TwoLattes.createMarshaller(HasEnum3.class).marshall(e);

    // assertions
    assertEquals(1, o.size());
    assertEquals(string(Abc.B.name()), o.get(string("my_enum")));
  }

  @Test
  public void hasEnum3Unmarshall() throws Exception {
    // unmarshalling
    HasEnum3 e = TwoLattes.createMarshaller(HasEnum3.class)
        .unmarshall(
            (Json.Object) Json.fromString("{\"my_enum\":\"" + Abc.B.name()
                + "\"}"));

    // assertions
    assertNotNull(e);
    Abc abc = e.abc;
    assertNotNull(abc);
    assertEquals(Abc.B, abc);
  }

  @Test
  public void hasEnum1NullMarshall() throws Exception {
    // entity
    HasEnum1 e = new HasEnum1();
    e.abc = null;
    // marshalling
    Json.Object o = TwoLattes.createMarshaller(HasEnum1.class).marshall(e);

    // assertions
    assertEquals(1, o.size());
    assertEquals(Json.NULL, o.get(string("abc")));
  }

  @Test
  public void hasEnum1NullUnmarshall() throws Exception {
    // unmarshalling
    HasEnum1 e = TwoLattes.createMarshaller(HasEnum1.class).unmarshall(
        (Json.Object) Json.fromString("{\"abc\":null}"));

    // assertions
    assertNotNull(e);
    Abc abc = e.abc;
    assertNull(abc);
  }

  @Test
  public void hasEnumList1Marshall() throws Exception {
    // entity
    HasEnumList1 e = new HasEnumList1();
    List<Abc> list = new ArrayList<Abc>(3);
    list.add(Abc.A);
    list.add(Abc.B);
    list.add(Abc.C);
    e.abcs = list;

    // marshalling
    Json.Object o = TwoLattes.createMarshaller(HasEnumList1.class).marshall(e);

    // assertions
    assertEquals(1, o.size());
    Json.Array abcs = (Json.Array) o.get(string("abcs"));
    assertEquals(3, abcs.size());
    assertEquals(string(Abc.A.name()), abcs.get(0));
    assertEquals(string(Abc.B.name()), abcs.get(1));
    assertEquals(string(Abc.C.name()), abcs.get(2));
  }

  @Test
  public void hasEnumList1Unmarshall() throws Exception {
    // unmarshalling
    HasEnumList1 e = TwoLattes.createMarshaller(HasEnumList1.class).unmarshall(
        (Json.Object) Json.fromString("{\"abcs\":[\"A\",\"B\",\"C\"]}"));

    // assertions
    assertNotNull(e);
    List<Abc> list = e.abcs;
    assertNotNull(list);
    assertEquals(3, list.size());
    assertTrue(list.contains(Abc.A));
    assertTrue(list.contains(Abc.B));
    assertTrue(list.contains(Abc.C));
  }

  @Test
  public void hasEnumList2Marshall() throws Exception {
    // entity
    HasEnumList2 e = new HasEnumList2();
    List<Abc> list = new ArrayList<Abc>(3);
    list.add(Abc.A);
    list.add(Abc.B);
    list.add(Abc.C);
    e.abcs = list;

    // marshalling
    Json.Object o = TwoLattes.createMarshaller(HasEnumList2.class).marshall(e);

    // assertions
    assertEquals(1, o.size());
    Json.Array abcs = (Json.Array) o.get(string("abcs"));
    assertEquals(3, abcs.size());
    assertEquals(number(Abc.A.ordinal()), abcs.get(0));
    assertEquals(number(Abc.B.ordinal()), abcs.get(1));
    assertEquals(number(Abc.C.ordinal()), abcs.get(2));
  }

  @Test
  public void hasEnumList2Unmarshall() throws Exception {
    // unmarshalling
    HasEnumList2 e = TwoLattes.createMarshaller(HasEnumList2.class).unmarshall(
        (Json.Object) Json.fromString("{\"abcs\":[0,1,2]}"));

    // assertions
    assertNotNull(e);
    List<Abc> list = e.abcs;
    assertNotNull(list);
    assertEquals(3, list.size());
    assertTrue(list.contains(Abc.A));
    assertTrue(list.contains(Abc.B));
    assertTrue(list.contains(Abc.C));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstantUnmarshall() throws Exception {
    // unmarshalling
    TwoLattes.createMarshaller(HasEnum1.class).unmarshall(
        (Json.Object) Json.fromString("{\"abc\":\"INVALID_CONSTANT\"}"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidOrdinalUnmarshall() throws Exception {
    // unmarshalling
    TwoLattes.createMarshaller(HasEnum2.class).unmarshall(
        (Json.Object) Json.fromString("{\"abc\":-1}"));
  }

  @Test
  public void hasEnum4Marshall() throws Exception {
    // entity
    HasEnum4 e = new HasEnum4();
    e.abc = Abc.B;

    // marshalling
    Json.Object o = TwoLattes.createMarshaller(HasEnum4.class).marshall(e);

    // assertions
    assertEquals(1, o.size());
    assertEquals(string("myvalue"), o.get(string("abc")));
  }

  @Test
  public void hasEnum4Unmarshall() throws Exception {
    // unmarshalling
    HasEnum4 e = TwoLattes.createMarshaller(HasEnum4.class).unmarshall(
        (Json.Object) Json.fromString("{\"abc\":\"doesnotmatter\"}"));

    // assertions
    assertNotNull(e);
    Abc abc = e.abc;
    assertNotNull(abc);
    assertEquals(Abc.A, abc);
  }

}
