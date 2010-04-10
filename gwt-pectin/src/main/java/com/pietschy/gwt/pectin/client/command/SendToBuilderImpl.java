package com.pietschy.gwt.pectin.client.command;

import com.pietschy.gwt.pectin.client.binding.Disposable;
import com.pietschy.gwt.pectin.client.channel.Channel;
import com.pietschy.gwt.pectin.client.channel.Destination;
import com.pietschy.gwt.pectin.client.channel.Publisher;
import com.pietschy.gwt.pectin.client.value.MutableValue;

/**
* Created by IntelliJ IDEA.
* User: andrew
* Date: Mar 6, 2010
* Time: 1:01:25 PM
* To change this template use File | Settings | File Templates.
*/
public class SendToBuilderImpl<T> implements SendToBuilder<T>
{
   private Channel<?> trigger;
   private T value;

   SendToBuilderImpl(Channel<?> trigger, T value)
   {
      this.trigger = trigger;
      this.value = value;
   }

   public Disposable to(final Destination<? super T> destination)
   {
      return trigger.sendTo(new Destination<Object>()
      {
         public void receive(Object ignore)
         {
            destination.receive(value);
         }
      });
   }

   public Disposable to(final Publisher<? super T> destination)
   {
      return trigger.sendTo(new Destination<Object>()
      {
         public void receive(Object ignore)
         {
            destination.publish(value);
         }
      });
   }

   public Disposable to(final MutableValue<? super T> destination)
   {
      return trigger.sendTo(new Destination<Object>()
      {
         public void receive(Object ignore)
         {
            destination.setValue(value);
         }
      });
   }

   public Disposable to(final ParameterisedCommand<? super T> destination)
   {
      return trigger.sendTo(new Destination<Object>()
      {
         public void receive(Object ignore)
         {
            destination.execute(value);
         }
      });
   }
}
