package com.pietschy.gwt.pectin.client.bean.runner;


import com.pietschy.gwt.pectin.client.bean.*;
import com.pietschy.gwt.pectin.client.bean.data.AnotherBean;
import com.pietschy.gwt.pectin.client.bean.data.BeanWithCollections;
import com.pietschy.gwt.pectin.client.bean.data.TestBean;

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
public class BeanModelProviderPropertyDescriptorTestRunner extends AbstractRunner<TestBean>
{
   public BeanModelProviderPropertyDescriptorTestRunner(AbstractBeanModelProvider<TestBean> provider, ResultCallback callback)
   {
      super(provider, callback);
   }

   public void a_createDescriptorForUnknownPath()
   {
      try
      {
         provider.createPropertyDescriptor("blah");
         callback.fail("expected to throw UnknownPropertyException");
      }
      catch (UnknownPropertyException e)
      {
      }
   }

   public void b_createDescriptorForUnknownNestedPath()
   {
      // this should work so we guarantee our nestedBeanProperty exists
      provider.createPropertyDescriptor("nestedBean");
      try
      {
         provider.createPropertyDescriptor("nestedBean.blah");
         callback.fail("expected to throw UnknownPropertyException");
      }
      catch (UnknownPropertyException e)
      {
      }
   }

   public void c_isMutable()
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

      doMutableTest("readOnlyNestedBean", false);
      doMutableTest("readOnlyNestedBean.string", true);
      doMutableTest("readOnlyNestedBean.stringList", true);

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
      callback.assertEquals(descriptor.isMutable(), mutable);
   }


   public void d_readValue()
   {
      TestBean bean = new TestBean();
      String name = "abc";
      bean.setString(name);
      PropertyDescriptor descriptor = provider.createPropertyDescriptor("string");
      callback.assertEquals(descriptor.readProperty(bean), name);
   }

   public void e_readNestedValue()
   {
      TestBean bean = new TestBean();
      String name = "abc";
      bean.setNestedBean(new AnotherBean());
      bean.getNestedBean().setString(name);

      PropertyDescriptor descriptor = provider.createPropertyDescriptor("nestedBean.string");
      callback.assertEquals(descriptor.readProperty(bean.getNestedBean()), name);
   }


   public void f_writeValue()
   {
      TestBean bean = new TestBean();
      String name = "abc";
      PropertyDescriptor descriptor = provider.createPropertyDescriptor("string");
      descriptor.writeProperty(bean, name);
      callback.assertEquals(bean.getString(), name);
   }

   public void g_writeNestedValue()
   {
      TestBean bean = new TestBean();
      String name = "abc";
      bean.setNestedBean(new AnotherBean());
      PropertyDescriptor descriptor = provider.createPropertyDescriptor("nestedBean.string");
      descriptor.writeProperty(bean.getNestedBean(), name);
      callback.assertEquals(bean.getNestedBean().getString(), name);
   }

   public void h_writeValueWithNullBean()
   {
      String name = "abc";
      try
      {
         PropertyDescriptor descriptor = provider.createPropertyDescriptor("string");
         descriptor.writeProperty(null, name);
         callback.fail("expected TargetBeanIsNullException");
      }
      catch (TargetBeanIsNullException e)
      {
         // this should happen
      }
   }

   public void i_writeValueToImmutable()
   {
      try
      {
         TestBean bean = new TestBean();
         PropertyDescriptor key = provider.createPropertyDescriptor("readOnlyObject");
         key.writeProperty(bean, "blah");
         callback.fail("expected ImmutablePropertyException");
      }
      catch (ReadOnlyPropertyException e)
      {

      }
   }

   public void j_getValueType()
   {
      callback.assertEquals(provider.createPropertyDescriptor("object").getValueType(), Object.class);
      callback.assertEquals(provider.createPropertyDescriptor("string").getValueType(), String.class);
      callback.assertEquals(provider.createPropertyDescriptor("primitiveInt").getValueType(), Integer.class);
      callback.assertEquals(provider.createPropertyDescriptor("primitiveBoolean").getValueType(), Boolean.class);
      callback.assertEquals(provider.createPropertyDescriptor("objectInteger").getValueType(), Integer.class);
      callback.assertEquals(provider.createPropertyDescriptor("objectBoolean").getValueType(), Boolean.class);
      callback.assertEquals(provider.createPropertyDescriptor("readOnlyObject").getValueType(), Object.class);
      callback.assertEquals(provider.createPropertyDescriptor("readOnlyPrimitiveInt").getValueType(), Integer.class);

      callback.assertEquals(provider.createPropertyDescriptor("nestedBean").getValueType(), AnotherBean.class);
      callback.assertEquals(provider.createPropertyDescriptor("nestedBean.string").getValueType(), String.class);
      callback.assertEquals(provider.createPropertyDescriptor("nestedBean.stringList").getValueType(), List.class);

      callback.assertEquals(provider.createPropertyDescriptor("collections").getValueType(), BeanWithCollections.class);
      callback.assertEquals(provider.createPropertyDescriptor("collections.stringCollection").getValueType(), Collection.class);
      callback.assertEquals(provider.createPropertyDescriptor("collections.integerCollection").getValueType(), Collection.class);
      callback.assertEquals(provider.createPropertyDescriptor("collections.stringList").getValueType(), List.class);
      callback.assertEquals(provider.createPropertyDescriptor("collections.stringSortedSet").getValueType(), SortedSet.class);
      callback.assertEquals(provider.createPropertyDescriptor("collections.untypedCollection").getValueType(), Collection.class);
      callback.assertEquals(provider.createPropertyDescriptor("collections.readOnlyUntypedCollection").getValueType(), Collection.class);
   }

   public void k_getElementType()
   {
      callback.assertEquals(provider.createPropertyDescriptor("collections.stringCollection").getElementType(), String.class);
      callback.assertEquals(provider.createPropertyDescriptor("collections.integerCollection").getElementType(), Integer.class);
      callback.assertEquals(provider.createPropertyDescriptor("collections.stringList").getElementType(), String.class);
      callback.assertEquals(provider.createPropertyDescriptor("collections.stringSortedSet").getElementType(), String.class);
      callback.assertEquals(provider.createPropertyDescriptor("collections.untypedCollection").getElementType(), Object.class);
      callback.assertEquals(provider.createPropertyDescriptor("collections.readOnlyUntypedCollection").getElementType(), Object.class);
   }

   public void l_getElementTypeForNonCollection()
   {
      try
      {
         provider.createPropertyDescriptor("object").getElementType();
         callback.fail("expected to throw NotCollectionPropertyException");
      }
      catch (NotCollectionPropertyException e)
      {
      }
   }

   public void m_createDescriptorForUnknownProperty()
   {
      try
      {
         provider.createPropertyDescriptor("blah");
         callback.fail("expected to throw UnknownPropertyException");
      }
      catch (UnknownPropertyException e)
      {
      }
   }

   public void n_createDescriptorWithUnknownNestedProperty()
   {
      try
      {
         provider.createPropertyDescriptor("nestedBean.blah");
         callback.fail("expected to throw UnknownPropertyException");
      }
      catch (UnknownPropertyException e)
      {
      }
   }


}