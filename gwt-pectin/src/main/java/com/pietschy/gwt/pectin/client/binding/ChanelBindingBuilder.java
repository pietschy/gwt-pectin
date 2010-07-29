package com.pietschy.gwt.pectin.client.binding;

import com.google.gwt.user.client.ui.HasValue;
import com.pietschy.gwt.pectin.client.channel.Channel;
import com.pietschy.gwt.pectin.client.channel.Destination;
import com.pietschy.gwt.pectin.client.format.DisplayFormat;
import com.pietschy.gwt.pectin.client.function.Function;
import com.pietschy.gwt.pectin.client.value.ValueTarget;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Mar 25, 2010
* Time: 12:17:59 PM
* To change this template use File | Settings | File Templates.
*/
public class ChanelBindingBuilder<T>
{
   private Channel<T> channel;
   private AbstractBindingContainer binder;

   public ChanelBindingBuilder(AbstractBindingContainer binder, Channel<T> channel)
   {
      this.binder = binder;
      this.channel = channel;
   }

   public void to(Destination<? super T> destination)
   {
      binder.registerDisposable(channel.sendTo(destination));
   }

   public void to(ValueTarget<? super T> destination)
   {
      binder.registerDisposable(channel.sendTo(destination));
   }

   public void to(HasValue<? super T> destination)
   {
      binder.registerDisposable(channel.sendTo(destination));
   }

   public FormattedChannelBindingBuilder formattedWith(DisplayFormat<? super T> format)
   {
      return new FormattedChannelBindingBuilder<T>(binder, channel, format);
   }

   public FormattedChannelBindingBuilder formattedWith(final Function<String, ? super T> format)
   {
      return new FormattedChannelBindingBuilder<T>(binder, channel, new DisplayFormat<T>()
      {
         public String format(T value)
         {
            return format.compute(value);
         }
      });
   }
}
