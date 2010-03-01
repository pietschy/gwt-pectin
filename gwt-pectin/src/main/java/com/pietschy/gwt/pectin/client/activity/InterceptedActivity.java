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
public class InterceptedActivity implements Activity
{
   private Interceptor interceptor;
   private Activity delegate;

   public InterceptedActivity(Activity delegate, Interceptor interceptor)
   {
      this.delegate = delegate ;
      this.interceptor = interceptor;
   }

   public Activity intercept(Interceptor interceptor)
   {
      return new InterceptedActivity(this, interceptor);
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

   public ValueModel<Boolean> isEnabled()
   {
      return delegate.isEnabled();
   }
}