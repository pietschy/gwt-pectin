package com.pietschy.gwt.pectin.client.bean;


import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.pietschy.gwt.pectin.client.bean.data.TestBean;
import com.pietschy.gwt.pectin.client.bean.data.TestProvider;
import com.pietschy.gwt.pectin.client.bean.runner.BeanModelProviderPropertyDescriptorTestRunner;
import org.junit.Test;


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
   BeanModelProviderPropertyDescriptorTestRunner runner;
   private JUnitResultCallback callback;

   @Override
   protected void gwtSetUp() throws Exception
   {
      provider = GWT.create(TestProvider.class);
      callback = new JUnitResultCallback();
      runner = new BeanModelProviderPropertyDescriptorTestRunner(provider, callback);
   }

   public String getModuleName()
   {
      return "com.pietschy.gwt.pectin.PectinTest";
   }

   @Test
   public void testCreateDescriptorForUnknownPath()
   {
      runner.a_createDescriptorForUnknownPath();
   }

   @Test
   public void testCreateDescriptorForUnknownNestedPath()
   {
      runner.b_createDescriptorForUnknownNestedPath();
   }

   @Test
   public void testIsMutable()
   {
      runner.c_isMutable();
   }


   @Test
   public void testReadValue()
   {
      runner.d_readValue();
   }

   @Test
   public void testReadNestedValue()
   {
      runner.e_readNestedValue();
   }


   @Test
   public void testWriteValue()
   {
      runner.f_writeValue();
   }

   @Test
   public void testWriteNestedValue()
   {
      runner.g_writeNestedValue();
   }

   @Test
   public void testWriteValueWithNullBean()
   {
      runner.h_writeValueWithNullBean();
   }

   @Test
   public void testWriteValueToImmutable()
   {
      runner.i_writeValueToImmutable();
   }

   @Test
   public void testGetValueType()
   {
      runner.j_getValueType();
   }

   @Test
   public void testGetElementType()
   {
      runner.k_getElementType();
   }

   @Test
   public void testGetElementTypeForNonCollection()
   {
      runner.l_getElementTypeForNonCollection();
   }

   @Test
   public void testCreateDescriptorForUnknownProperty()
   {
      runner.m_createDescriptorForUnknownProperty();
   }

   @Test
   public void testCreateDescriptorWithUnknownNestedProperty()
   {
      runner.n_createDescriptorWithUnknownNestedProperty();
   }

}