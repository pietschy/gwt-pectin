package com.pietschy.gwt.pectin.client.binding;

import com.google.gwt.user.client.ui.HasValue;
import com.pietschy.gwt.pectin.client.channel.Channel;
import com.pietschy.gwt.pectin.client.channel.Destination;
import com.pietschy.gwt.pectin.client.format.DisplayFormat;
import com.pietschy.gwt.pectin.client.function.Function;
import com.pietschy.gwt.pectin.client.value.ValueTarget;

/**
 * This class simple delegates to {@link com.pietschy.gwt.pectin.client.binding.ChannelBindingBuilder}.  It
 * exists purely to improve the readability of the API.
 */
public class UiCommandChanelBindingBuilder<T>
{
   private ChanelBindingBuilder<T> delegate;

   public UiCommandChanelBindingBuilder(AbstractBindingContainer widgetBinder, Channel<T> channel)
   {
      delegate = new ChanelBindingBuilder<T>(widgetBinder, channel);
   }

   public void using(Destination<T> destination)
   {
      delegate.to(destination);
   }

   public void using(ValueTarget<T> destination)
   {
      delegate.to(destination);
   }

   public void using(HasValue<T> destination)
   {
      delegate.to(destination);
   }

   public UiCommandFormattedChannelBindingBuilder formattedWith(DisplayFormat<? super T> format)
   {
      return new UiCommandFormattedChannelBindingBuilder(delegate.formattedWith(format));
   }

   public UiCommandFormattedChannelBindingBuilder formattedWith(final Function<String, ? super T> format)
   {
      return new UiCommandFormattedChannelBindingBuilder(delegate.formattedWith(new DisplayFormat<T>()
      {
         public String format(T value)
         {
            return format.compute(value);
         }
      }));
   }
}