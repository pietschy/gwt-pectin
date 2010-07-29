package com.pietschy.gwt.pectin.reflect;


import com.pietschy.gwt.pectin.client.bean.data.TestBean;
import com.pietschy.gwt.pectin.client.bean.runner.BeanModelProviderListModelTestRunner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @since <pre>09/23/2009</pre>
 * @version 1.0
 */
public class BeanModelProviderListModelTest
{
   private BeanModelProviderListModelTestRunner runner;

   @BeforeMethod
   protected void gwtSetUp() throws Exception
   {
      runner = new BeanModelProviderListModelTestRunner(new ReflectionBeanModelProvider<TestBean>(TestBean.class),
                                                        new TestNgResultCallback());
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