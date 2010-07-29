package com.pietschy.gwt.pectin.client.bean;


import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.pietschy.gwt.pectin.client.bean.data.TestProvider;
import com.pietschy.gwt.pectin.client.bean.runner.BeanModelProviderValueModelTestRunner;
import org.junit.Test;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>09/23/2009</pre>
 */
public class BeanModelProviderValueModelTest extends GWTTestCase
{
   private BeanModelProviderValueModelTestRunner runner;


   @Override
   protected void gwtSetUp() throws Exception
   {
      TestProvider provider = GWT.create(TestProvider.class);
      runner = new BeanModelProviderValueModelTestRunner(provider,
                                                         new JUnitResultCallback());
   }

   public String getModuleName()
   {
      return "com.pietschy.gwt.pectin.PectinTest";
   }

   @Test
   public void testGetNestedValueModel()
   {
      runner.a_getNestedValueModel();
   }


   @Test
   public void testGetValueModelForUnknownProperty()
   {
      runner.b_getValueModelForUnknownProperty();
   }

   @Test
   public void testGetValueModelWithWrongType()
   {
      runner.c_getValueModelWithWrongType();
   }

}