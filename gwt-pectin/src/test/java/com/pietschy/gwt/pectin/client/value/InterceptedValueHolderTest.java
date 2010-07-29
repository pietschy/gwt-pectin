package com.pietschy.gwt.pectin.client.value;

import com.pietschy.gwt.pectin.client.interceptor.Interceptor;
import com.pietschy.gwt.pectin.client.interceptor.Invocation;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: May 25, 2010
 * Time: 2:28:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class InterceptedValueHolderTest
{
   @Test
   public void setValueForcedIgnoresInterceptors()
   {
      InterceptedValueHolder<String> model = new InterceptedValueHolder<String>();

      model.interceptUsing(new Interceptor()
      {
         public void intercept(Invocation invocation)
         {
            // this interceptor blocks...
         }
      });


      // interceptor should block the set
      model.setValue("abc");
      Assert.assertNull(model.getValue());

      // interceptor should block the set
      model.setValue("abc", false);
      Assert.assertNull(model.getValue());

      // this should ignore the interceptor..
      model.setValue("def", true);
      assertEquals(model.getValue(), "def");
   }

   @Test
   public void setValueIsIntercepted()
   {
      InterceptedValueHolder<String> model = new InterceptedValueHolder<String>();

      final AtomicBoolean interceptorCalled = new AtomicBoolean(false);
      model.interceptUsing(new Interceptor()
      {
         public void intercept(Invocation invocation)
         {
            interceptorCalled.set(true);
            invocation.proceed();
         }
      });

      // interceptor should block the set
      model.setValue("abc");
      assertEquals(model.getValue(), "abc");
      assertTrue(interceptorCalled.get());
   }
}
