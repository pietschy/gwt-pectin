package com.pietschy.gwt.pectin.client.bean;


import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.pietschy.gwt.pectin.client.bean.data.recursion.RecursiveBeanProvider;
import com.pietschy.gwt.pectin.client.bean.runner.BeanModelProviderRecursiveTestRunner;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>09/23/2009</pre>
 */
public class BeanModelProviderRecursiveTest extends GWTTestCase
{
   private ResultCallback callback = new JUnitResultCallback();
   private BeanModelProviderRecursiveTestRunner runner;

   @Override
   protected void gwtSetUp() throws Exception
   {
      runner = new BeanModelProviderRecursiveTestRunner(GWT.<RecursiveBeanProvider>create(RecursiveBeanProvider.class), callback);
   }

   public String getModuleName()
   {
      return "com.pietschy.gwt.pectin.PectinTest";
   }

   public void testDepthIsEqualToFour()
   {
      runner.a_providerWithRecursiveBean();
   }

}