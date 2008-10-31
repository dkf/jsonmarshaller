package com.twolattes.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.twolattes.json.OuterClass.InnerClass;


public class MarshallingTest {
  @Test
  public void testBaseTypeEntity() throws JSONException {
    Marshaller<BaseTypeEntity> marshaller = TwoLattes.createMarshaller(BaseTypeEntity.class);

    BaseTypeEntity base = new BaseTypeEntity.Factory().create(5, 'h', 89L, 3.2f, (short) 16, "ya", true, 6218.687231);

    JSONObject o = new JSONObject();
    o.put("_0", 5);
    o.put("_1", new Character('h'));
    o.put("_2", 89);
    o.put("_3", 3.2);
    o.put("_4", 16);
    o.put("_5", "ya");
    o.put("_6", true);
    o.put("_7", 6218.687231);

    assertEquals(o, marshaller.marshall(base));
  }

  @Test
  public void testNullInEntity() throws Exception {
    Email e = new Email();
    e.email = null;

    Marshaller<Email> m = TwoLattes.createMarshaller(Email.class);

    JSONObject o = m.marshall(e);

    assertEquals(JSONObject.NULL, o.get("email"));
  }

  @Test
  public void testCollectionEntity() throws JSONException {
    Marshaller<CollectionEntity> marshaller = TwoLattes.createMarshaller(CollectionEntity.class);

    List<String> friends = new ArrayList<String>();
    friends.add("Jack");
    friends.add("Monica");
    CollectionEntity base = new CollectionEntity.Factory().create(friends);
    JSONObject jsonObject = marshaller.marshall(base);
    JSONArray array = new JSONArray();
    array.put("Jack");
    array.put("Monica");

    assertEquals(array, jsonObject.get("friends"));
  }

  @Test
  public void testInlinedEntityUsingValueOption() throws JSONException {
    Marshaller<User> marshaller = TwoLattes.createMarshaller(User.class);

    Email e = new Email(); e.email = "plperez@stanford.edu";
    User u = new User(); u.email = e;

    JSONObject o = marshaller.marshall(u);
    assertEquals("plperez@stanford.edu", o.get("email"));
  }

  @Test
  public void testInlinedEntityUsingInlineAnnotation1() throws JSONException {
    Marshaller<UserInlinedEmail> marshaller = TwoLattes.createMarshaller(UserInlinedEmail.class);

    EmailInline e = new EmailInline(); e.email = "plperez@stanford.edu";
    UserInlinedEmail u = new UserInlinedEmail(); u.email = e;

    JSONObject o = marshaller.marshall(u, "1");
    assertEquals("plperez@stanford.edu", o.get("email"));
  }

  @Test
  public void testInlinedEntityUsingInlineAnnotation2() throws JSONException {
    Marshaller<UserInlinedEmail> marshaller = TwoLattes.createMarshaller(UserInlinedEmail.class);

    EmailInline e1 = new EmailInline(); e1.email = "plperez@stanford.edu";
    EmailInline e2 = new EmailInline(); e2.email = "pascal@cs.stanford.edu";
    UserInlinedEmail u = new UserInlinedEmail();
    u.emails.put("foo", e1);
    u.emails.put("bar", e2);

    JSONObject o1 = marshaller.marshall(u, "2");
    Object emails = o1.get("emails");
    assertTrue(emails instanceof JSONObject);
    JSONObject o2 = (JSONObject) emails;
    assertEquals("plperez@stanford.edu", o2.get("foo"));
    assertEquals("pascal@cs.stanford.edu", o2.get("bar"));
  }

  @Test
  public void testInlinedEntityUsingInlineAnnotation3() throws JSONException {
    Marshaller<UserInlinedEmail> marshaller = TwoLattes.createMarshaller(UserInlinedEmail.class);

    EmailInline e = new EmailInline(); e.email = "plperez@stanford.edu";
    UserInlinedEmail u = new UserInlinedEmail(); u.emailNoInline = e;

    JSONObject o = marshaller.marshall(u, "3");
    assertTrue(o.get("emailNoInline") instanceof JSONObject);
    JSONObject oe = (JSONObject) o.get("emailNoInline");
    assertEquals("plperez@stanford.edu", oe.get("email"));
  }

  @Test
  public void testInlinedEntityUsingInlineAnnotation4() throws JSONException {
    Marshaller<UserInlinedEmail> marshaller = TwoLattes.createMarshaller(UserInlinedEmail.class);

    EmailInline e = new EmailInline(); e.email = "plperez@stanford.edu";
    UserInlinedEmail u = new UserInlinedEmail(); u.emailsArray = new EmailInline[] { e };

    JSONObject o = marshaller.marshall(u, "4");
    assertTrue(o.get("emailsArray") instanceof JSONArray);
    JSONArray oe = (JSONArray) o.get("emailsArray");
    assertEquals("plperez@stanford.edu", oe.get(0));
  }

  @Test
  public void testInlinedEntityUsingInlineAnnotation5() throws JSONException {
    Marshaller<UserInlinedEmail> marshaller = TwoLattes.createMarshaller(UserInlinedEmail.class);

    EmailInline e = new EmailInline(); e.email = "plperez@stanford.edu";
    UserInlinedEmail u = new UserInlinedEmail(); u.emailsList.add(e);

    JSONObject o = marshaller.marshall(u, "5");
    assertTrue(o.get("emailsList") instanceof JSONArray);
    JSONArray oe = (JSONArray) o.get("emailsList");
    assertEquals("plperez@stanford.edu", oe.get(0));
  }

  @Test
  public void testInlinedEntityUsingInlineAnnotation6() throws JSONException {
    Marshaller<EmailInline> marshaller = TwoLattes.createMarshaller(EmailInline.class);

    EmailInline e = new EmailInline(); e.email = "plperez@stanford.edu";
    ArrayList<EmailInline> list = new ArrayList<EmailInline>();
    list.add(e);

    JSONArray a = marshaller.marshallList(list);
    assertEquals(1, a.length());
    assertEquals("plperez@stanford.edu", a.get(0));
  }

  @Test
  public void testListOfEntities() throws JSONException {
    Marshaller<User> marshaller = TwoLattes.createMarshaller(User.class);

    Email e1 = new Email(); e1.email = "plperez@stanford.edu";
    User u1 = new User(); u1.email = e1;

    Email e2 = new Email(); e2.email = "dalia_ma@hotmail.com";
    User u2 = new User(); u2.email = e2;
    List<User> users = new ArrayList<User>(2);
    users.add(u1); users.add(u2);

    JSONArray array = marshaller.marshallList(users);
    assertEquals("plperez@stanford.edu", array.getJSONObject(0).get("email"));
    assertEquals("dalia_ma@hotmail.com", array.getJSONObject(1).get("email"));
  }

  @Test
  public void testMapOfEntities() throws Exception {
    Marshaller<EntityMap> marshaller = TwoLattes.createMarshaller(EntityMap.class);

    EntityMap em = new EntityMap();
    Email e1 = new Email(); e1.email = "plperez@stanford.edu";
    em.addEmail("Jack", e1);

    JSONObject jsonEmails = new JSONObject();
    JSONObject jsonPlperez = new JSONObject();
    JSONObject jsonEm = new JSONObject();
    jsonPlperez.put("email", "plperez@stanford.edu");
    jsonEmails.put("Jack", jsonPlperez);
    jsonEm.put("emails", jsonEmails);

    assertEquals(jsonEm, marshaller.marshall(em));
  }

  @Test
  public void testNullArray() throws Exception {
    Marshaller<ArrayEntity> marshaller = TwoLattes.createMarshaller(ArrayEntity.class);

    JSONObject o = marshaller.marshall(new ArrayEntity());

    assertEquals(JSONArray.NULL, o.get("values"));
  }

  @Test
  public void testArray() throws Exception {
    Marshaller<ArrayEntity> marshaller = TwoLattes.createMarshaller(ArrayEntity.class);

    ArrayEntity arrayEntity = new ArrayEntity();
    arrayEntity.values = new String[] {"ya", "yo", "yi"};
    JSONObject o = marshaller.marshall(arrayEntity);

    assertTrue(o.get("values") instanceof JSONArray);
    assertEquals(3, o.getJSONArray("values").length());
    assertEquals("ya", o.getJSONArray("values").get(0));
    assertEquals("yo", o.getJSONArray("values").get(1));
    assertEquals("yi", o.getJSONArray("values").get(2));
  }

  @Test(expected = StackOverflowError.class)
  public void testCyclicStructureWarn() throws Exception {
    Node n = new Node();
    n.addNeighbor(n);

    Marshaller<Node> marshaller = TwoLattes.createMarshaller(Node.class);

    marshaller.marshall(n);
  }

  @Test
  public void testUserType() throws Exception {
    Marshaller<EntityWithURL> m = TwoLattes.createMarshaller(EntityWithURL.class);

    EntityWithURL entity = new EntityWithURL();
    entity.setUrl(new URL("http://www.twolattes.com"));

    assertEquals("http://www.twolattes.com", m.marshall(entity).get("url"));
  }

  @Test
  public void testInnerClass() throws Exception {
    Marshaller<InnerClass> m =  TwoLattes.createMarshaller(OuterClass.InnerClass.class);

    InnerClass e = new InnerClass();
    e.field = "hello";

    JSONObject o = m.marshall(e);
    assertEquals(1, o.length());
    assertEquals("hello", o.getString("field"));
  }

  @Test
  public void testGetterSetter1() throws Exception {
    Marshaller<GetterSetterEntity> m = TwoLattes.createMarshaller(GetterSetterEntity.class);

    GetterSetterEntity e = new GetterSetterEntity();
    e.setName("Jack");

    JSONObject o = m.marshall(e);
    assertEquals(1, o.length());
    assertEquals("Jack", o.get("name"));
  }

  @Test
  public void testGetterSetter2() throws Exception {
    Marshaller<EntityInterface> m = TwoLattes.createMarshaller(EntityInterface.class);

    EntityInterface e = new EntityInterfaceImpl();
    e.setWhatever(false);

    JSONObject o = m.marshall(e);
    assertEquals(1, o.length());
    assertEquals(false, o.get("whatever"));
  }

  @Test
  public void testDoublyInlined1() throws Exception {
  	Marshaller<DoublyInlined> m = TwoLattes.createMarshaller(DoublyInlined.class);

  	// entity
  	DoublyInlined entity = new DoublyInlined();
  	DoublyInlined.Bar bar = new DoublyInlined.Bar();
  	bar.hello = "hello";
  	DoublyInlined.Foo foo = new DoublyInlined.Foo();
  	foo.bar = bar;
  	entity.foo = foo;

  	JSONObject o = m.marshall(entity);
  	assertEquals(1, o.length());
  	assertEquals("hello", o.getString("foo"));
  }

  @Test
  public void testDoublyInlined2() throws Exception {
  	Marshaller<DoublyInlined> m = TwoLattes.createMarshaller(DoublyInlined.class);

  	// entity
  	DoublyInlined entity = new DoublyInlined();
  	DoublyInlined.Foo foo = new DoublyInlined.Foo();
  	foo.bar = null;
  	entity.foo = foo;

  	JSONObject o = m.marshall(entity);
  	assertEquals(1, o.length());
  	assertEquals(JSONObject.NULL, o.get("foo"));
  }

  @Test
  public void testDoublyInlined3() throws Exception {
  	Marshaller<DoublyInlined> m = TwoLattes.createMarshaller(DoublyInlined.class);

  	// entity
  	DoublyInlined entity = new DoublyInlined();
  	entity.foo = null;

  	JSONObject o = m.marshall(entity);
  	assertEquals(1, o.length());
  	assertEquals(JSONObject.NULL, o.get("foo"));
  }

  @Test
  public void testPrivateNoArgConstructor() throws Exception {
    Marshaller<PrivateNoArgConstructor> m =
        TwoLattes.createMarshaller(PrivateNoArgConstructor.class);
    PrivateNoArgConstructor e = new PrivateNoArgConstructor("hi");
    JSONObject o = m.marshall(e);

    assertEquals(1, o.length());
    assertEquals("hi", o.get("foo"));
  }

  @Test
  public void testSameEntityShouldNotBeConsideredACyclycity() throws Exception {
  	UserWithTwoInlinedEmail user = new UserWithTwoInlinedEmail();
  	Email email = new Email();
  	email.email = "somewhere@bug.com";

  	// two values point to the same entity
  	user.email1 = email;
  	user.email2 = email;

  	JSONObject o = TwoLattes.createMarshaller(UserWithTwoInlinedEmail.class).marshall(user);

  	assertTrue(o.get("email1") instanceof JSONObject);
  	assertTrue(o.get("email2") instanceof JSONObject);
  }

  @Test
  public void testTypeOnGetter() throws Exception {
  	TypeOnGetter e = new TypeOnGetter();

  	JSONObject o = TwoLattes.createMarshaller(TypeOnGetter.class).marshall(e);

  	assertEquals(1, o.length());
  	assertEquals(10.0, o.get("price"));
  }

  @Test
  public void nativeArray() throws Exception {
    Marshaller<EntityWithNativeArray> m = TwoLattes.createMarshaller(EntityWithNativeArray.class);

    EntityWithNativeArray e = new EntityWithNativeArray();
    e.ids = new int[] { 5, 1, 2 };

    JSONObject o = m.marshall(e);

    assertEquals(1, o.length());
    JSONArray ids = o.getJSONArray("ids");
    assertEquals(3, ids.length());
    assertEquals(5, ids.get(0));
    assertEquals(1, ids.get(1));
    assertEquals(2, ids.get(2));
  }

  @Test
  public void arrayOfArray() throws Exception {
    ArrayOfArray arrayOfArray = new ArrayOfArray();
    arrayOfArray.matrix = new Integer[][] {{56, 57}, {58, 59}};

    JSONObject o = TwoLattes.createMarshaller(ArrayOfArray.class).marshall(arrayOfArray);

    assertEquals(1, o.length());
    JSONArray matrix = o.getJSONArray("matrix");
    assertEquals(2, matrix.length());
    assertEquals(2, matrix.getJSONArray(0).length());
    assertEquals(56, matrix.getJSONArray(0).get(0));
    assertEquals(57, matrix.getJSONArray(0).get(1));
    assertEquals(2, matrix.getJSONArray(1).length());
    assertEquals(58, matrix.getJSONArray(1).get(0));
    assertEquals(59, matrix.getJSONArray(1).get(1));
  }

  @Test
  public void nullOptionalObject() throws Exception {
    NullOptionalValue obj = new NullOptionalValue();

    JSONObject o = TwoLattes.createMarshaller(NullOptionalValue.class).marshall(obj);
    assertEquals(0, o.length());
  }

  @Test
  public void nonNullOptionalObject() throws Exception {
    NullOptionalValue obj = new NullOptionalValue();
    obj.setOptional("optional");

    JSONObject o = TwoLattes.createMarshaller(NullOptionalValue.class).marshall(obj);
    assertEquals(1, o.length());
  }

}
