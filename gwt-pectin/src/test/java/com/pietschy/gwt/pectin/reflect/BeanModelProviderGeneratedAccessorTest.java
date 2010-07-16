package com.pietschy.gwt.pectin.reflect;


import com.pietschy.gwt.pectin.client.bean.*;
import com.pietschy.gwt.pectin.reflect.test.AnotherBean;
import com.pietschy.gwt.pectin.reflect.test.BeanWithCollections;
import com.pietschy.gwt.pectin.reflect.test.TestBean;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.fail;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>09/23/2009</pre>
 */
public class BeanModelProviderGeneratedAccessorTest
{
   private AbstractBeanModelProvider<TestBean> provider;
   private TestBean bean;

   @BeforeMethod
   protected void setup() throws Exception
   {
      provider = new ReflectionBeanModelProvider<TestBean>(TestBean.class); 
      bean = new TestBean();
   }

   @Test
   public void testGetBeanAccessorForPropertyPathUnknownProperty()
   {
      try
      {
         provider.createPropertyDescriptor("blah");
         fail("expected to throw UnknownPropertyException");
      }
      catch (UnknownPropertyException e)
      {
      }
   }

   @Test
   public void testGetBeanAccessorForPropertyPathUnknownNestedProperty()
   {
      // this should work so we guarantee our nestedBeanProperty exists
      provider.createPropertyDescriptor("nestedBean");
      try
      {
         provider.createPropertyDescriptor("nestedBean.blah");
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
      PropertyDescriptor descriptor = provider.createPropertyDescriptor(propertyPath);
      assertEquals(descriptor.isMutable(), mutable);
   }


   @Test
   public void testReadValue()
   {
      String name = "abc";
      bean.setString(name);
      PropertyDescriptor propertyDescriptor = provider.createPropertyDescriptor("string");
      assertEquals(propertyDescriptor.readProperty(bean), name);
   }

   @Test
   public void testReadNestedValue()
   {
      String name = "abc";
      bean.setNestedBean(new AnotherBean());
      bean.getNestedBean().setString(name);

      PropertyDescriptor descriptor = provider.createPropertyDescriptor("nestedBean.string");
      assertEquals(descriptor.readProperty(bean.getNestedBean()), name);
   }


   @Test
   public void testWriteValue()
   {
      String name = "abc";
      provider.createPropertyDescriptor("string").writeProperty(bean, name);
      assertEquals(bean.getString(), name);
   }

   @Test
   public void testWriteNestedValue()
   {
      String name = "abc";
      bean.setNestedBean(new AnotherBean());
      provider.createPropertyDescriptor("nestedBean.string").writeProperty(bean.getNestedBean(), name);
      assertEquals(bean.getNestedBean().getString(), name);
   }

   @Test
   public void testWriteValueWithNullBean()
   {
      String name = "abc";
      try
      {
         provider.createPropertyDescriptor("string").writeProperty(null, name);
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
         provider.createPropertyDescriptor("readOnlyObject").writeProperty(bean, "blah");
         fail("expected ReadOnlyPropertyException");
      }
      catch (ReadOnlyPropertyException e)
      {

      }
   }

   @org.junit.Test
   public void testPropertyDescriptorGetValueType()
   {
      Assert.assertEquals(provider.createPropertyDescriptor("object").getValueType(), Object.class);
      Assert.assertEquals(provider.createPropertyDescriptor("string").getValueType(), String.class);
      Assert.assertEquals(provider.createPropertyDescriptor("primitiveInt").getValueType(), Integer.class);
      Assert.assertEquals(provider.createPropertyDescriptor("primitiveBoolean").getValueType(), Boolean.class);
      Assert.assertEquals(provider.createPropertyDescriptor("objectInteger").getValueType(), Integer.class);
      Assert.assertEquals(provider.createPropertyDescriptor("objectBoolean").getValueType(), Boolean.class);
      Assert.assertEquals(provider.createPropertyDescriptor("readOnlyObject").getValueType(), Object.class);
      Assert.assertEquals(provider.createPropertyDescriptor("readOnlyPrimitiveInt").getValueType(), Integer.class);

      Assert.assertEquals(provider.createPropertyDescriptor("nestedBean").getValueType(), AnotherBean.class);
      Assert.assertEquals(provider.createPropertyDescriptor("nestedBean.string").getValueType(), String.class);
      Assert.assertEquals(provider.createPropertyDescriptor("nestedBean.stringList").getValueType(), List.class);

      Assert.assertEquals(provider.createPropertyDescriptor("collections").getValueType(), BeanWithCollections.class);
      Assert.assertEquals(provider.createPropertyDescriptor("collections.stringCollection").getValueType(), Collection.class);
      Assert.assertEquals(provider.createPropertyDescriptor("collections.integerCollection").getValueType(), Collection.class);
      Assert.assertEquals(provider.createPropertyDescriptor("collections.stringList").getValueType(), List.class);
      Assert.assertEquals(provider.createPropertyDescriptor("collections.stringSortedSet").getValueType(), SortedSet.class);
      Assert.assertEquals(provider.createPropertyDescriptor("collections.untypedCollection").getValueType(), Collection.class);
      Assert.assertEquals(provider.createPropertyDescriptor("collections.readOnlyUntypedCollection").getValueType(), Collection.class);
   }

   @Test
   public void testPropertyDescriptorGetElementType()
   {
      Assert.assertEquals(provider.createPropertyDescriptor("collections.stringCollection").getElementType(), String.class);
      Assert.assertEquals(provider.createPropertyDescriptor("collections.integerCollection").getElementType(), Integer.class);
      Assert.assertEquals(provider.createPropertyDescriptor("collections.stringList").getElementType(), String.class);
      Assert.assertEquals(provider.createPropertyDescriptor("collections.stringSortedSet").getElementType(), String.class);
      Assert.assertEquals(provider.createPropertyDescriptor("collections.untypedCollection").getElementType(), Object.class);
      Assert.assertEquals(provider.createPropertyDescriptor("collections.readOnlyUntypedCollection").getElementType(), Object.class);
   }

   @Test
   public void testGetPropertyDescriptorWithUnknownProperty()
   {
      try
      {
         provider.createPropertyDescriptor("blah");
         Assert.fail("expected to throw UnknownPropertyException");
      }
      catch (UnknownPropertyException e)
      {
      }
   }

   @Test
   public void testGetPropertyDescriptorWithUnknownNestedProperty()
   {
      try
      {
         provider.createPropertyDescriptor("nestedBean.blah");
         Assert.fail("expected to throw UnknownPropertyException");
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
         provider.createPropertyDescriptor("object").getElementType();
         Assert.fail("expected to throw NotCollectionPropertyException");
      }
      catch (NotCollectionPropertyException e)
      {
      }
   }


}