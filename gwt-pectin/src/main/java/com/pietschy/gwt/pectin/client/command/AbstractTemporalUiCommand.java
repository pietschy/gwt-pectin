package com.pietschy.gwt.pectin.client.command;

import com.pietschy.gwt.pectin.client.value.ValueHolder;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Mar 25, 2010
 * Time: 2:42:09 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTemporalUiCommand extends UiCommandSupport implements TemporalUiCommand
{
   private ValueHolder<Boolean> active = new ValueHolder<Boolean>(false);

   public ValueModel<Boolean> active()
   {
      return active;
   }

   /**
    * This is only for use in base type classes, not user end type work.
    *
    * @param active <code>true</code> if the command is active, <code>false</code> otherwise.
    */
   void setActive(boolean active)
   {
      this.active.setValue(active);
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
         onReEntrantExecution();
      }

      startExecution(new Context());
   }

   /**
    * Hook for subclasses to perform work just before the command starts.
    */
   protected void onStarting()
   {
   }

   /**
    * Hook for subclasses to perform work after the command ends.
    */
   protected void afterFinish()
   {
   }


   protected abstract void startExecution(Context context);

   /**
    * Called when ever execute is invoke while active == true;  This is just an entry point for experimentation
    * if I ever want to see if queuing events is useful or insane.
    */
   protected void onReEntrantExecution()
   {
      throw new ReEntrantExecutionException();
   }

   public class Context
   {
      public void notifyStarted()
      {
         setActive(true);
         onStarting();
      }

      public void notifyFinished()
      {
         afterFinish();
         setActive(false);
      }
   }
}
