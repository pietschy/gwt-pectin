package com.pietschy.gwt.pectin.client.activity;

import com.google.gwt.user.client.Command;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Feb 28, 2010
* Time: 3:00:25 PM
* To change this template use File | Settings | File Templates.
*/
public class InterceptedAsyncActivity<R,E> implements AsyncActivity<R, E>
{
   private final Interceptor interceptor;
   private AsyncActivity<R, E> delegate;

   public InterceptedAsyncActivity(AsyncActivity<R, E> delegate, Interceptor interceptor)
   {
      this.delegate = delegate ;
      this.interceptor = interceptor;
   }

   public AsyncActivity<R, E> intercept(Interceptor interceptor)
   {
      return new InterceptedAsyncActivity<R,E>(this, interceptor);
   }

   public void execute()
   {
      interceptor.intercept(new Invocation()
      {
         @Override
         public Command getProceedCommand()
         {
            return delegate;
         }
      });
   }

   public ChannelRegistration onSuccessExecute(Command command)
   {
      return delegate.onSuccessExecute(command);
   }

   public ChannelRegistration onSuccessCall(Callback<R> callback)
   {
      return delegate.onSuccessCall(callback);
   }

   public ChannelRegistration onErrorExecute(Command command)
   {
      return delegate.onErrorExecute(command);
   }

   public ChannelRegistration onErrorCall(Callback<E> callback)
   {
      return delegate.onErrorCall(callback);
   }

   public Channel<R> getResults()
   {
      return delegate.getResults();
   }

   public Channel<E> getErrors()
   {
      return delegate.getErrors();
   }

   public ValueModel<Boolean> isEnabled()
   {
      return delegate.isEnabled();
   }

   public ValueModel<Boolean> isActive()
   {
      return delegate.isActive();
   }
}
