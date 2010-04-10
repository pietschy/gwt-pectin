package com.pietschy.gwt.pectin.client.binding;

import com.pietschy.gwt.pectin.client.command.AsyncLifeCycleCallback;
import com.pietschy.gwt.pectin.client.command.AsyncUiCommand;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Apr 4, 2010
* Time: 10:57:58 AM
* To change this template use File | Settings | File Templates.
*/
public class UiCommandAsyncEventsBindingBuilder<R,E> extends UiCommandEventsBindingBuilder
{
   UiCommandAsyncEventsBindingBuilder(WidgetBinder widgetBinder, AsyncUiCommand<R,E> command)
   {
      super(widgetBinder, command);

   }

   @SuppressWarnings("unchecked")
   public void to(AsyncLifeCycleCallback<R,E> callback)
   {
      registerDisposable(
         ((AsyncUiCommand<R, E>) getCommand()).always().sendAllEventsTo(callback)
      );
   }
}
