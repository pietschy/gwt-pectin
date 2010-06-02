package com.pietschy.gwt.pectin.client.command;

import com.google.gwt.user.client.Command;
import com.pietschy.gwt.pectin.client.binding.Disposable;
import com.pietschy.gwt.pectin.client.channel.DefaultChannel;
import com.pietschy.gwt.pectin.client.channel.Destination;
import com.pietschy.gwt.pectin.client.util.SubscriptionList;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Apr 1, 2010
 * Time: 1:38:00 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractEvents implements Events
{
   private SubscriptionList<LifeCycleCallback> callbacks = new SubscriptionList<LifeCycleCallback>();
   private DefaultChannel<Void> startChannel = new DefaultChannel<Void>();
   private DefaultChannel<Void> finishedChannel= new DefaultChannel<Void>();

   protected AbstractEvents()
   {
   }

   protected void fireStart()
   {
      startChannel.publish(null);
      visitSubscribers(new SubscriptionList.Visitor<LifeCycleCallback>()
      {
         public void visit(LifeCycleCallback subscriber)
         {
            subscriber.onStart();
         }
      });
   }

   protected void fireFinish()
   {
      finishedChannel.publish(null);
      visitSubscribers(new SubscriptionList.Visitor<LifeCycleCallback>()
      {
         public void visit(LifeCycleCallback subscriber)
         {
            subscriber.onFinish();
         }
      });
   }

   protected void visitSubscribers(SubscriptionList.Visitor<LifeCycleCallback> visitor)
   {
      if (visitor == null)
      {
         throw new NullPointerException("visitor is null");
      }
      callbacks.visitSubscribers(visitor);
   }

   public Disposable onStartInvoke(final Command command)
   {
      if (command == null)
      {
         throw new NullPointerException("command is null");
      }

      return startChannel.sendTo(new Destination<Void>()
      {
         public void receive(Void value)
         {
            command.execute();
         }
      });
   }

   public <T> SendToBuilder<T> onStartSend(final T value)
   {
      return new SendToBuilderImpl<T>(startChannel, value);
   }

   public Disposable onFinishInvoke(final Command command)
   {
      if (command == null)
      {
         throw new NullPointerException("command is null");
      }

      return finishedChannel.sendTo(new Destination<Void>()
      {
         public void receive(Void value)
         {
            command.execute();
         }
      });
   }

   public Disposable sendAllEventsTo(LifeCycleCallback callback)
   {
      if (callback == null)
      {
         throw new NullPointerException("callback is null");
      }

      return callbacks.subscribe(callback);
   }

   public <T> SendToBuilder<T> onFinishSend(final T value)
   {
      return new SendToBuilderImpl<T>(finishedChannel, value);
   }

}
