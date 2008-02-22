package com.twolattes.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.twolattes.json.OuterClass.InnerClass;
import com.twolattes.json.types.URLType;

public class UnmarshallingTest {
  @Test
  public void testBaseTypeEntity() throws Exception {
    BaseTypeEntity base = unmarshall(BaseTypeEntity.class,
        "{_0:5,_1:'h',_2:89,_3:3.2,_4:16,_5:\"ya\",_6:true,_7:6218.687231}");

    assertEquals(5, base.get_0());
    assertEquals('h', base.get_1());
    assertEquals(89L, base.get_2());
    assertEquals(3.2f, base.get_3());
    assertEquals((short) 16, base.get_4());
    assertEquals("ya", base.get_5());
    assertTrue(base.is_6());
    assertEquals(6218.687231, base.get_7());
  }

  @Test
  public void testDoubleFloatBaseTypes1() throws Exception {
    DoubleFloatPojo p = unmarshall(DoubleFloatPojo.class,
        "{\"dval\":34,\"fval\":54}");

    assertEquals(54.0f, p.getFloatValue());
    assertEquals(34.0, p.getDoubleValue());
  }

  @Test
  public void testDoubleFloatBaseTypes2() throws Exception {
    DoubleFloatPojo p = unmarshall(DoubleFloatPojo.class,
        "{\"dval\":34.0,\"fval\":54.0}");

    assertEquals(54.0f, p.getFloatValue());
    assertEquals(34.0, p.getDoubleValue());
  }

  @Test
  public void testDoubleFloatBaseTypes3() throws Exception {
    DoubleFloatPojo p = unmarshall(DoubleFloatPojo.class,
        "{\"dval\":null,\"fval\":null}");

    assertNull(p.getFloatValue());
    assertNull(p.getDoubleValue());
  }

  @Test
  public void testCollectionEntity() throws Exception {
    CollectionEntity base = unmarshall(CollectionEntity.class,
        "{friends:['Simon',   'David', \"Michael\"]}");

    Set<String> friends = new HashSet<String>(3);
    friends.add("Simon");
    friends.add("David");
    friends.add("Michael");

    assertEquals(3, base.getBuddies().size());
    assertTrue(friends.containsAll(base.getBuddies()));
  }

  @Test
  public void testCollectionEntityWithNull() throws Exception {
    CollectionEntity base = unmarshall(CollectionEntity.class,
        "{friends:null}");

    assertEquals(null, base.getBuddies());
  }

  @Test
  public void testBaseTypeWithNull() throws Exception {
    Email email = unmarshall(Email.class, "{email: null}");

    assertEquals(null, email.email);
  }

  @Test
  public void testCollectionInCollection() throws Exception {
    CollectionInCollection base = unmarshall(CollectionInCollection.class,
        "{data: [[\"C\"], [\"A\", \"B\"], []]}");

    assertEquals(3, base.getData().size());
    assertTrue(base.getData().get(0).containsAll(Sets.immutableSet("C")));
    assertTrue(base.getData().get(1).containsAll(Sets.immutableSet("A", "B")));
    assertTrue(base.getData().get(2).isEmpty());
  }

  @Test
  public void testInlinedEntity() throws Exception {
    User user = unmarshall(User.class, "{email: \"jack@bauer.net\"}");

    assertEquals("jack@bauer.net", user.email.email);
  }

  @Test
  public void testInlinedEntityWithNull() throws Exception {
    User user = unmarshall(User.class, "{email: null}");

    assertNull(user.email);
  }
  
  @Test
  public void testInlinedEntityUsingInlineAnnotation1() throws Exception {
    UserInlinedEmail user =
        unmarshall(UserInlinedEmail.class, "{email: \"jack.bauer@ctu.gov\"}", "1");
    
    assertEquals("jack.bauer@ctu.gov", user.email.email);
  }
  
  @Test
  public void testInlinedEntityUsingInlineAnnotation1WithNull() throws Exception {
    UserInlinedEmail user =
        unmarshall(UserInlinedEmail.class, "{email: null}", "1");
    
    assertNull(user.email);
  }
  
  @Test
  public void testInlinedEntityUsingInlineAnnotation2() throws Exception {
    UserInlinedEmail user =
        unmarshall(UserInlinedEmail.class,
        "{emails: {foo:\"jack.bauer@ctu.gov\", bar:'not@me.com'}}", "2");

    assertEquals(2, user.emails.size());
    assertNotNull(user.emails.get("foo"));
    assertEquals("jack.bauer@ctu.gov", user.emails.get("foo").email);
    assertNotNull(user.emails.get("bar"));
    assertEquals("not@me.com", user.emails.get("bar").email);
  }
  
  @Test
  public void testInlinedEntityUsingInlineAnnotation2WithNull() throws Exception {
    UserInlinedEmail user =
        unmarshall(UserInlinedEmail.class, "{emails: null}", "2");

    assertNull(user.emails);
  }
  
  @Test
  public void testInlinedEntityUsingInlineAnnotation3() throws Exception {
    UserInlinedEmail user =
        unmarshall(UserInlinedEmail.class,
        "{emailNoInline: {email:\"jack.bauer@ctu.gov\"}}", "3");

    assertNotNull(user.emailNoInline);
    assertNotNull(user.emailNoInline.email);
    assertEquals("jack.bauer@ctu.gov", user.emailNoInline.email);
  }
  
  @Test
  public void testInlinedEntityUsingInlineAnnotation3WithNull1() throws Exception {
    UserInlinedEmail user =
        unmarshall(UserInlinedEmail.class,
        "{emailNoInline: null}", "3");

    assertNull(user.emailNoInline);
  }
  
  @Test
  public void testInlinedEntityUsingInlineAnnotation3WithNull2() throws Exception {
    UserInlinedEmail user =
        unmarshall(UserInlinedEmail.class,
        "{emailNoInline: {email: null}}", "3");

    assertNotNull(user.emailNoInline);
    assertNull(user.emailNoInline.email);
  }
  
  @Test
  public void testInlinedEntityUsingInlineAnnotation4() throws Exception {
    UserInlinedEmail user =
        unmarshall(UserInlinedEmail.class,
        "{emailsArray: [\"jack.bauer@ctu.gov\", \"tony@ctu.gov\"]}", "4");

    assertNotNull(user.emailsArray);
    assertEquals(2, user.emailsArray.length);
    assertNotNull(user.emailsArray[0]);
    assertEquals("jack.bauer@ctu.gov", user.emailsArray[0].email);
    assertNotNull(user.emailsArray[1]);
    assertEquals("tony@ctu.gov", user.emailsArray[1].email);
  }
  
  @Test
  public void testInlinedEntityUsingInlineAnnotation4WithNull1() throws Exception {
    UserInlinedEmail user =
        unmarshall(UserInlinedEmail.class,
        "{emailsArray: null}", "4");

    assertNull(user.emailsArray);
  }
  
  @Test
  public void testInlinedEntityUsingInlineAnnotation4WithNull2() throws Exception {
    UserInlinedEmail user =
        unmarshall(UserInlinedEmail.class,
        "{emailsArray: [null]}", "4");

    assertNotNull(user.emailsArray);
    assertEquals(1, user.emailsArray.length);
    assertNull(user.emailsArray[0]);
  }
  
  @Test
  public void testInlinedEntityUsingInlineAnnotation5() throws Exception {
    UserInlinedEmail user =
        unmarshall(UserInlinedEmail.class,
        "{emailsList: [\"jack.bauer@ctu.gov\", \"tony@ctu.gov\"]}", "5");

    assertNotNull(user.emailsList);
    assertEquals(2, user.emailsList.size());
    assertNotNull(user.emailsList.get(0));
    assertEquals("jack.bauer@ctu.gov", user.emailsList.get(0).email);
    assertNotNull(user.emailsList.get(1));
    assertEquals("tony@ctu.gov", user.emailsList.get(1).email);
  }
  
  @Test
  public void testInlinedEntityUsingInlineAnnotation5WithNull1() throws Exception {
    UserInlinedEmail user =
        unmarshall(UserInlinedEmail.class,
        "{emailsList: null}", "5");

    assertNull(user.emailsList);
  }
  
  @Test
  public void testInlinedEntityUsingInlineAnnotation5WithNull2() throws Exception {
    UserInlinedEmail user =
        unmarshall(UserInlinedEmail.class,
        "{emailsList: [null]}", "5");

    assertNotNull(user.emailsList);
    assertEquals(1, user.emailsList.size());
    assertNull(user.emailsList.get(0));
  }
  
  @Test
  public void testInlinedEntityUsingInlineAnnotation6() throws Exception {
	List<EmailInline> emails =
        unmarshallList(EmailInline.class, "[\"jack.bauer@ctu.gov\"]");

    assertNotNull(emails);
    assertEquals(1, emails.size());
    assertNotNull(emails.get(0));
    assertEquals("jack.bauer@ctu.gov", emails.get(0).email);
  }

  @Test
  public void testOptionalValue() throws Exception {
    OptionalValue v = unmarshall(OptionalValue.class, "{}");

    assertEquals(new OptionalValue().getOptional(), v.getOptional());
  }

  @Test
  public void testCollectionOfEntities() throws Exception {
    List<User> users = unmarshallList(User.class,
        "[{email: \"jack@bauer.net\"}, {email: \"foo@bar.com\"}]");

    assertEquals(2, users.size());
    assertEquals("jack@bauer.net", users.get(0).email.email);
    assertEquals("foo@bar.com", users.get(1).email.email);
  }

  @Test
  public void testMap() throws Exception {

    EntityMap base = unmarshall(EntityMap.class,
        "{emails: {a: {email: 'plperez@stanford.edu'}, b: {email: 'nonono@yesyesyes.com'}}}");

    assertEquals(2, base.numberOfEmails());
    assertEquals("plperez@stanford.edu", base.get("a").email);
    assertEquals("nonono@yesyesyes.com", base.get("b").email);
  }

  @Test
  public void testMapWithNull() throws Exception {
    EntityMap base = unmarshall(EntityMap.class, "{emails: null}");

    assertEquals(null, base.getEmails());
  }

  @Test
  public void testUserType() throws Exception {
    EntityWithURL e =
      unmarshall(EntityWithURL.class, "{url:\"http://code.twolattes.com\"}");

    assertEquals(new URL("http://code.twolattes.com"), e.getUrl());
  }

  @Test
  public void testUserTypeWithRegistration() throws Exception {
    Marshaller.register(new URLType());
    EntityWithURLNotDeclared e =
      unmarshall(EntityWithURLNotDeclared.class, "{url:\"http://code.twolattes.com\"}");

    assertEquals(new URL("http://code.twolattes.com"), e.getUrl());
  }

  @Test
  public void testUserTypeWithNull() throws Exception {
    EntityWithURL e =
      unmarshall(EntityWithURL.class, "{url:null}");

    assertEquals(null, e.getUrl());
  }

  @Test
  public void testArray() throws Exception {
    ArrayEntity e =
      unmarshall(ArrayEntity.class, "{values:[\"hello\", \"world\"]}");

    assertNotNull(e.values);
    assertEquals(2, e.values.length);
    assertEquals("hello", e.values[0]);
    assertEquals("world", e.values[1]);
  }

  @Test
  public void testArrayWithNull() throws Exception {
    ArrayEntity e =
      unmarshall(ArrayEntity.class, "{values:null}");

    assertNull(e.values);
  }

  @Test
  public void testArrayOfList() throws Exception {
    ArrayOfList e = unmarshall(ArrayOfList.class, "{weird:[[5.0,6.0,0.9],[2.3],[12.45,78.0]]}");

    assertNotNull(e.weird);
    assertEquals(3, e.weird.length);
    assertEquals(Lists.immutableList(5.0, 6.0, 0.9), e.weird[0]);
    assertEquals(Lists.immutableList(2.3), e.weird[1]);
    assertEquals(Lists.immutableList(12.45,78.0), e.weird[2]);
  }

  @Test
  public void testViews() throws Exception {
    MultipleViewEntity fullView =
        unmarshall(MultipleViewEntity.class,
        "{email:'a',motto:'b',normal:'c',name:\"d\"}", "full");

    assertEquals("a", fullView.getEmail());
    assertEquals("b", fullView.getMotto());
    assertEquals("c", fullView.getNormal());
    assertEquals("d", fullView.getName());

    MultipleViewEntity simpleView =
        unmarshall(MultipleViewEntity.class,
        "{user:'b',normal:'c',name:\"d\"}", "simple");

    assertEquals("b", simpleView.getUser());
    assertEquals("c", simpleView.getNormal());
    assertEquals("d", simpleView.getName());

    MultipleViewEntity view =
        unmarshall(MultipleViewEntity.class,
        "{email:'a',user:'b',motto:'d',normal:'c',name:\"e\"}", "simple");

    assertEquals("b", view.getUser());
    assertEquals("c", view.getNormal());
    assertEquals("e", view.getName());
    assertEquals("a", view.getEmail());
    assertEquals("d", view.getMotto());
  }
  
  @Test
  public void testInnerClass() throws Exception {
    InnerClass e = unmarshall(InnerClass.class, "{field:'hello world!'}");
    
    assertEquals("hello world!", e.field);
  }
  
  @Test
  public void testGetterSetter1() throws Exception {
    GetterSetterEntity e = unmarshall(GetterSetterEntity.class, "{name:'foo bar'}");
    
    assertEquals("foo bar", e.getName());
  }
  
  @Test
  public void testGetterSetter2() throws Exception {
    EntityInterface e = unmarshall(EntityInterface.class, "{whatever:true}");
    
    assertTrue(e.isWhatever());
  }
  
  @Test
  public void testDoublyInlined1() throws Exception {
	  DoublyInlined e = unmarshall(DoublyInlined.class, "{foo:'hello there'}");
    
    assertNotNull(e.foo);
    assertNotNull(e.foo.bar);
    assertEquals("hello there", e.foo.bar.hello);
  }
  
  @Test
  public void testDoublyInlined2() throws Exception {
	  DoublyInlined e = unmarshall(DoublyInlined.class, "{foo:null}");
    
    assertNull(e.foo);
  }
  
  @Test
  public void testPrivateNoArgConstructor() throws Exception {
    PrivateNoArgConstructor e =
        unmarshall(PrivateNoArgConstructor.class, "{foo:'hi'}");
    
    assertNotNull(e);
    assertEquals("hi", e.getFoo());
  }

  @Test
  @Ignore("not yet implemented")
  public void testCyclicStructure() throws Exception {
    Node n = unmarshall(Node.class, "{neighbors:[{id:0}],id:0}", true);

    assertEquals(Sets.immutableSet(n), n.neighbors());
  }

  private <T> T unmarshall(Class<T> clazz, String json) throws JSONException {
    return unmarshall(clazz, json, false, null);
  }

  private <T> T unmarshall(Class<T> clazz, String json, boolean cyclic) throws JSONException {
    return unmarshall(clazz, json, cyclic, null);
  }

  private <T> T unmarshall(Class<T> clazz, String json, String view) throws JSONException {
    return unmarshall(clazz, json, false, view);
  }

  private <T> T unmarshall(Class<T> clazz, String json, boolean cyclic, String view) throws JSONException {
    Marshaller<T> marshaller = Marshaller.create(clazz);
    return marshaller.unmarshall(new JSONObject(json), view);
  }

  private <T> List<T> unmarshallList(Class<T> clazz, String json) throws JSONException {
    Marshaller<T> marshaller = Marshaller.create(clazz);
    return marshaller.unmarshallList(new JSONArray(json));
  }
}
