package com.pietschy.gwt.pectin.client.command;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;

/**
 * The UiCommand equivalent of IncrementalCommand.  The command takes care of performing the work
 * using DeferredCommand so callers can invoke execute() directly.  Subclasses implement {@link #doIncrementalWork()}
 * to perform the units of work.
 * <p/>
 * Subclasses can override {@link #onStarting()} and {@link #afterFinish()} to perform additional work.
 * <p/>
 * Subclasses can also override {@link #onReEntrantExecution()} if they want to allow calls to execute while the
 * command is active.  By default the method will throw a
 * {@link com.pietschy.gwt.pectin.client.command.ReEntrantExecutionException}.
 */
public abstract class IncrementalUiCommand extends AbstractTemporalUiCommand
{
   private EventSupport eventSupport = new EventSupport();

   public Events always()
   {
      return eventSupport.always();
   }

   public Events onNextCall()
   {
      return eventSupport.onNextCall();
   }

   @Override
   protected void startExecution(final Context context)
   {
      final EventSupport.Trigger events = eventSupport.prepareEvents();

      runWithInterceptors(new Command()
      {
         public void execute()
         {
            context.notifyStarted();
            events.fireStarted();

            DeferredCommand.addCommand(new IncrementalCommand()
            {
               public boolean execute()
               {
                  boolean keepGoing = doIncrementalWork();

                  if (!keepGoing)
                  {
                     events.fireFinished();
                     context.notifyFinished();
                  }

                  return keepGoing;
               }
            });
         }
      });

   }

   public abstract boolean doIncrementalWork();


}
