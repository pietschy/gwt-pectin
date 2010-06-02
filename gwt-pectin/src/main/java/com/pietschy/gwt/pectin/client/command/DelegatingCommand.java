package com.pietschy.gwt.pectin.client.command;

import com.google.gwt.user.client.Command;
import com.pietschy.gwt.pectin.client.binding.Disposable;

/**
 * A command that delegates to another instance.
 */
public class DelegatingCommand implements Command, Disposable
{
   private Object debugInfo;
   private Command delegate;

   /**
    * Creates a new delegating command.
    */
   public DelegatingCommand()
   {
   }

   /**
    * Creates a new delegating command.
    * @param delegate  the delegate to use.
    */
   public DelegatingCommand(Command delegate)
   {
      setDelegate(delegate);
   }

   public DelegatingCommand withDebugContext(Object debugInfo)
   {
      this.debugInfo = debugInfo;
      return this;
   }

   public void setDelegate(Command delegate)
   {
      this.delegate = delegate;
   }

   public void execute()
   {
      if (delegate == null)
      {
         throw new MissingDelegateException(debugInfo);
      }

      delegate.execute();
   }

   public void dispose()
   {
      delegate = null;
   }
}
