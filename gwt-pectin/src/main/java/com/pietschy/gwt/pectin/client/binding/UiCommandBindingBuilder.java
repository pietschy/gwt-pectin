package com.pietschy.gwt.pectin.client.binding;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FocusWidget;
import com.pietschy.gwt.pectin.client.command.UiCommand;
import com.pietschy.gwt.pectin.client.metadata.HasEnabled;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Mar 25, 2010
* Time: 12:17:35 PM
* To change this template use File | Settings | File Templates.
*/
public class UiCommandBindingBuilder
{
   private UiCommand uiCommand;
   private WidgetBinder widgetBinder;

   public UiCommandBindingBuilder(WidgetBinder widgetBinder, UiCommand uiCommand)
   {
      this.widgetBinder = widgetBinder;
      this.uiCommand = uiCommand;
   }

   public void to(final HasClickHandlers button)
   {
      HandlerRegistration registration = button.addClickHandler(new ClickHandler()
      {
         public void onClick(ClickEvent event)
         {
            uiCommand.execute();
         }
      });

      widgetBinder.registerHandler(registration);

      bindEnabled(uiCommand, button);
   }

   private void bindEnabled(UiCommand uiCommand, HasClickHandlers button)
   {
      if (button instanceof FocusWidget)
      {
         widgetBinder.enable((FocusWidget) button).when(uiCommand.enabled());
      }
      else if (button instanceof HasEnabled)
      {
         widgetBinder.enable((HasEnabled) button).when(uiCommand.enabled());
      }
      else
      {
         throw new IllegalStateException("Button doesn't extend FocusWidget or implement HasEnabled");
      }
   }
}
