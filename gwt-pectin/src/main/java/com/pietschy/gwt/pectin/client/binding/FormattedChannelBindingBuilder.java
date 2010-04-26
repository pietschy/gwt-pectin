package com.pietschy.gwt.pectin.client.binding;

import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.pietschy.gwt.pectin.client.channel.Channel;
import com.pietschy.gwt.pectin.client.channel.Destination;
import com.pietschy.gwt.pectin.client.format.DisplayFormat;
import com.pietschy.gwt.pectin.client.value.HasValueSetter;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Mar 25, 2010
* Time: 12:18:10 PM
* To change this template use File | Settings | File Templates.
*/
public class FormattedChannelBindingBuilder<T>
{
   private Channel<T> channel;
   private DisplayFormat<? super T> format;
   private WidgetBinder widgetBinder;

   public FormattedChannelBindingBuilder(WidgetBinder widgetBinder, Channel<T> channel, DisplayFormat<? super T> format)
   {
      this.widgetBinder = widgetBinder;
      this.channel = channel;
      this.format = format;
   }

   public void to(final Destination<String> destination)
   {
      widgetBinder.registerDisposable(channel.sendTo(new Destination<T>()
      {
         public void receive(T value)
         {
            destination.receive(format.format(value));
         }
      }));
   }

   public void to(final HasValueSetter<String> destination)
   {
      widgetBinder.registerDisposable(channel.sendTo(new Destination<T>()
      {
         public void receive(T value)
         {
            destination.setValue(format.format(value));
         }
      }));
   }

   public void to(final HasText destination)
   {
      widgetBinder.registerDisposable(channel.sendTo(new Destination<T>()
      {
         public void receive(T value)
         {
            destination.setText(format.format(value));
         }
      }));
   }

   public void toHtml(final HasHTML destination)
   {
      widgetBinder.registerDisposable(channel.sendTo(new Destination<T>()
      {
         public void receive(T value)
         {
            destination.setHTML(format.format(value));
         }
      }));
   }

   public void to(final HasValue<String> destination)
   {
      widgetBinder.registerDisposable(channel.sendTo(new Destination<T>()
      {
         public void receive(T value)
         {
            destination.setValue(format.format(value));
         }
      }));
   }
}
