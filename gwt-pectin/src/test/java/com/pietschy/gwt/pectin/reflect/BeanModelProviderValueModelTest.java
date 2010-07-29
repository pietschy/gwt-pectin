package com.pietschy.gwt.pectin.reflect;


import com.pietschy.gwt.pectin.client.bean.data.TestBean;
import com.pietschy.gwt.pectin.client.bean.runner.BeanModelProviderValueModelTestRunner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @since <pre>09/23/2009</pre>
 * @version 1.0
 */
public class BeanModelProviderValueModelTest
{
   private BeanModelProviderValueModelTestRunner runner;

   @BeforeMethod
   protected void setUp() throws Exception
   {
      runner = new BeanModelProviderValueModelTestRunner(new ReflectionBeanModelProvider<TestBean>(TestBean.class),
                                                         new TestNgResultCallback());
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