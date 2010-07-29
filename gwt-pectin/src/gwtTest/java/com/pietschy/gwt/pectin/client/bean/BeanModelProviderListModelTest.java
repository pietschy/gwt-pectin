package com.pietschy.gwt.pectin.client.bean;


import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.pietschy.gwt.pectin.client.bean.data.TestProvider;
import com.pietschy.gwt.pectin.client.bean.runner.BeanModelProviderListModelTestRunner;
import org.junit.Test;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @since <pre>09/23/2009</pre>
 * @version 1.0
 */
public class BeanModelProviderListModelTest extends GWTTestCase
{
   private BeanModelProviderListModelTestRunner runner;

   @Override
   protected void gwtSetUp() throws Exception
   {
      TestProvider provider = GWT.create(TestProvider.class);
      runner = new BeanModelProviderListModelTestRunner(provider, new JUnitResultCallback());
   }

   public String getModuleName()
   {
      return "com.pietschy.gwt.pectin.PectinTest";
   }

   @Test
   public void testGetNestedListModel()
   {
      runner.a_getNestedListModel();
   }

   @Test
   public void testGetListModelWithUnknownProperty()
   {
      runner.b_getListModelWithUnknownProperty();
   }

   @Test
   public void testGetListModelWithNonCollectionProperty()
   {
      runner.c_getListModelWithNonCollectionProperty();
   }

   @Test
   public void testGetListModelWithWrongElementType()
   {
      runner.d_getListModelWithWrongElementType();
   }
   @Test
   public void testGetListModelWithUnsupportedCollectionType()
   {
      runner.e_getListModelWithUnsupportedCollectionType();
   }
}