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
 * @version 1.0
 * @since <pre>09/23/2009</pre>
 */
public class BeanModelProviderPropertyDescriptorTest extends GWTTestCase
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
   public void testGetPropertyDescriptorForUnknownPath()
   {
      try
      {
         provider.getPropertyDescriptor("blah");
         fail("expected to throw UnknownPropertyException");
      }
      catch (UnknownPropertyException e)
      {
      }
   }

   @Test
   public void testGetPropertyDescriptorForUnknownNestedPath()
   {
      // this should work so we guarantee our nestedBeanProperty exists
      provider.getPropertyDescriptor("nestedBean");
      try
      {
         provider.getPropertyDescriptor("nestedBean.blah");
         fail("expected to throw UnknownPropertyException");
      }
      catch (UnknownPropertyException e)
      {
      }
   }

   @Test
   public void testIsMutable()
   {
      doMutableTest("object", true);
      doMutableTest("string", true);
      doMutableTest("primitiveInt", true);
      doMutableTest("primitiveBoolean", true);
      doMutableTest("objectInteger", true);
      doMutableTest("objectBoolean", true);
      doMutableTest("readOnlyObject", false);
      doMutableTest("readOnlyPrimitiveInt", false);

      doMutableTest("nestedBean", true);
      doMutableTest("nestedBean.string", true);
      doMutableTest("nestedBean.stringList", true);

      doMutableTest("collections", true);
      doMutableTest("collections.stringCollection", true);
      doMutableTest("collections.integerCollection", true);
      doMutableTest("collections.stringList", true);
      doMutableTest("collections.stringSortedSet", true);
      doMutableTest("collections.untypedCollection", true);
      doMutableTest("collections.readOnlyUntypedCollection", false);
   }

   private void doMutableTest(String propertyPath, boolean mutable)
   {
      PropertyDescriptor descriptor = provider.getPropertyDescriptor(propertyPath);
      assertEquals(descriptor.isMutable(), mutable);
   }


   @Test
   public void testReadValue()
   {
      String name = "abc";
      bean.setString(name);
      PropertyDescriptor descriptor = provider.getPropertyDescriptor("string");
      assertEquals(descriptor.readProperty(bean), name);
   }

   @Test
   public void testReadNestedValue()
   {
      String name = "abc";
      bean.setNestedBean(new AnotherBean());
      bean.getNestedBean().setString(name);

      PropertyDescriptor descriptor = provider.getPropertyDescriptor("nestedBean.string");
      assertEquals(descriptor.readProperty(bean.getNestedBean()), name);
   }


   @Test
   public void testWriteValue()
   {
      String name = "abc";
      PropertyDescriptor descriptor = provider.getPropertyDescriptor("string");
      descriptor.writeProperty(bean, name);
      assertEquals(bean.getString(), name);
   }

   @Test
   public void testWriteNestedValue()
   {
      String name = "abc";
      bean.setNestedBean(new AnotherBean());
      PropertyDescriptor descriptor = provider.getPropertyDescriptor("nestedBean.string");
      descriptor.writeProperty(bean.getNestedBean(), name);
      assertEquals(bean.getNestedBean().getString(), name);
   }

   @Test
   public void testWriteValueWithNullBean()
   {
      String name = "abc";
      try
      {
         PropertyDescriptor descriptor = provider.getPropertyDescriptor("string");
         descriptor.writeProperty(null, name);
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
         PropertyDescriptor key = provider.getPropertyDescriptor("readOnlyObject");
         key.writeProperty(bean, "blah");
         fail("expected ImmutablePropertyException");
      }
      catch (ReadOnlyPropertyException e)
      {

      }
   }

   @Test
   public void testGetValueType()
   {
      assertEquals(provider.getPropertyDescriptor("object").getValueType(), Object.class);
      assertEquals(provider.getPropertyDescriptor("string").getValueType(), String.class);
      assertEquals(provider.getPropertyDescriptor("primitiveInt").getValueType(), Integer.class);
      assertEquals(provider.getPropertyDescriptor("primitiveBoolean").getValueType(), Boolean.class);
      assertEquals(provider.getPropertyDescriptor("objectInteger").getValueType(), Integer.class);
      assertEquals(provider.getPropertyDescriptor("objectBoolean").getValueType(), Boolean.class);
      assertEquals(provider.getPropertyDescriptor("readOnlyObject").getValueType(), Object.class);
      assertEquals(provider.getPropertyDescriptor("readOnlyPrimitiveInt").getValueType(), Integer.class);

      assertEquals(provider.getPropertyDescriptor("nestedBean").getValueType(), AnotherBean.class);
      assertEquals(provider.getPropertyDescriptor("nestedBean.string").getValueType(), String.class);
      assertEquals(provider.getPropertyDescriptor("nestedBean.stringList").getValueType(), List.class);

      assertEquals(provider.getPropertyDescriptor("collections").getValueType(), BeanWithCollections.class);
      assertEquals(provider.getPropertyDescriptor("collections.stringCollection").getValueType(), Collection.class);
      assertEquals(provider.getPropertyDescriptor("collections.integerCollection").getValueType(), Collection.class);
      assertEquals(provider.getPropertyDescriptor("collections.stringList").getValueType(), List.class);
      assertEquals(provider.getPropertyDescriptor("collections.stringSortedSet").getValueType(), SortedSet.class);
      assertEquals(provider.getPropertyDescriptor("collections.untypedCollection").getValueType(), Collection.class);
      assertEquals(provider.getPropertyDescriptor("collections.readOnlyUntypedCollection").getValueType(), Collection.class);
   }

   @Test
   public void testGetElementType()
   {
      assertEquals(provider.getPropertyDescriptor("collections.stringCollection").getElementType(), String.class);
      assertEquals(provider.getPropertyDescriptor("collections.integerCollection").getElementType(), Integer.class);
      assertEquals(provider.getPropertyDescriptor("collections.stringList").getElementType(), String.class);
      assertEquals(provider.getPropertyDescriptor("collections.stringSortedSet").getElementType(), String.class);
      assertEquals(provider.getPropertyDescriptor("collections.untypedCollection").getElementType(), Object.class);
      assertEquals(provider.getPropertyDescriptor("collections.readOnlyUntypedCollection").getElementType(), Object.class);
   }

   @Test
   public void testGetDescriptorWithUnknownProperty()
   {
      try
      {
         provider.getPropertyDescriptor("blah");
         fail("expected to throw UnknownPropertyException");
      }
      catch (UnknownPropertyException e)
      {
      }
   }

   @Test
   public void testGetDescriptorWithUnknownNestedProperty()
   {
      try
      {
         provider.getPropertyDescriptor("nestedBean.blah");
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
         provider.getPropertyDescriptor("object").getElementType();
         fail("expected to throw NotCollectionPropertyException");
      }
      catch (NotCollectionPropertyException e)
      {
      }
   }


}