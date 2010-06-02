package com.pietschy.gwt.pectin.client.binding;

import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.pietschy.gwt.pectin.client.channel.Channel;
import com.pietschy.gwt.pectin.client.channel.Destination;
import com.pietschy.gwt.pectin.client.format.DisplayFormat;
import com.pietschy.gwt.pectin.client.value.ValueTarget;

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
   private AbstractBindingContainer binder;

   public FormattedChannelBindingBuilder(AbstractBindingContainer binder, Channel<T> channel, DisplayFormat<? super T> format)
   {
      this.binder = binder;
      this.channel = channel;
      this.format = format;
   }

   public void to(final Destination<String> destination)
   {
      binder.registerDisposable(channel.sendTo(new Destination<T>()
      {
         public void receive(T value)
         {
            destination.receive(format.format(value));
         }
      }));
   }

   public void to(final ValueTarget<String> destination)
   {
      binder.registerDisposable(channel.sendTo(new Destination<T>()
      {
         public void receive(T value)
         {
            destination.setValue(format.format(value));
         }
      }));
   }

   public void to(final HasText destination)
   {
      binder.registerDisposable(channel.sendTo(new Destination<T>()
      {
         public void receive(T value)
         {
            destination.setText(format.format(value));
         }
      }));
   }

   public void toHtml(final HasHTML destination)
   {
      binder.registerDisposable(channel.sendTo(new Destination<T>()
      {
         public void receive(T value)
         {
            destination.setHTML(format.format(value));
         }
      }));
   }

   public void to(final HasValue<String> destination)
   {
      binder.registerDisposable(channel.sendTo(new Destination<T>()
      {
         public void receive(T value)
         {
            destination.setValue(format.format(value));
         }
      }));
   }
}
