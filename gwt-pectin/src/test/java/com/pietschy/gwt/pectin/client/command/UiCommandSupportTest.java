package com.pietschy.gwt.pectin.client.command;

import com.google.gwt.user.client.Command;
import com.pietschy.gwt.pectin.client.interceptor.Interceptor;
import com.pietschy.gwt.pectin.client.interceptor.Invocation;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Apr 26, 2010
 * Time: 10:27:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class UiCommandSupportTest
{
   @BeforeMethod
   public void setUp() throws Exception {
   }

   @Test
   public void interceptorsAreCalledInTheCorrectOrder() throws Exception {


      final AtomicBoolean commandInterceptorRun = new AtomicBoolean(false);
      final AtomicBoolean firstInterceptorRun = new AtomicBoolean(false);
      final AtomicBoolean secondInterceptorRun = new AtomicBoolean(false);
      final AtomicBoolean commandRun = new AtomicBoolean(false);

      // the overridden intercept method runs first.  This allows the command to run things
      // like validators before any noisy interceptors get run.. I.e. you wouldn't prompt to
      // save changes until you've checked the the changes are actually valid.
      UiCommandSupport subject = new TestCommand()
      {
         @Override
         protected void intercept(Invocation invocation)
         {
            assertFalse(commandInterceptorRun.getAndSet(true));
            assertFalse(firstInterceptorRun.get());
            assertFalse(secondInterceptorRun.get());
            assertFalse(commandRun.get());
            // super should proceed...
            super.intercept(invocation);
         }
      };


      // this interceptor will run after the following one.
      subject.interceptUsing(new Interceptor()
      {
         public void intercept(Invocation invocation)
         {
            // the intercept method runs first.
            assertTrue(commandInterceptorRun.get());

            // interceptors get executed in reverse order.
            assertFalse(firstInterceptorRun.getAndSet(true));
            assertTrue(secondInterceptorRun.get());

            // and the command runs last
            assertFalse(commandRun.get());
            
            invocation.proceed();
         }
      });

      // this interceptor will run first...
      subject.interceptUsing(new Interceptor()
      {
         public void intercept(Invocation invocation)
         {
            // the intercept method runs first.
            assertTrue(commandInterceptorRun.get());

            // interceptors get executed in reverse order.
            assertFalse(firstInterceptorRun.get());
            assertFalse(secondInterceptorRun.getAndSet(true));

            // and the command runs last
            assertFalse(commandRun.get());

            invocation.proceed();
         }
      });

      // the command should run last
      subject.runWithInterceptors(new Command()
      {
         public void execute()
         {
            assertTrue(commandInterceptorRun.get());
            assertTrue(firstInterceptorRun.get());
            assertTrue(secondInterceptorRun.getAndSet(true));
            commandRun.set(true);
         }
      });

      assertTrue(commandRun.get());

   }

   private static class TestCommand extends UiCommandSupport
   {
      public Events always()
      {
         return null;
      }

      public Events onNextCall()
      {
         return null;
      }

      public void execute()
      {

      }
   }
}
