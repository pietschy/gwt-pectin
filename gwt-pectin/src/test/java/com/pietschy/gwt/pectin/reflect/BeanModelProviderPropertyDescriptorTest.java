package com.pietschy.gwt.pectin.reflect;


import com.pietschy.gwt.pectin.client.bean.data.TestBean;
import com.pietschy.gwt.pectin.client.bean.runner.BeanModelProviderPropertyDescriptorTestRunner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>09/23/2009</pre>
 */
public class BeanModelProviderPropertyDescriptorTest
{
   private BeanModelProviderPropertyDescriptorTestRunner runner;

   @BeforeMethod
   protected void setup() throws Exception
   {
      runner = new BeanModelProviderPropertyDescriptorTestRunner(new ReflectionBeanModelProvider<TestBean>(TestBean.class),
                                                                 new TestNgResultCallback());
   }

   @Test
   public void getPropertyDescriptorForUnknownPath()
   {
      runner.a_createDescriptorForUnknownPath();
   }

   @Test
   public void getPropertyDescriptorForUnknownNestedPath()
   {
      runner.b_createDescriptorForUnknownNestedPath();
   }

   @Test
   public void isMutable()
   {
      runner.c_isMutable();
   }

   @Test
   public void readValue()
   {
      runner.d_readValue();
   }

   @Test
   public void readNestedValue()
   {
      runner.e_readNestedValue();
   }


   @Test
   public void writeValue()
   {
      runner.f_writeValue();
   }

   @Test
   public void writeNestedValue()
   {
      runner.g_writeNestedValue();
   }

   @Test
   public void writeValueWithNullBean()
   {
      runner.h_writeValueWithNullBean();
   }

   @Test
   public void writeValueToImmutable()
   {
      runner.i_writeValueToImmutable();
   }

   @Test
   public void getValueType()
   {
      runner.j_getValueType();
   }

   @Test
   public void getElementType()
   {
      runner.k_getElementType();
   }

   @Test
   public void getElementTypeForNonCollection()
   {
      runner.l_getElementTypeForNonCollection();
   }

   @Test
   public void createDescriptorForUnknownProperty()
   {
      runner.m_createDescriptorForUnknownProperty();
   }

   @Test
   public void createDescriptorWithUnknownNestedProperty()
   {
      runner.n_createDescriptorWithUnknownNestedProperty();
   }

}