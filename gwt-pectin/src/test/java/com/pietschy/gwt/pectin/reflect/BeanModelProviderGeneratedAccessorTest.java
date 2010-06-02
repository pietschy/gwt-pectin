package com.pietschy.gwt.pectin.reflect;


import com.pietschy.gwt.pectin.client.bean.*;
import com.pietschy.gwt.pectin.reflect.test.AnotherBean;
import com.pietschy.gwt.pectin.reflect.test.TestBean;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
   private BeanModelProvider<TestBean> provider;
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
         provider.getBeanAccessorForPropertyPath("blah");
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
      provider.getBeanAccessorForPropertyPath("nestedBean");
      try
      {
         provider.getBeanAccessorForPropertyPath("nestedBean.blah");
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
      BeanPropertyAccessor accessor = provider.getBeanAccessorForPropertyPath(propertyPath);
      String propertyName = getPropertyAtPathEnd(propertyPath);
      assertEquals(accessor.isMutable(propertyName), mutable);
   }


   private String getPropertyAtPathEnd(String propertyPath)
   {
      int index = propertyPath.lastIndexOf('.');
      return index < 0 ? propertyPath : propertyPath.substring(index + 1);
   }


   @Test
   public void testReadValue()
   {
      String name = "abc";
      bean.setString(name);
      BeanPropertyAccessor accessor = provider.getBeanAccessorForPropertyPath("string");
      assertEquals(accessor.readProperty(bean, "string"), name);
   }

   @Test
   public void testReadNestedValue()
   {
      String name = "abc";
      bean.setNestedBean(new AnotherBean());
      bean.getNestedBean().setString(name);

      BeanPropertyAccessor accessor = provider.getBeanAccessorForPropertyPath("nestedBean.string");
      assertEquals(accessor.readProperty(bean.getNestedBean(), "string"), name);
   }


   @Test
   public void testWriteValue()
   {
      String name = "abc";
      provider.getBeanAccessorForPropertyPath("string").writeProperty(bean, "string", name);
      assertEquals(bean.getString(), name);
   }

   @Test
   public void testWriteNestedValue()
   {
      String name = "abc";
      bean.setNestedBean(new AnotherBean());
      provider.getBeanAccessorForPropertyPath("nestedBean.string").writeProperty(bean.getNestedBean(), "string", name);
      assertEquals(bean.getNestedBean().getString(), name);
   }

   @Test
   public void testWriteValueWithNullBean()
   {
      String name = "abc";
      try
      {
         provider.getBeanAccessorForPropertyPath("string").writeProperty(null, "string", name);
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
         provider.getBeanAccessorForPropertyPath("readOnlyObject").writeProperty(bean, "readOnlyObject", "blah");
         fail("expected ImmutablePropertyException");
      }
      catch (ImmutablePropertyException e)
      {

      }
   }


}