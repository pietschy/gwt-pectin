package com.pietschy.gwt.pectin.reflect;


import com.pietschy.gwt.pectin.client.bean.data.TestBean;
import com.pietschy.gwt.pectin.client.bean.runner.BeanModelProviderGeneralTestRunner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>09/23/2009</pre>
 */
public class BeanModelProviderGeneralTest
{
   private BeanModelProviderGeneralTestRunner general;


   @BeforeMethod
   protected void setUp() throws Exception
   {
      general = new BeanModelProviderGeneralTestRunner(new ReflectionBeanModelProvider<TestBean>(TestBean.class),
                                                       new TestNgResultCallback());
   }

   @Test
   public void testModelsUpdateWhenBeanChanges()
   {
      general.a_modelsUpdateWhenBeanChanges();
   }

   @Test
   public void testModelsInitialisedWhenCreatedAfterBeanConfigured()
   {
      general.b_modelsInitialisedWhenCreatedAfterBeanConfigured();
   }

   @Test
   public void testModelCommit()
   {
      general.c_modelCommit();
   }
}