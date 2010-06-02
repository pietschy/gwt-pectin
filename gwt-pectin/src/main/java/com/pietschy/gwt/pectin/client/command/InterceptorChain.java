package com.pietschy.gwt.pectin.client.command;

import com.google.gwt.user.client.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Mar 18, 2010
 * Time: 5:11:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class InterceptorChain
{
   private ArrayList<Interceptor> interceptorList = new ArrayList<Interceptor>();

   /**
    * This adds interceptors to run before the command gets to execute.  These
    *
    * @param interceptor
    * @param others
    */
   public void addInterceptor(Interceptor interceptor)
   {
      interceptorList.add(interceptor);
   }

   public void addInterceptors(Collection<Interceptor> interceptors)
   {
      interceptorList.addAll(interceptors);
   }

   public void addInterceptors(Interceptor interceptor, Interceptor... others)
   {
      addInterceptor(interceptor);
      addInterceptors(Arrays.asList(others));
   }

   /**
    * Executes the specified command if and only if all the interceptors proceed.
    * @param executor the command to execute if all the interceptors proceed.
    */
   public void execute(Command executor)
   {
      buildInvocationChain(executor).proceed();
   }

   protected Invocation buildInvocationChain(final Command executor)
   {
      Invocation invocation = new Invocation(new Command()
      {
         public void execute()
         {
            executor.execute();
         }
      });

      for (Interceptor interceptor : interceptorList)
      {
         invocation = new Invocation(invocation, interceptor);
      }

      return invocation;
   }
}
