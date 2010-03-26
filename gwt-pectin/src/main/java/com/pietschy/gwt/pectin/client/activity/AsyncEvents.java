package com.pietschy.gwt.pectin.client.activity;

import com.google.gwt.user.client.Command;
import com.pietschy.gwt.pectin.client.binding.Disposable;
import com.pietschy.gwt.pectin.client.channel.Destination;
import com.pietschy.gwt.pectin.client.channel.Publisher;
import com.pietschy.gwt.pectin.client.value.MutableValue;

/**
 * This class provides methods to hooking into the events of an {@link com.pietschy.gwt.pectin.client.activity.AsyncActivity}.
 */
public interface AsyncEvents<R, E>
{
   Disposable sendResultTo(Destination<? super R> destination);

   Disposable sendResultTo(MutableValue<? super R> value);

   Disposable sendResultTo(Publisher<? super R> publisher);

   Disposable sendResultTo(ParameterisedCommand<? super R> publisher);

   Disposable invokeOnSuccess(Command command);

   Disposable sendErrorTo(Destination<? super E> destination);

   Disposable sendErrorTo(MutableValue<? super E> value);

   Disposable sendErrorTo(Publisher<? super E> publisher);

   Disposable invokeOnError(Command command);

   Disposable sendErrorTo(ParameterisedCommand<? super E> parameterisedCommand);

   Disposable invokeOnStart(Command command);

   <T> SendToBuilder<T> onStartSend(T value);

   <T> SendToBuilder<T> onSuccessSend(T value);

   <T> SendToBuilder<T> onErrorSend(T value);

   /**
    * Creates an instance of {@link com.pietschy.gwt.pectin.client.activity.AsyncEvents} that can be
    * disposed of as a whole rather than keeping references to each of the Disposables returned during
    * the use of the API.  Once disposed the disposable instance will no longer be referenced by the
    * activity.
    *
    * @return a new instance that can be disposed of as a whole.
    */
   DisposableAsyncEvents<R, E> disposableInstance();

}
