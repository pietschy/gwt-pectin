package com.pietschy.gwt.pectin.client.command;

import com.google.gwt.user.client.Command;
import com.pietschy.gwt.pectin.client.binding.Disposable;

/**
 * Delegating command
 */
public class DelegatingCommand implements Command, Disposable
{
   private Command delegate;
   
   public DelegatingCommand()
   {
   }

   public DelegatingCommand(Command delegate)
   {
      setDelegate(delegate);
   }

   public void setDelegate(Command delegate)
   {
      this.delegate = delegate;
   }

   public void execute()
   {
      if (delegate == null)
      {
         throw new NullPointerException("delegate is null");
      }
      
      delegate.execute();
   }

   public void dispose()
   {
      delegate = null;
   }
}
