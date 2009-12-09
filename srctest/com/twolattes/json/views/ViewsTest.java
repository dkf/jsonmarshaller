package com.twolattes.json.views;

import static com.twolattes.json.Json.number;
import static com.twolattes.json.Json.string;
import static com.twolattes.json.TwoLattes.createEntityMarshaller;
import static com.twolattes.json.TwoLattes.createMarshaller;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.twolattes.json.EntityMarshaller;
import com.twolattes.json.Json;
import com.twolattes.json.MultipleViewEntity;
import com.twolattes.json.UserInlinedEmail;

public class ViewsTest {

  @Test
  public void MultipleViewEntityMarshalling1() throws Exception {
    EntityMarshaller<MultipleViewEntity> m =
      createEntityMarshaller(MultipleViewEntity.class);

    MultipleViewEntity view = new MultipleViewEntity();
    view.setEmail("hello@email.com");
    view.setName("Pascal");
    view.setUser("plperez");
    view.setMotto("nice sentence");
    view.setNormal("everywhere");

    Json.Object simpleView = m.marshall(view, "simple");

    assertEquals(3, simpleView.size());
    assertEquals(string("Pascal"), simpleView.get(string("name")));
    assertEquals(string("plperez"), simpleView.get(string("user")));
    assertEquals(string("everywhere"), simpleView.get(string("normal")));

    Json.Object fullView = m.marshall(view, "full");

    assertEquals(4, fullView.size());
    assertEquals(string("Pascal"), fullView.get(string("name")));
    assertEquals(string("hello@email.com"), fullView.get(string("email")));
    assertEquals(string("nice sentence"), fullView.get(string("motto")));
    assertEquals(string("everywhere"), simpleView.get(string("normal")));
  }

  @Test
  public void MultipleViewEntityMarshalling2() throws Exception {
    EntityMarshaller<MultipleViewEntity> m =
      createEntityMarshaller(MultipleViewEntity.class);

    MultipleViewEntity view = new MultipleViewEntity();
    view.setEmail("hello@email.com");
    view.setName("Pascal");
    view.setUser("plperez");
    view.setMotto("nice sentence");
    view.setNormal("everywhere");

    Json.Object simpleView = m.marshall(view);

    assertEquals(1, simpleView.size());
    assertEquals(string("everywhere"), simpleView.get(string("normal")));
  }

  @Test
  public void MultipleViewEntityUnmarshalling() throws Exception {
    MultipleViewEntity fullView = unmarshall(MultipleViewEntity.class,
        "{\"email\":\"a\",\"motto\":\"b\",\"normal\":\"c\",\"name\":\"d\"}", "full");

    assertEquals("a", fullView.getEmail());
    assertEquals("b", fullView.getMotto());
    assertEquals("c", fullView.getNormal());
    assertEquals("d", fullView.getName());

    MultipleViewEntity simpleView = unmarshall(MultipleViewEntity.class,
        "{\"user\":\"b\",\"normal\":\"c\",\"name\":\"d\"}", "simple");

    assertEquals("b", simpleView.getUser());
    assertEquals("c", simpleView.getNormal());
    assertEquals("d", simpleView.getName());

    MultipleViewEntity view = unmarshall(MultipleViewEntity.class,
        "{\"email\":\"a\",\"user\":\"b\",\"motto\":\"d\",\"normal\":\"c\",\"name\":\"e\"}", "simple");

    assertEquals("b", view.getUser());
    assertEquals("c", view.getNormal());
    assertEquals("e", view.getName());
    assertEquals(null, view.getEmail());
    assertEquals(null, view.getMotto());
  }

  @Test
  public void testNormalOrFullMarshalling1() throws Exception {
    EntityMarshaller<NormalOrFull> m = createEntityMarshaller(NormalOrFull.class);

    Json.Object o = m.marshall(new NormalOrFull());
    assertEquals(1, o.size());
    assertEquals(number(89), o.get(string("foo")));
  }

  @Test
  public void testNormalOrFullMarshalling2() throws Exception {
    EntityMarshaller<NormalOrFull> m = createEntityMarshaller(NormalOrFull.class);

    Json.Object o = m.marshall(new NormalOrFull(), "full");
    assertEquals(2, o.size());
    assertEquals(number(89), o.get(string("foo")));
    assertEquals(number(78), o.get(string("bar")));
  }

  @Test
  public void testNormalOrFullUnmarshalling1() throws Exception {
    NormalOrFull normal = unmarshall(NormalOrFull.class, "{\"foo\":41}", null);

    assertNotNull(normal);
    assertEquals(41, normal.foo);
    assertEquals(78, normal.bar);
  }

  @Test
  public void testNormalOrFullUnmarshalling2() throws Exception {
    NormalOrFull normal = unmarshall(NormalOrFull.class,
        "{\"foo\":41,\"bar\":102}", null);

    assertNotNull(normal);
    assertEquals(41, normal.foo);
    // 102 should be discarded as it is not part of the view
    assertEquals(78, normal.bar);
  }

  @Test
  public void testNormalOrFullUnmarshalling3() throws Exception {
    NormalOrFull normal = unmarshall(NormalOrFull.class,
        "{\"foo\":41,\"bar\":102}", "full");

    assertNotNull(normal);
    assertEquals(41, normal.foo);
    assertEquals(102, normal.bar);
  }

  @Test
  public void testInlinedEntityUsingInlineAnnotation1() throws Exception {
    UserInlinedEmail user =
        unmarshall(UserInlinedEmail.class,
            "{\"email\": \"jack.bauer@ctu.gov\"}", "1");

    assertEquals("jack.bauer@ctu.gov", user.email.email);
  }

  @Test
  public void testInlinedEntityUsingInlineAnnotation1WithNull() throws Exception {
    UserInlinedEmail user = unmarshall(UserInlinedEmail.class,
        "{\"email\": null}", "1");

    assertNull(user.email);
  }

  @Test
  public void testInlinedEntityUsingInlineAnnotation2() throws Exception {
    UserInlinedEmail user = unmarshall(UserInlinedEmail.class,
        "{\"emails\": {\"foo\":\"jack.bauer@ctu.gov\", \"bar\":\"not@me.com\"}}", "2");

    assertEquals(2, user.emails.size());
    assertNotNull(user.emails.get("foo"));
    assertEquals("jack.bauer@ctu.gov", user.emails.get("foo").email);
    assertNotNull(user.emails.get("bar"));
    assertEquals("not@me.com", user.emails.get("bar").email);
  }

  @Test
  public void testInlinedEntityUsingInlineAnnotation2WithNull() throws Exception {
    UserInlinedEmail user = unmarshall(UserInlinedEmail.class,
        "{\"emails\": null}", "2");

    assertNull(user.emails);
  }

  @Test
  public void testInlinedEntityUsingInlineAnnotation3() throws Exception {
    UserInlinedEmail user = unmarshall(UserInlinedEmail.class,
        "{\"emailNoInline\": {\"email\":\"jack.bauer@ctu.gov\"}}", "3");

    assertNotNull(user.emailNoInline);
    assertNotNull(user.emailNoInline.email);
    assertEquals("jack.bauer@ctu.gov", user.emailNoInline.email);
  }

  @Test
  public void testInlinedEntityUsingInlineAnnotation3WithNull1() throws Exception {
    UserInlinedEmail user = unmarshall(UserInlinedEmail.class,
        "{\"emailNoInline\": null}", "3");

    assertNull(user.emailNoInline);
  }

  @Test
  public void testInlinedEntityUsingInlineAnnotation3WithNull2() throws Exception {
    UserInlinedEmail user = unmarshall(UserInlinedEmail.class,
        "{\"emailNoInline\": {\"email\": null}}", "3");

    assertNotNull(user.emailNoInline);
    assertNull(user.emailNoInline.email);
  }

  @Test
  public void testInlinedEntityUsingInlineAnnotation4() throws Exception {
    UserInlinedEmail user = unmarshall(UserInlinedEmail.class,
        "{\"emailsArray\": [\"jack.bauer@ctu.gov\", \"tony@ctu.gov\"]}", "4");

    assertNotNull(user.emailsArray);
    assertEquals(2, user.emailsArray.length);
    assertNotNull(user.emailsArray[0]);
    assertEquals("jack.bauer@ctu.gov", user.emailsArray[0].email);
    assertNotNull(user.emailsArray[1]);
    assertEquals("tony@ctu.gov", user.emailsArray[1].email);
  }

  @Test
  public void testInlinedEntityUsingInlineAnnotation4WithNull1() throws Exception {
    UserInlinedEmail user = unmarshall(UserInlinedEmail.class,
        "{\"emailsArray\": null}", "4");

    assertNull(user.emailsArray);
  }

  @Test
  public void testInlinedEntityUsingInlineAnnotation4WithNull2() throws Exception {
    UserInlinedEmail user = unmarshall(UserInlinedEmail.class,
        "{\"emailsArray\": [null]}", "4");

    assertNotNull(user.emailsArray);
    assertEquals(1, user.emailsArray.length);
    assertNull(user.emailsArray[0]);
  }

  @Test
  public void testInlinedEntityUsingInlineAnnotation5() throws Exception {
    UserInlinedEmail user = unmarshall(UserInlinedEmail.class,
        "{\"emailsList\": [\"jack.bauer@ctu.gov\", \"tony@ctu.gov\"]}", "5");

    assertNotNull(user.emailsList);
    assertEquals(2, user.emailsList.size());
    assertNotNull(user.emailsList.get(0));
    assertEquals("jack.bauer@ctu.gov", user.emailsList.get(0).email);
    assertNotNull(user.emailsList.get(1));
    assertEquals("tony@ctu.gov", user.emailsList.get(1).email);
  }

  @Test
  public void testInlinedEntityUsingInlineAnnotation5WithNull1() throws Exception {
    UserInlinedEmail user = unmarshall(UserInlinedEmail.class,
        "{\"emailsList\": null}", "5");

    assertNull(user.emailsList);
  }

  @Test
  public void testInlinedEntityUsingInlineAnnotation5WithNull2() throws Exception {
    UserInlinedEmail user = unmarshall(UserInlinedEmail.class,
        "{\"emailsList\": [null]}", "5");

    assertNotNull(user.emailsList);
    assertEquals(1, user.emailsList.size());
    assertNull(user.emailsList.get(0));
  }

  @Test
  public void setOfNormalOrFullMarshallingEmptyNormal() throws Exception {
    Json.Object o = createEntityMarshaller(SetOfNormalOrFull.class)
        .marshall(new SetOfNormalOrFull());

    assertEquals(1, o.size());
    Json.Array a = (Json.Array) o.get(string("normalOrFulls"));
    assertEquals(0, a.size());
  }

  @Test
  public void setOfNormalOrFullMarshallingEmptyFull() throws Exception {
    Json.Object o = createEntityMarshaller(SetOfNormalOrFull.class)
        .marshall(new SetOfNormalOrFull(), "full");

    assertEquals(1, o.size());
    Json.Array a = (Json.Array) o.get(string("normalOrFulls"));
    assertEquals(0, a.size());
  }

  @Test
  public void setOfNormalOrFullMarshallingNotEmptyNormal() throws Exception {
    SetOfNormalOrFull setOfNormalOrFull = new SetOfNormalOrFull();
    setOfNormalOrFull.normalOrFulls.add(new NormalOrFull());
    Json.Object o1 = createEntityMarshaller(SetOfNormalOrFull.class)
        .marshall(setOfNormalOrFull);

    assertEquals(1, o1.size());
    Json.Array a = (Json.Array) o1.get(string("normalOrFulls"));
    assertEquals(1, a.size());
    Json.Object o2 = (Json.Object) a.get(0);

    assertEquals(1, o2.size());
    assertEquals(number(89), o2.get(string("foo")));
  }

  @Test
  public void setOfNormalOrFullMarshallingNotEmptyFull() throws Exception {
    SetOfNormalOrFull setOfNormalOrFull = new SetOfNormalOrFull();
    setOfNormalOrFull.normalOrFulls.add(new NormalOrFull());
    Json.Object o1 = createEntityMarshaller(SetOfNormalOrFull.class)
        .marshall(setOfNormalOrFull, "full");

    assertEquals(1, o1.size());
    Json.Array a = (Json.Array) o1.get(string("normalOrFulls"));
    assertEquals(1, a.size());
    Json.Object o2 = (Json.Object) a.get(0);

    assertEquals(2, o2.size());
    assertEquals(number(89), o2.get(string("foo")));
    assertEquals(number(78), o2.get(string("bar")));
  }

  private <T> T unmarshall(Class<T> clazz, String json, String view) {
    return createMarshaller(clazz).unmarshall(Json.fromString(json), view);
  }

}
