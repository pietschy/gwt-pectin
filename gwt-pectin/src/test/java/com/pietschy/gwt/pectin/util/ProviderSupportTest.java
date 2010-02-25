package com.pietschy.gwt.pectin.util;


import com.pietschy.gwt.pectin.client.bean.NotCollectionPropertyException;
import com.pietschy.gwt.pectin.client.bean.TargetBeanIsNullException;
import com.pietschy.gwt.pectin.client.bean.UnknownPropertyException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import static org.testng.Assert.*;

/**
 * ReflectionBeanModelProvider Tester.
 *
 * @author andrew
 * @version $Revision$, $Date$
 * @created January 19, 2010
 * @since 1.0
 */
public class ProviderSupportTest
{
   private ProviderSupport<TestBean> provider;
   private TestBean bean;

   @BeforeMethod
   public void setUp()
   {
      provider = new ProviderSupport<TestBean>(TestBean.class);
      bean = new TestBean();
   }

   @Test
   public void readValue()
   {
      assertNull(provider.readProperty(bean, "string"));
      String name = "abc";
      bean.setString(name);
      assertEquals(provider.readProperty(bean, "string"), name);
   }

   @Test
   public void writeValue()
   {
      String name = "abc";
      provider.writeProperty(bean, "string", name);
      assertEquals(bean.getString(), name);
   }

   @Test(expectedExceptions = TargetBeanIsNullException.class)
   public void writeValueToNullBean()
   {
      provider.writeProperty(null, "string", "abc");
   }

   @Test
   public void readValueWithUnknownProperty()
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
   public void writeValueWithUnknownProperty()
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




//   @Test
//   public void testDirtyModel()
//   {
//      TestBean bean = new TestBean();
//      bean.setString("abc");
//      bean.setPrimativeInt(5);
//      provider.setBean(bean);
//
//      assertFalse(provider.getDirtyModel().getValue());
//
//      BeanPropertyValueModel<TestBean, String> vm1 = provider.getValueModel("string", String.class);
//      BeanPropertyValueModel<TestBean, Integer> vm2 = provider.getValueModel("primativeInt", Integer.class);
//
//      vm1.setValue("def");
//      assertTrue(provider.getDirtyModel().getValue());
//
//      vm2.setValue(42);
//      assertTrue(provider.getDirtyModel().getValue());
//
//      vm1.setValue("abc");
//      assertTrue(provider.getDirtyModel().getValue());
//
//      vm2.setValue(5);
//      assertFalse(provider.getDirtyModel().getValue());
//
//      vm1.setValue("def");
//      assertTrue(provider.getDirtyModel().getValue());
//      vm2.setValue(42);
//      assertTrue(provider.getDirtyModel().getValue());
//
//      provider.commit();
//      assertFalse(provider.getDirtyModel().getValue());
//   }

}

