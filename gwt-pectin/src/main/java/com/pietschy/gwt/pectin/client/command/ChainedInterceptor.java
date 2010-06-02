package com.pietschy.gwt.pectin.client.command;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: May 25, 2010
 * Time: 4:29:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChainedInterceptor implements Interceptor
{
   private InterceptorChain chain = new InterceptorChain();

   public ChainedInterceptor(Interceptor interceptor, Interceptor... others)
   {
      chain.addInterceptors(interceptor, others);
   }

   public void addInterceptor(Interceptor interceptor)
   {
      chain.addInterceptor(interceptor);
   }

   public void addInterceptors(Interceptor interceptor, Interceptor... others)
   {
      chain.addInterceptors(interceptor, others);
   }

   public void addInterceptors(Collection<Interceptor> interceptors)
   {
      chain.addInterceptors(interceptors);
   }

   public void intercept(Invocation invocation)
   {
      chain.execute(invocation.getProceedCommand());
   }
}
