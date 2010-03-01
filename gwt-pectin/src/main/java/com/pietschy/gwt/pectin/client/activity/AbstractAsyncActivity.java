package com.pietschy.gwt.pectin.client.activity;

import com.google.gwt.user.client.Command;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * 
 */
public abstract class AbstractAsyncActivity<R, E> extends AbstractActivity implements AsyncActivity<R, E>
{
   private ValueHolder<Boolean> activeModel = new ValueHolder<Boolean>(false);

   private DefaultChannel<R> resultChannel = new DefaultChannel<R>();
   private DefaultChannel<E> errorChannel = new DefaultChannel<E>();

   private ResultCallback<R, E> callback = new ResultCallback<R, E>()
   {
      public void publishSuccess(R result)
      {
         afterSuccess(result);
         resultChannel.publish(result);
         activeModel.setValue(false);
      }

      public void publishError(E error)
      {
         afterError(error);
         errorChannel.publish(error);
         activeModel.setValue(false);
      }
   };

   public AsyncActivity<R, E> intercept(final Interceptor interceptor)
   {
      return new InterceptedAsyncActivity<R, E>(this, interceptor);
   }

   protected boolean beforeExecute()
   {
      return true;
   }

   protected void afterSuccess(R result)
   {
   }

   protected void afterError(E error)
   {

   }

   public void execute()
   {
      // let's not get all re-entrant...
      if (!isActive().getValue())
      {
         if (beforeExecute())
         {
            activeModel.setValue(true);
            performActivity(callback);
         }
      }
   }

   public ChannelRegistration onSuccessExecute(final Command command)
   {
      return getResults().sendTo(new Destination<R>()
      {
         public void receive(R value)
         {
            command.execute();
         }
      });
   }

   public ChannelRegistration onSuccessCall(final Callback<R> callback)
   {
      return getResults().sendTo(new Destination<R>()
      {
         public void receive(R value)
         {
            callback.call(value);
         }
      });
   }

   public ChannelRegistration onErrorExecute(final Command command)
   {
      return getErrors().sendTo(new Destination<E>()
      {
         public void receive(E value)
         {
            command.execute();
         }
      });
   }

   public ChannelRegistration onErrorCall(final Callback<E> callback)
   {
      return getErrors().sendTo(new Destination<E>()
      {
         public void receive(E value)
         {
            callback.call(value);
         }
      });
   }

   public Channel<R> getResults()
   {
      return resultChannel;
   }

   public Channel<E> getErrors()
   {
      return errorChannel;
   }

   protected abstract void performActivity(ResultCallback<R, E> callback);

   public ValueModel<Boolean> isActive()
   {
      return activeModel;
   }
}