package com.pietschy.gwt.pectin.client.activity;

import com.google.gwt.user.client.Command;
import com.pietschy.gwt.pectin.client.binding.Disposable;
import com.pietschy.gwt.pectin.client.channel.Channel;
import com.pietschy.gwt.pectin.client.channel.DefaultChannel;
import com.pietschy.gwt.pectin.client.channel.Destination;
import com.pietschy.gwt.pectin.client.channel.Publisher;
import com.pietschy.gwt.pectin.client.value.MutableValue;

/**
 * AbstractAsyncEvents provides a the default implementation of {@link com.pietschy.gwt.pectin.client.activity.AsyncEvents}.
 */
public abstract class AbstractAsyncEvents<R, E> implements AsyncEvents<R, E>
{
   private DefaultChannel<Void> startChannel = new DefaultChannel<Void>();
   private DefaultChannel<R> resultChannel = new DefaultChannel<R>();
   private DefaultChannel<E> errorChannel = new DefaultChannel<E>();

   protected Channel<Void> getStartChannel()
   {
      return startChannel;
   }

   protected Channel<E> getErrorChannel()
   {
      return errorChannel;
   }

   protected Channel<R> getResultChannel()
   {
      return resultChannel;
   }

   public Disposable invokeOnStart(final Command command)
   {
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

   public <T> SendToBuilder<T> onSuccessSend(final T value)
   {
      return new SendToBuilderImpl<T>(resultChannel, value);
   }

   public <T> SendToBuilder<T> onErrorSend(final T value)
   {
      return new SendToBuilderImpl<T>(errorChannel, value);
   }

   public Disposable sendResultTo(Destination<? super R> destination)
   {
      return getResultChannel().sendTo(destination);
   }

   public Disposable sendResultTo(MutableValue<? super R> mutableValue)
   {
      return getResultChannel().sendTo(mutableValue);
   }

   public Disposable sendResultTo(Publisher<? super R> publisher)
   {
      return getResultChannel().sendTo(publisher);
   }

   public Disposable invokeOnSuccess(final Command command)
   {
      return getResultChannel().sendTo(new Destination<R>()
      {
         public void receive(R value)
         {
            command.execute();
         }
      });
   }

   public Disposable sendResultTo(final ParameterisedCommand<? super R> parameterisedCommand)
   {
      return getResultChannel().sendTo(parameterisedCommand);
   }

   public Disposable sendErrorTo(Destination<? super E> destination)
   {
      return getErrorChannel().sendTo(destination);
   }

   public Disposable sendErrorTo(MutableValue<? super E> mutableValue)
   {
      return getErrorChannel().sendTo(mutableValue);
   }

   public Disposable sendErrorTo(Publisher<? super E> publisher)
   {
      return getErrorChannel().sendTo(publisher);
   }

   public Disposable sendErrorTo(final ParameterisedCommand<? super E> parameterisedCommand)
   {
      return getErrorChannel().sendTo(parameterisedCommand);
   }

   public Disposable invokeOnError(final Command command)
   {
      return getErrorChannel().sendTo(new Destination<E>()
      {
         public void receive(E value)
         {
            command.execute();
         }
      });
   }

   public DisposableAsyncEvents<R, E> disposableInstance()
   {
      return new DisposableAsyncEventsImpl<R, E>(getStartChannel(), getResultChannel(), getErrorChannel());  
   }
}
