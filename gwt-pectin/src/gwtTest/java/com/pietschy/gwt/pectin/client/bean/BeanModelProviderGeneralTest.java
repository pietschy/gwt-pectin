package com.pietschy.gwt.pectin.client.bean;


import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.pietschy.gwt.pectin.client.bean.data.TestProvider;
import com.pietschy.gwt.pectin.client.bean.runner.BeanModelProviderGeneralTestRunner;
import org.junit.Test;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>09/23/2009</pre>
 */
public class BeanModelProviderGeneralTest extends GWTTestCase
{
   private TestProvider provider;
   private BeanModelProviderGeneralTestRunner runner;
   private JUnitResultCallback callback;

   @Override
   protected void gwtSetUp() throws Exception
   {
      provider = GWT.create(TestProvider.class);
      callback = new JUnitResultCallback();
      runner = new BeanModelProviderGeneralTestRunner(provider, callback);
   }

   public String getModuleName()
   {
      return "com.pietschy.gwt.pectin.PectinTest";
   }

   @Test
   public void testModelsUpdateWhenBeanChanges()
   {
      runner.a_modelsUpdateWhenBeanChanges();
   }

   @Test
   public void testModelsInitialisedWhenCreatedAfterBeanConfigured()
   {
      runner.b_modelsInitialisedWhenCreatedAfterBeanConfigured();
   }

   @Test
   public void testModelCommit()
   {
      runner.c_modelCommit();
   }

}