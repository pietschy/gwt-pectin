package com.pietschy.gwt.pectin.client.bean.runner;


import com.pietschy.gwt.pectin.client.bean.AbstractBeanModelProvider;
import com.pietschy.gwt.pectin.client.bean.ResultCallback;
import com.pietschy.gwt.pectin.client.bean.UnknownPropertyException;
import com.pietschy.gwt.pectin.client.bean.data.recursion.RecursiveBeanOne;
import com.pietschy.gwt.pectin.client.bean.data.recursion.RecursiveBeanTwo;
import com.pietschy.gwt.pectin.client.bean.data.recursion.TestBean;

/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>09/23/2009</pre>
 */
public class BeanModelProviderRecursiveTestRunner extends AbstractRunner<TestBean>
{
   public BeanModelProviderRecursiveTestRunner(AbstractBeanModelProvider<TestBean> provider, ResultCallback callback)
   {
      super(provider, callback);
   }

   public void a_providerWithRecursiveBean()
   {
      provider.getValueModel("one", RecursiveBeanOne.class);
      provider.getValueModel("one.two", RecursiveBeanTwo.class);
      provider.getValueModel("one.two.one", RecursiveBeanOne.class);
      provider.getValueModel("one.two.one.two", RecursiveBeanTwo.class);
      try
      {
         provider.getValueModel("one.two.one.two.one", RecursiveBeanOne.class);
         callback.fail("expected UnknownPropertyException for path greater than 4 deep");
      }
      catch (UnknownPropertyException e)
      {
      }
   }
}