package com.pietschy.gwt.pectin.client.activity;

import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;

/**
 * The activity equivalent of IncrementalCommand.  The Activity takes care of performing the work
 * using DeferredCommand so callers can invoke execute() directly.  Subclasses implement {@link #doIncrementalWork()}
 * to perform the units of work.
 * <p>
 * Subclasses can override {@link #onStarting()} and {@link #afterFinished()} to perform additional work.
 * <p>
 * Subclasses can also override {@link #onReEntrantExecution()} if they want to allow calls to execute while the
 * activity is active.  By default the method will throw a
 * {@link com.pietschy.gwt.pectin.client.activity.ReEntrantExecutionException}.
 *
 */
public abstract class AbstractIncrementalActivity extends AbstractTemporalActivity
{
   public void execute()
   {
      if (!enabled().getValue())
      {
         throw new IllegalStateException("execute called while disabled");
      }

      if (active().getValue())
      {
         // the default implementation throws an {@link ReEntrantExecutionException}.
         onReEntrantExecution();
      }

      setActive(true);
      onStarting();

      DeferredCommand.addCommand(new IncrementalCommand()
      {
         public boolean execute()
         {
            boolean keepGoing = doIncrementalWork();
            if (!keepGoing)
            {
               setActive(false);
               afterFinished();
            }
            return keepGoing;
         }
      });
   }

   /**
    * Hook for subclasses to perform work just before the activity starts.
    */
   protected void onStarting(){}


   public abstract boolean doIncrementalWork();

   /**
    * Hook for subclasses to perform work after the activity ends.
    */
   protected void afterFinished(){}

   /**
    * Called when ever execute is invoke while active == true;  This is just an entry point for experimentation
    * if I ever want to see if queuing events is useful or insane.
    */
   protected void onReEntrantExecution()
   {
      throw new ReEntrantExecutionException();
   }
}
