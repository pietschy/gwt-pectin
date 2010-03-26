package com.pietschy.gwt.pectin.client.binding;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FocusWidget;
import com.pietschy.gwt.pectin.client.activity.Activity;
import com.pietschy.gwt.pectin.client.metadata.HasEnabled;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Mar 25, 2010
* Time: 12:17:35 PM
* To change this template use File | Settings | File Templates.
*/
public class ActivityBindingBuilder
{
   private Activity activity;
   private WidgetBinder widgetBinder;

   public ActivityBindingBuilder(WidgetBinder widgetBinder, Activity activity)
   {
      this.widgetBinder = widgetBinder;
      this.activity = activity;
   }

   public void to(final HasClickHandlers button)
   {
      HandlerRegistration registration = button.addClickHandler(new ClickHandler()
      {
         public void onClick(ClickEvent event)
         {
            activity.execute();
         }
      });

      widgetBinder.registerHandler(registration);

      bindEnabled(activity, button);
   }

   private void bindEnabled(Activity activity, HasClickHandlers button)
   {
      if (button instanceof FocusWidget)
      {
         widgetBinder.enable((FocusWidget) button).when(activity.enabled());
      }
      else if (button instanceof HasEnabled)
      {
         widgetBinder.enable((HasEnabled) button).when(activity.enabled());
      }
   }
}
