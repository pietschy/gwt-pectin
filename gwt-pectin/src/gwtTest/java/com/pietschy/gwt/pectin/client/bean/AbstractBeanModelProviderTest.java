package com.pietschy.gwt.pectin.client.bean;


import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.pietschy.gwt.pectin.client.bean.test.TestAbstractBeanModelProvider;
import com.pietschy.gwt.pectin.client.bean.test.TestBean;
import org.junit.Test;

import java.util.*;

import static com.pietschy.gwt.pectin.client.bean.test.AssertUtil.assertContentEquals;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @since <pre>09/23/2009</pre>
 * @version 1.0
 */
public class AbstractBeanModelProviderTest extends GWTTestCase
{
   private TestAbstractBeanModelProvider provider;
   private TestBean bean;

   @Override
   protected void gwtSetUp() throws Exception
   {
      provider = GWT.create(TestAbstractBeanModelProvider.class);
      bean = new TestBean();
   }

   public String getModuleName()
   {
      return "com.pietschy.gwt.pectin.PectinTest";
   }

   @Test
   public void testReadValue()
   {
      assertNull(provider.readProperty(null, "string"));
      String name = "abc";
      bean.setString(name);
      assertEquals(provider.readProperty(bean, "string"), name);
   }

   @Test
   public void testWriteValue()
   {
      String name = "abc";
      provider.writeProperty(bean, "string", name);
      assertEquals(bean.getString(), name);
   }

   @Test
   public void testWriteValueWithNullBean()
   {
      String name = "abc";
      try
      {
         provider.writeProperty(null, "string", name);
         fail("expected TargetBeanIsNullException");
      }
      catch (TargetBeanIsNullException e)
      {
         // this should happen
      }
   }

   @Test
   public void testWriteValueToImmutable()
   {
      try
      {
         provider.writeProperty(bean, "readOnlyObject", "blah");
         fail("expected ImmutablePropertyException");
      }
      catch (ImmutablePropertyException e)
      {

      }
   }

   @Test
   public void testReadValueWithUnknownProperty()
   {
      try
      {
         provider.readProperty(bean, "blah");
         fail("expected to throw UnknownPropertyException");
      }
      catch (UnknownPropertyException e)
      {
      }
   }

   @Test
   public void testWriteValueWithUnknownProperty()
   {
      try
      {
         provider.writeProperty(bean, "blah", "abc");
         fail("expected to throw UnknownPropertyException");
      }
      catch (UnknownPropertyException e)
      {
      }
   }

   @Test
   public void testGetPropertyType()
   {
      assertEquals(provider.getPropertyType("object"), Object.class);
      assertEquals(provider.getPropertyType("string"), String.class);
      assertEquals(provider.getPropertyType("primitiveInt"), Integer.class);
      assertEquals(provider.getPropertyType("objectInteger"), Integer.class);
      assertEquals(provider.getPropertyType("primitiveBoolean"), Boolean.class);
      assertEquals(provider.getPropertyType("objectBoolean"), Boolean.class);

      assertEquals(provider.getPropertyType("list"), List.class);

      assertEquals(provider.getPropertyType("set"), Set.class);
      assertEquals(provider.getPropertyType("sortedSet"), SortedSet.class);
      assertEquals(provider.getPropertyType("collection"), Collection.class);
      assertEquals(provider.getPropertyType("untypedCollection"), Collection.class);

      assertEquals(provider.getPropertyType("readOnlyPrimitive"), Integer.class);
      assertEquals(provider.getPropertyType("readOnlyObject"), Object.class);
      assertEquals(provider.getPropertyType("readOnlyUntypedCollection"), Collection.class);
      assertEquals(provider.getPropertyType("readOnlyCollection"), Collection.class);
   }

   @Test
   public void testGetPropertyTypeWithUnknownProperty()
   {
      try
      {
         provider.getPropertyType("blah");
         fail("expected to throw UnknownPropertyException");
      }
      catch (UnknownPropertyException e)
      {
      }
   }

   @Test
   public void testIsMutable()
   {
      assertTrue(provider.isMutable("object"));
      assertTrue(provider.isMutable("string"));
      assertTrue(provider.isMutable("primitiveInt"));
      assertTrue(provider.isMutable("objectInteger"));
      assertTrue(provider.isMutable("primitiveBoolean"));
      assertTrue(provider.isMutable("objectBoolean"));

      assertTrue(provider.isMutable("list"));

      assertTrue(provider.isMutable("set"));
      assertTrue(provider.isMutable("sortedSet"));
      assertTrue(provider.isMutable("collection"));
      assertTrue(provider.isMutable("untypedCollection"));

      assertFalse(provider.isMutable("readOnlyPrimitive"));
      assertFalse(provider.isMutable("readOnlyObject"));
      assertFalse(provider.isMutable("readOnlyUntypedCollection"));
      assertFalse(provider.isMutable("readOnlyCollection"));
   }



   @Test
   public void testGetElementType()
   {
      assertEquals(provider.getElementType("list"), String.class);
      assertEquals(provider.getElementType("set"), String.class);
      assertEquals(provider.getElementType("sortedSet"), String.class);
      assertEquals(provider.getElementType("untypedCollection"), Object.class);
      assertEquals(provider.getElementType("readOnlyCollection"), String.class);
      assertEquals(provider.getElementType("readOnlyUntypedCollection"), Object.class);
   }

   @Test
   public void testGetElementTypeForNonCollection()
   {
      try
      {
         provider.getElementType("object");
         fail("expected to throw NotCollectionPropertyException");
      }
      catch (NotCollectionPropertyException e)
      {
      }
   }


   @Test
   public void testReadFrom()
   {
      TestBean bean = new TestBean();
      bean.setString("abc");
      bean.setPrimitiveInt(5);
      bean.setList(Arrays.asList("abc", "def", "ghi"));

      BeanPropertyValueModel<TestBean, String> stringValue = provider.getValueModel("string", String.class);
      BeanPropertyValueModel<TestBean, Integer> intValue = provider.getValueModel("primitiveInt", Integer.class);
      BeanPropertyListModel<TestBean, String> stringList = provider.getListModel("list", String.class);

      assertEquals(stringValue.getValue(), null);
      assertEquals(intValue.getValue(), null);
      assertEquals(stringList.size(), 0);

      provider.readFrom(bean);

      assertEquals(stringValue.getValue(), bean.getString());
      assertEquals(intValue.getValue(), (Integer) bean.getPrimitiveInt());
      assertContentEquals(stringList.asUnmodifiableList(), bean.getList());

      assertFalse(stringValue.getDirtyModel().getValue());
      assertFalse(intValue.getDirtyModel().getValue());
      assertFalse(stringList.getDirtyModel().getValue());

      // now change our values so we're dirty, but read from another bean.
      stringValue.setValue("elephant");
      intValue.setValue(100);
      stringList.setElements(Arrays.asList("abc","xyz"));

      assertTrue(stringValue.getDirtyModel().getValue());
      assertTrue(intValue.getDirtyModel().getValue());
      assertTrue(stringList.getDirtyModel().getValue());

      // now read from another bean.
      TestBean bean2 = new TestBean();
      bean2.setString("allGood");
      bean2.setPrimitiveInt(42);
      bean2.setList(Arrays.asList("xyz", "blah"));

      provider.readFrom(bean2);

      assertEquals(stringValue.getValue(), bean2.getString());
      assertEquals(intValue.getValue(), (Integer) bean2.getPrimitiveInt());
      assertContentEquals(stringList.asUnmodifiableList(), bean2.getList());

      assertFalse(stringValue.getDirtyModel().getValue());
      assertFalse(intValue.getDirtyModel().getValue());
      assertFalse(stringList.getDirtyModel().getValue());

   }

   @Test
   public void testResetDirtyState()
   {
      TestBean bean = new TestBean();
      bean.setString("abc");
      bean.setPrimitiveInt(5);
      bean.setList(Arrays.asList("abc", "def", "ghi"));


      BeanPropertyValueModel<TestBean, String> stringValue = provider.getValueModel("string", String.class);
      BeanPropertyValueModel<TestBean, Integer> intValue = provider.getValueModel("primitiveInt", Integer.class);
      BeanPropertyListModel<TestBean, String> stringList = provider.getListModel("list", String.class);

      provider.readFrom(bean);

      assertEquals(stringValue.getValue(), bean.getString());
      assertEquals(intValue.getValue(), (Integer) bean.getPrimitiveInt());
      assertContentEquals(stringList.asUnmodifiableList(), bean.getList());

      assertFalse(stringValue.getDirtyModel().getValue());
      assertFalse(intValue.getDirtyModel().getValue());
      assertFalse(stringList.getDirtyModel().getValue());

      // now change our values so we're dirty, but read from another bean.
      stringValue.setValue("elephant");
      intValue.setValue(100);
      stringList.setElements(Arrays.asList("abc","xyz"));

      assertTrue(stringValue.getDirtyModel().getValue());
      assertTrue(intValue.getDirtyModel().getValue());
      assertTrue(stringList.getDirtyModel().getValue());

      provider.checkpoint();

      // the values should be the same
      assertEquals(stringValue.getValue(), "elephant");
      assertEquals(intValue.getValue(), (Integer) 100);
      assertContentEquals(stringList.asUnmodifiableList(), Arrays.asList("abc","xyz"));

      // but we're no longer dirty
      assertFalse(stringValue.getDirtyModel().getValue());
      assertFalse(intValue.getDirtyModel().getValue());
      assertFalse(stringList.getDirtyModel().getValue());
   }



   @Test
   public void testCopyToBeanWithoutResettingDirtyState()
   {
      TestBean bean = new TestBean();

      bean.setString("abc");
      bean.setPrimitiveInt(5);
      bean.setList(Arrays.asList("abc", "def", "ghi"));


      BeanPropertyValueModel<TestBean, String> stringValue = provider.getValueModel("string", String.class);
      BeanPropertyValueModel<TestBean, Integer> intValue = provider.getValueModel("primitiveInt", Integer.class);
      BeanPropertyListModel<TestBean, String> stringList = provider.getListModel("list", String.class);

      provider.readFrom(bean);

      assertEquals(stringValue.getValue(), "abc");
      assertEquals(intValue.getValue(), new Integer(5));
      assertContentEquals(stringList.asUnmodifiableList(), "abc", "def", "ghi");

      // now change some values and copy to a bean
      stringValue.setValue("blah");
      intValue.setValue(42);
      stringList.add("jkl");

      assertTrue(stringValue.getDirtyModel().getValue());
      assertTrue(stringValue.getDirtyModel().getValue());
      assertTrue(stringValue.getDirtyModel().getValue());

      // now lets copy to the bean without resetting our dirty state.
      provider.copyTo(bean, false);

      // teh bean should have the new values
      assertEquals(bean.getString(), "blah");
      assertEquals(bean.getPrimitiveInt(), 42);
      assertContentEquals(stringList.asUnmodifiableList(), "abc", "def", "ghi", "jkl");

      // but we're still dirty.
      assertTrue(stringValue.getDirtyModel().getValue());
      assertTrue(stringValue.getDirtyModel().getValue());
      assertTrue(stringValue.getDirtyModel().getValue());
   }

   @Test
   public void testCopyToBeanAndResettingDirtyState()
   {
      TestBean bean = new TestBean();

      bean.setString("abc");
      bean.setPrimitiveInt(5);
      bean.setList(Arrays.asList("abc", "def", "ghi"));


      BeanPropertyValueModel<TestBean, String> stringValue = provider.getValueModel("string", String.class);
      BeanPropertyValueModel<TestBean, Integer> intValue = provider.getValueModel("primitiveInt", Integer.class);
      BeanPropertyListModel<TestBean, String> stringList = provider.getListModel("list", String.class);

      provider.readFrom(bean);

      assertEquals(stringValue.getValue(), "abc");
      assertEquals(intValue.getValue(), new Integer(5));
      assertContentEquals(stringList.asUnmodifiableList(), "abc", "def", "ghi");

      // now change some values and copy to a bean
      stringValue.setValue("blah");
      intValue.setValue(42);
      stringList.add("jkl");

      // and make sure we're now dirty
      assertTrue(stringValue.getDirtyModel().getValue());
      assertTrue(stringValue.getDirtyModel().getValue());
      assertTrue(stringValue.getDirtyModel().getValue());

      // now lets copy to the bean without resetting our dirty state.
      provider.copyTo(bean, true);

      // the bean should have the new values
      assertEquals(bean.getString(), "blah");
      assertEquals(bean.getPrimitiveInt(), 42);
      assertContentEquals(stringList.asUnmodifiableList(), "abc", "def", "ghi", "jkl");

      // but we're still dirty.
      assertFalse(stringValue.getDirtyModel().getValue());
      assertFalse(stringValue.getDirtyModel().getValue());
      assertFalse(stringValue.getDirtyModel().getValue());
   }

   @Test
   public void testWritingNullToPrimitiveProperties()
   {
      
   }

}