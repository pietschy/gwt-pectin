package com.pietschy.gwt.pectin.client.bean;


import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.pietschy.gwt.pectin.client.bean.test.AnotherBean;
import com.pietschy.gwt.pectin.client.bean.test.BeanWithCollections;
import com.pietschy.gwt.pectin.client.bean.test.TestBean;
import com.pietschy.gwt.pectin.client.bean.test.TestProvider;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @since <pre>09/23/2009</pre>
 * @version 1.0
 */
public class BeanModelProviderGeneratedMethodsTest extends GWTTestCase
{
   private TestProvider provider;
   private TestBean bean;

   @Override
   protected void gwtSetUp() throws Exception
   {
      provider = GWT.create(TestProvider.class);
      bean = new TestBean();
   }

   public String getModuleName()
   {
      return "com.pietschy.gwt.pectin.PectinTest";
   }

   @Test
   public void testGetPropertyType()
   {
      assertEquals(provider.getPropertyType("object"), Object.class);
      assertEquals(provider.getPropertyType("string"), String.class);
      assertEquals(provider.getPropertyType("primitiveInt"), Integer.class);
      assertEquals(provider.getPropertyType("primitiveBoolean"), Boolean.class);
      assertEquals(provider.getPropertyType("objectInteger"), Integer.class);
      assertEquals(provider.getPropertyType("objectBoolean"), Boolean.class);
      assertEquals(provider.getPropertyType("readOnlyObject"), Object.class);
      assertEquals(provider.getPropertyType("readOnlyPrimitiveInt"), Integer.class);

      assertEquals(provider.getPropertyType("nestedBean"), AnotherBean.class);
      assertEquals(provider.getPropertyType("nestedBean.string"), String.class);
      assertEquals(provider.getPropertyType("nestedBean.stringList"), List.class);

      assertEquals(provider.getPropertyType("collections"), BeanWithCollections.class);
      assertEquals(provider.getPropertyType("collections.stringCollection"), Collection.class);
      assertEquals(provider.getPropertyType("collections.integerCollection"), Collection.class);
      assertEquals(provider.getPropertyType("collections.stringList"), List.class);
      assertEquals(provider.getPropertyType("collections.stringSortedSet"), SortedSet.class);
      assertEquals(provider.getPropertyType("collections.untypedCollection"), Collection.class);
      assertEquals(provider.getPropertyType("collections.readOnlyUntypedCollection"), Collection.class);
   }

   @Test
   public void testGetElementType()
   {
      assertEquals(provider.getElementType("collections.stringCollection"), String.class);
      assertEquals(provider.getElementType("collections.integerCollection"), Integer.class);
      assertEquals(provider.getElementType("collections.stringList"), String.class);
      assertEquals(provider.getElementType("collections.stringSortedSet"), String.class);
      assertEquals(provider.getElementType("collections.untypedCollection"), Object.class);
      assertEquals(provider.getElementType("collections.readOnlyUntypedCollection"), Object.class);
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
   public void testGetPropertyTypeWithUnknownNestedProperty()
   {
      try
      {
         provider.getPropertyType("nestedBean.blah");
         fail("expected to throw UnknownPropertyException");
      }
      catch (UnknownPropertyException e)
      {
      }
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
}