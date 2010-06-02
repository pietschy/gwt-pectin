package com.pietschy.gwt.pectin.client.binding;

import com.pietschy.gwt.pectin.client.command.LifeCycleCallback;
import com.pietschy.gwt.pectin.client.command.UiCommand;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Apr 4, 2010
* Time: 10:58:12 AM
* To change this template use File | Settings | File Templates.
*/
public class UiCommandEventsBindingBuilder
{
   private AbstractBindingContainer binder;
   private UiCommand command;

   public UiCommandEventsBindingBuilder(AbstractBindingContainer binder, UiCommand command)
   {
      this.binder = binder;
      this.command = command;
   }

   public void to(LifeCycleCallback listener)
   {
      registerDisposable(
         command.always().sendAllEventsTo(listener)
      );
   }

   void registerDisposable(Disposable disposable)
   {
      binder.registerDisposable(disposable);
   }

   UiCommand getCommand()
   {
      return command;
   }
}
