package com.pietschy.gwt.pectin.client.command;

import com.google.gwt.user.client.Command;
import com.pietschy.gwt.pectin.client.binding.Disposable;
import com.pietschy.gwt.pectin.client.channel.Destination;
import com.pietschy.gwt.pectin.client.channel.Publisher;
import com.pietschy.gwt.pectin.client.value.HasValueSetter;

/**
 * This class provides methods to hooking into the events of an {@link AsyncUiCommand}.
 */
public interface AsyncEvents<R, E> extends Events
{
   Disposable sendResultTo(Destination<? super R> destination);

   Disposable sendResultTo(HasValueSetter<? super R> destination);

   Disposable sendResultTo(Publisher<? super R> publisher);

   Disposable sendErrorTo(Destination<? super E> destination);

   Disposable sendErrorTo(HasValueSetter<? super E> destination);

   Disposable sendErrorTo(Publisher<? super E> publisher);

   Disposable onSuccessInvoke(Command command);

   Disposable onSuccessInvoke(ParameterisedCommand<? super R> command);

   Disposable onErrorInvoke(Command command);

   Disposable onErrorInvoke(ParameterisedCommand<? super E> command);

   <T> SendToBuilder<T> onSuccessSend(T value);

   <T> SendToBuilder<T> onErrorSend(T value);

   Disposable sendAllEventsTo(AsyncLifeCycleCallback<R,E> callback);

}
