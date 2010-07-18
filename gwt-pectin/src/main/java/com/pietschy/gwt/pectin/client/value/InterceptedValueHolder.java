package com.pietschy.gwt.pectin.client.value;

import com.google.gwt.user.client.Command;
import com.pietschy.gwt.pectin.client.interceptor.Interceptor;
import com.pietschy.gwt.pectin.client.interceptor.InterceptorChain;

/**
 * A ValueModel that can allows value changes to be intercepted by interested third parties.  The
 * interceptors can abort the value change if required.
 * 
 * @see #interceptUsing(com.pietschy.gwt.pectin.client.interceptor.Interceptor)
 */
public class InterceptedValueHolder<T> extends ValueHolder<T> implements InterceptedMutableValueModel<T>
{
   private InterceptorChain interceptors = new InterceptorChain();

   public InterceptedValueHolder()
   {
   }

   public InterceptedValueHolder(T value)
   {
      super(value);
   }

   @Override
   public void setValue(final T newValue)
   {
      setValueInternal(newValue, false);
   }

   public void setValue(final T newValue, boolean force)
   {
      setValueInternal(newValue, force);
   }

   private void setValueInternal(final T newValue, boolean force)
   {
      if (force)
      {
         super.setValue(newValue);
      }
      else
      {
         interceptors.execute(new Command()
         {
            public void execute()
            {
               InterceptedValueHolder.super.setValue(newValue);
            }
         });
      }
   }

   public void interceptUsing(Interceptor interceptor)
   {
      interceptors.addInterceptor(interceptor);
   }
}