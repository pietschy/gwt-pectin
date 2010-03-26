package com.pietschy.gwt.pectin.client.activity;

import com.google.gwt.user.client.Command;
import com.pietschy.gwt.pectin.client.channel.Channel;
import com.pietschy.gwt.pectin.client.channel.DefaultChannel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Mar 18, 2010
 * Time: 11:12:03 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractAsyncActivity<R, E> extends AbstractTemporalActivity implements AsyncActivity<R,E>
{
   private DefaultChannel<R> resultChannel = new DefaultChannel<R>();
   private DefaultChannel<E> errorChannel = new DefaultChannel<E>();
   private InterceptorChain interceptorChain = new InterceptorChain();

   private AsyncEventsImpl<R, E> alwaysEvents;
   private AsyncEventsImpl<R, E> oneTimeEvents;

   public AsyncEvents<R, E> always()
   {
      if (alwaysEvents == null)
      {
         alwaysEvents = new AsyncEventsImpl<R, E>();
      }
      return alwaysEvents;
   }

   /**
    * Gets a instance of {@link com.pietschy.gwt.pectin.client.activity.AsyncEvents} that will only fire
    * on the next execution.
    * @return the events for the next execution.
    */
   public AsyncEvents<R, E> onNextCall()
   {
      if (oneTimeEvents == null)
      {
         oneTimeEvents = new AsyncEventsImpl<R, E>();
      }
      return oneTimeEvents;
   }

   /**
    * Gets the result channel for this activity, using this is equivalent using the {@link #always()}
    * activity events.
    * @return this activity's result channel.
    */
   public Channel<R> getResults()
   {
      return resultChannel;
   }

   /**
    * Gets the error channel for this activity, using this is equivalent using the {@link #always()}
    * activity events.
    * @return this activity's error channel.
    */
   public Channel<E> getErrors()
   {
      return errorChannel;
   }

   /**
    * Adds an interceptor to run before the command executes.  The interceptor <b>must</b> invoke
    * {@link com.pietschy.gwt.pectin.client.activity.Invocation#proceed()} or execute {@link Invocation#getProceedCommand()}
    * for the execution to proceed.  Failure to do so will effectively abort the execution.  In reality
    * it's a case of a silent abort since the activity is not notified of the interception.
    * <p>
    * As such it it imperative that interceptors be tested to ensure they always execute the proceed
    * command if they don't want the execution to simple stop without further ado.
    * <p>
    * Of special interest is the case where onNextCall().sendSuccessTo(..) has been used and an interceptor
    * chooses not to proceed.  In this case the events will never be fired.
    * <p>
    * Notes to self:  In order for other parties to be notified of cancellation then the Invocation would need
    * to provide a cancel or abort method.  The main issue with this is that failing to call it will still abort
    * the execution.  I.e. abort is equivalent to not calling proceed.  There are a pile of bugs waiting to happen
    * if I add an Invocation.abort() methods and onAborting(..) style events when I can't guarantee they'll be called.
    *
    * @param interceptor the interceptor to add.
    */
   public final void beforeStartingRun(Interceptor interceptor)
   {
      interceptorChain.addInterceptor(interceptor);
   }

   /**
    * Allows subclasses to intercept the execution.  This method will run before any
    * other interceptors.
    * <p/>
    * The activity <b>will only execute</b> when <code>invocation.proceed()</code> or <code>invocation.getProceedCommand().execute()</code>.
    * is called.
    * <p/>
    * To cancel the command don't call <code>invocation.proceed()</code> or <code>invocation.getProceedCommand().execute()</code>.
    * <p/>
    * <b>NOTE:</b> Calling <code>super.beforeExecute(invocation)</code> is equivalent to simply calling
    * <code>invocation.proceed()</code>.
    * <p/>
    * Examples:
    * <pre>
    * protected void beforeExecute(Invocation invocation)
    * {
    *    // only proceed if the model is valid.
    *    if (model.validate())
    *    {
    *       invocation.proceed();
    *    }
    * }
    * </pre>
    *
    * @param invocation the invocation chain.
    * @see #beforeStartingRun(Interceptor)
    */
   protected void beforeStarting(Invocation invocation)
   {
      invocation.proceed();
   }

   /**
    * Method for subclasses to perform work after all the interceptors have run and
    * the activity has started.
    */
   protected void onStarting()
   {
   }

   /**
    * Method for subclasses to perform work after the callback has succeeded.  This
    * method is called before the result is published.
    */
   protected void afterSuccess(R result)
   {
   }

   /**
    * Method for subclasses to perform work after an error has been generated.  This
    * method is called before the error is published.
    */
   protected void afterError(E error)
   {
   }

   /**
    * Method for subclasses to perform work after the command has finished executing
    * but before any result or error is published.
    */
   protected void afterExecute()
   {
   }


   public void execute()
   {
      if (!enabled().getValue())
      {
         throw new IllegalStateException("execute called while disabled");
      }

      if (active().getValue())
      {
         // the default implementation throws an {@link ReEntrantExecutionException}.
         handleReEntrantExecution();
      }

      // Create our execution context.
      ExecutionContext executionContext = new ExecutionContext(resultChannel, errorChannel, oneTimeEvents, alwaysEvents);

      // now clear out one time events so that any subsequent calls to onNextCall() will be associated with
      // the next execution.  If any interceptors abort the execution then the events will never fire.
      oneTimeEvents = null;

      // create the interceptor chain with our execution at the end of it.  By passing it to
      // before starting we guarantee that beforeStarting has first shot and intercepting it.
      beforeStarting(interceptorChain.buildInvocationChain(executionContext));
   }

   /**
    * Called when ever execute is invoke while active == true;  This is just an entry point for experimentation
    * if I ever want to see if queuing events is useful or insane.
    */
   protected void handleReEntrantExecution()
   {
      throw new ReEntrantExecutionException();
   }


   private class ExecutionContext implements Command, ActivityCallback<R, E>
   {
      private DefaultChannel<R> resultChannel;
      private DefaultChannel<E> errorChannel;
      private AsyncEventsImpl<R, E> oneTimeEvents;
      private AsyncEventsImpl<R, E> alwaysEvents;

      public ExecutionContext(DefaultChannel<R> resultChannel, DefaultChannel<E> errorChannel, AsyncEventsImpl<R, E> oneTimeEvents, AsyncEventsImpl<R, E> alwaysEvents)
      {
         this.resultChannel = resultChannel;
         this.errorChannel = errorChannel;
         this.oneTimeEvents = oneTimeEvents;
         this.alwaysEvents = alwaysEvents;
      }

      public void execute()
      {
         setActive(true);
         onStarting();
         if (alwaysEvents != null)
         {
            alwaysEvents.fireStart();
         }
         if (oneTimeEvents != null)
         {
            oneTimeEvents.fireStart();
         }

         performActivity(this);
      }

      public void publishSuccess(R result)
      {
         try
         {
            afterSuccess(result);
            resultChannel.publish(result);
            if (alwaysEvents != null)
            {
               alwaysEvents.fireSuccess(result);
            }
            if (oneTimeEvents != null)
            {
               oneTimeEvents.fireSuccess(result);
            }
            afterExecute();
         }
         finally
         {
            // just so our button always gets enabled even if a listener
            // throws an exception.  This is just for debugging ease.
            setActive(false);
         }
      }

      public void publishError(E error)
      {
         try
         {
            afterError(error);
            errorChannel.publish(error);
            if (alwaysEvents != null)
            {
               alwaysEvents.fireError(error);
            }
            if (oneTimeEvents != null)
            {
              oneTimeEvents.fireError(error);
            }
            afterExecute();
         }
         finally
         {
            // just so our button always gets enabled even if a listener
            // throws an exception.  This is just for debugging ease.
            setActive(false);
         }
      }
   }

   protected abstract void performActivity(ActivityCallback<R, E> callback);

}
