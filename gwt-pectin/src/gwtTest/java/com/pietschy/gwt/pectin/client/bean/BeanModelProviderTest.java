package com.pietschy.gwt.pectin.client.bean;


import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.pietschy.gwt.pectin.client.bean.test.TestBean;
import com.pietschy.gwt.pectin.client.bean.test.TestBeanModelProvider;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @since <pre>09/23/2009</pre>
 * @version 1.0
 */
public class BeanModelProviderTest extends GWTTestCase
{
   private TestBeanModelProvider provider;

   @Override
   protected void gwtSetUp() throws Exception
   {
      provider = GWT.create(TestBeanModelProvider.class);
   }

   public String getModuleName()
   {
      return "com.pietschy.gwt.pectin.Pectin";
   }

   @Test
   public void testReadValue()
   {
      assertNull(provider.readValue("string"));
      TestBean bean = new TestBean();
      String name = "abc";
      bean.setString(name);
      provider.setBean(bean);
      assertEquals(provider.readValue("string"), name);
   }

   @Test
   public void testWriteValue()
   {
      TestBean bean = new TestBean();
      provider.setBean(bean);
      String name = "abc";
      provider.writeValue("string", name);
      assertEquals(bean.getString(), name);
   }

   @Test
   public void testReadValueWithUnknownProperty()
   {
      try
      {
         provider.readValue("blah");
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
         provider.writeValue("blah", "abc");
         fail("expected to throw UnknownPropertyException");
      }
      catch (UnknownPropertyException e)
      {
      }
   }

   @Test
   public void testGetPropertyType()
   {
      assertEquals(provider.getPropertyType("string"), String.class);
      assertEquals(provider.getPropertyType("primativeInt"), Integer.class);
      assertEquals(provider.getPropertyType("objectInteger"), Integer.class);
      assertEquals(provider.getPropertyType("primativeBoolean"), Boolean.class);
      assertEquals(provider.getPropertyType("objectBoolean"), Boolean.class);
      assertEquals(provider.getPropertyType("list"), List.class);
      assertEquals(provider.getPropertyType("set"), Set.class);
      assertEquals(provider.getPropertyType("sortedSet"), SortedSet.class);
      assertEquals(provider.getPropertyType("collection"), Collection.class);
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
   public void testDirtyModel()
   {
      TestBean bean = new TestBean();
      bean.setString("abc");
      bean.setPrimativeInt(5);
      provider.setBean(bean);

      assertFalse(provider.getDirtyModel().getValue());

      BeanPropertyValueModel<String> vm1 = provider.getValueModel("string", String.class);
      BeanPropertyValueModel<Integer> vm2 = provider.getValueModel("primativeInt", Integer.class);

      vm1.setValue("def");
      assertTrue(provider.getDirtyModel().getValue());

      vm2.setValue(42);
      assertTrue(provider.getDirtyModel().getValue());

      vm1.setValue("abc");
      assertTrue(provider.getDirtyModel().getValue());

      vm2.setValue(5);
      assertFalse(provider.getDirtyModel().getValue());

      vm1.setValue("def");
      assertTrue(provider.getDirtyModel().getValue());
      vm2.setValue(42);
      assertTrue(provider.getDirtyModel().getValue());

      provider.commit();
      assertFalse(provider.getDirtyModel().getValue());
   }

}
