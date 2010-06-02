package com.pietschy.gwt.pectin.client.binding;

import com.pietschy.gwt.pectin.client.value.ValueHolder;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: May 23, 2010
 * Time: 9:35:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class MutableValueModelToMutableValueModelBindingTest
{
   @Test
   public void bindingsGoBothWays()
   {
      // we use the binder to create it... so this is more of an integration test...
      Binder binder = new Binder();
      ValueHolder<String> a = new ValueHolder<String>("a");
      ValueHolder<String> b = new ValueHolder<String>("b");

      Assert.assertEquals(a.getValue(), "a");
      Assert.assertEquals(b.getValue(), "b");

      binder.bind(a).to(b);

      Assert.assertEquals(a.getValue(), "a");
      Assert.assertEquals(a.getValue(), "a");

      b.setValue("b");
      Assert.assertEquals(a.getValue(), "b");
      a.setValue("c");
      Assert.assertEquals(b.getValue(), "c");

   }
}
