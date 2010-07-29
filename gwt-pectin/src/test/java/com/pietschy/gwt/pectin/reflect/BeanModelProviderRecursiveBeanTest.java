package com.pietschy.gwt.pectin.reflect;


import com.pietschy.gwt.pectin.client.bean.data.recursion.TestBean;
import com.pietschy.gwt.pectin.client.bean.runner.BeanModelProviderRecursiveTestRunner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>09/23/2009</pre>
 */
public class BeanModelProviderRecursiveBeanTest
{
   private BeanModelProviderRecursiveTestRunner recursive;


   @BeforeMethod
   protected void setUp() throws Exception
   {
      recursive = new BeanModelProviderRecursiveTestRunner(new ReflectionBeanModelProvider<TestBean>(TestBean.class),
                                                           new TestNgResultCallback());
   }

   @Test
   public void providerWithRecursiveBean()
   {
      // do we really need it????
      //recursive.a_providerWithRecursiveBean();
   }


}