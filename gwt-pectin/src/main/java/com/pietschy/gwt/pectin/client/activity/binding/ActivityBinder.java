package com.pietschy.gwt.pectin.client.activity.binding;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.pietschy.gwt.pectin.client.activity.Activity;
import com.pietschy.gwt.pectin.client.activity.AsyncActivity;
import com.pietschy.gwt.pectin.client.activity.Channel;
import com.pietschy.gwt.pectin.client.activity.Destination;
import com.pietschy.gwt.pectin.client.binding.AbstractBinder;
import com.pietschy.gwt.pectin.client.format.DisplayFormat;
import com.pietschy.gwt.pectin.client.value.MutableValue;

/**
 *
 */
public class ActivityBinder extends AbstractBinder
{

   public Binder bind(Activity activity)
   {
      return new Binder(activity);
   }

   public <R,E> AsyncBinder<R,E> bind(AsyncActivity<R, E> activity)
   {
      return new AsyncBinder<R,E>(activity);
   }

   public <R> ChannelBinder<R> sendResultOf(AsyncActivity<R, ?> activity)
   {
      return new ChannelBinder<R>(activity.getResults());
   }

   public <E> ChannelBinder<E> sendErrorOf(AsyncActivity<?, E> activity)
   {
      return new ChannelBinder<E>(activity.getErrors());
   }

   public <T> ChannelBinder<T> send(Channel<T> channel)
   {
      return new ChannelBinder<T>(channel);
   }

//   @SuppressWarnings("unchecked")
//   private <R,E> Events<R, E> getEvents(AsyncActivity<R, E> activity)
//   {
//      Events<R, E> events = (Events<R,E>) subscriptions.get(activity);
//      if (events == null)
//      {
//         events = activity.subscribe();
//         subscriptions.put(activity, events);
//      }
//
//      return events;
//   }

   public class Binder
   {
      private Activity activity;

      public Binder(Activity activity)
      {
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
         registerHandler(registration);
      }
   }

   public class AsyncBinder<R,E>
   {
      private AsyncActivity<R,E> activity;

      public AsyncBinder(AsyncActivity<R,E> activity)
      {
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
         registerHandler(registration);
      }
   }

   public class ChannelBinder<T>
   {
      private Channel<T> channel;

      public ChannelBinder(Channel<T> channel)
      {
         this.channel = channel;
      }

      public void to(Destination<T> destination)
      {
         registerDisposable(channel.sendTo(destination));
      }

      public void to(MutableValue<T> destination)
      {
         registerDisposable(channel.sendTo(destination));
      }

      public FormattedChannelBinder formattedWith(DisplayFormat<? super T> format)
      {
         return new FormattedChannelBinder<T>(channel, format);
      }
   }

   public class FormattedChannelBinder<T>
   {
      private Channel<T> channel;
      private DisplayFormat<? super T> format;

      public FormattedChannelBinder(Channel<T> channel, DisplayFormat<? super T> format)
      {
         this.channel = channel;
         this.format = format;
      }

      public void to(final Destination<String> destination)
      {
         registerDisposable(channel.sendTo(new Destination<T>()
         {
            public void receive(T value)
            {
               destination.receive(format.format(value));
            }
         }));
      }

      public void to(final MutableValue<String> destination)
      {
         registerDisposable(channel.sendTo(new Destination<T>()
         {
            public void receive(T value)
            {
               destination.setValue(format.format(value));
            }
         }));
      }

      public void to(final HasText destination)
      {
         registerDisposable(channel.sendTo(new Destination<T>()
         {
            public void receive(T value)
            {
               destination.setText(format.format(value));
            }
         }));
      }

      public void toHtml(final HasHTML destination)
      {
         registerDisposable(channel.sendTo(new Destination<T>()
         {
            public void receive(T value)
            {
               destination.setHTML(format.format(value));
            }
         }));
      }

      public void to(final HasValue<String> destination)
      {
         registerDisposable(channel.sendTo(new Destination<T>()
         {
            public void receive(T value)
            {
               destination.setValue(format.format(value));
            }
         }));
      }
   }
}
