package com.pietschy.gwt.pectin.client.value;

import com.pietschy.gwt.pectin.client.command.Interceptor;
import com.pietschy.gwt.pectin.client.command.Invocation;
import junit.framework.TestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: May 25, 2010
 * Time: 2:28:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class InterceptedValueModelTest extends TestCase
{
   @Test
   public void setValueForcedIgnoresInterceptors()
   {
      InterceptedValueModel<String> model = new InterceptedValueModel<String>();

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
      InterceptedValueModel<String> model = new InterceptedValueModel<String>();

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
