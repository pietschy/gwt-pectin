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
   private WidgetBinder widgetBinder;
   private UiCommand command;

   public UiCommandEventsBindingBuilder(WidgetBinder widgetBinder, UiCommand command)
   {
      this.widgetBinder = widgetBinder;
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
      widgetBinder.registerDisposable(disposable);
   }

   UiCommand getCommand()
   {
      return command;
   }
}
