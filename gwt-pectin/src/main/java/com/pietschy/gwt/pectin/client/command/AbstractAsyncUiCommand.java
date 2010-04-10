package com.pietschy.gwt.pectin.client.command;

import com.google.gwt.user.client.Command;
import com.pietschy.gwt.pectin.client.channel.Channel;

/**
 * Base class for commands wishing to invoke an asynchronous operation.
 */
public abstract class AbstractAsyncUiCommand<R, E> extends AbstractTemporalUiCommand implements AsyncUiCommand<R,E>
{

   private AsyncEventSupport<R,E> eventSupport = new AsyncEventSupport<R, E>();

   public AsyncEvents<R, E> always()
   {
      return eventSupport.always();
   }

   /**
    * Gets a instance of {@link com.pietschy.gwt.pectin.client.command.AsyncEvents} that will only fire
    * on the next execution.
    * @return the events for the next execution.
    */
   public AsyncEvents<R, E> onNextCall()
   {
      return eventSupport.onNextCall();
   }

   /**
    * Gets the result channel for this command, using this is equivalent using the {@link #always()}
    * command events.
    * @return this command result channel.
    */
   public Channel<R> getResults()
   {
      return eventSupport.getResultChannel();
   }

   /**
    * Gets the error channel for this command, using this is equivalent using the {@link #always()}
    * command events.
    * @return this command error channel.
    */
   public Channel<E> getErrors()
   {
      return eventSupport.getErrorChannel();
   }


   /**
    * Method for subclasses to perform work after the callback has succeeded.  This
    * method is called before the result is published.
    * @param result the result of the command
    */
   protected void afterSuccess(R result)
   {
   }

   /**
    * Method for subclasses to perform work after an error has been generated.  This
    * method is called before the error is published.
    * @param error the error
    */
   protected void afterError(E error)
   {
   }

   @Override
   protected void startExecution(Context context)
   {
      // Create our execution context.
      runWithInterceptors(new ExecutionContext(context, eventSupport.prepareEvents()));
   }


   private class ExecutionContext implements Command, AsyncCommandCallback<R, E>
   {
      private Context context;
      private AsyncEventSupport<R,E>.Trigger events;

      public ExecutionContext(Context context, AsyncEventSupport<R, E>.Trigger trigger)
      {
         this.context = context;
         events = trigger;
      }

      public void execute()
      {
         context.notifyStarted();
         events.fireStart();
         performAsyncOperation(this);
      }

      public void publishSuccess(R result)
      {
         try
         {
            afterSuccess(result);
            events.fireSuccess(result);
            events.fireFinished();
         }
         finally
         {
            // just so our button always gets enabled even if a listener
            // throws an exception.  This is just for debugging ease.
            context.notifyFinished();
         }
      }

      public void publishError(E error)
      {
         try
         {
            afterError(error);
            events.fireError(error);
            events.fireFinished();
         }
         finally
         {
            // just so our button always gets enabled even if a listener
            // throws an exception.  This is just for debugging ease.
            context.notifyFinished();
         }
      }
   }

   protected abstract void performAsyncOperation(AsyncCommandCallback<R, E> callback);

}
