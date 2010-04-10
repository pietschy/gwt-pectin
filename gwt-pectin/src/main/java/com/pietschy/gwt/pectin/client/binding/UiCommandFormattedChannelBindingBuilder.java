package com.pietschy.gwt.pectin.client.binding;

import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.pietschy.gwt.pectin.client.channel.Destination;
import com.pietschy.gwt.pectin.client.value.MutableValue;

/**
 * This class simple delegates to {@link com.pietschy.gwt.pectin.client.binding.FormattedChannelBindingBuilder}.  It
 * exists purely to improve the readability of the API.
*/
public class UiCommandFormattedChannelBindingBuilder
{
   private FormattedChannelBindingBuilder delegate;

   public UiCommandFormattedChannelBindingBuilder(FormattedChannelBindingBuilder delegate)
   {
      this.delegate = delegate;
   }

   public void using(Destination<String> destination)
   {
      delegate.to(destination);
   }

   public void using(MutableValue<String> destination)
   {
      delegate.to(destination);
   }

   public void using(HasText destination)
   {
      delegate.to(destination);
   }

   public void using(HasValue<String> destination)
   {
      delegate.to(destination);
   }

   public void usingHtml(HasHTML destination)
   {
      delegate.toHtml(destination);
   }
}