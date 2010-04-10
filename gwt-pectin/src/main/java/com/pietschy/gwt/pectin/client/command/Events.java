package com.pietschy.gwt.pectin.client.command;

import com.google.gwt.user.client.Command;
import com.pietschy.gwt.pectin.client.binding.Disposable;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Apr 1, 2010
 * Time: 1:33:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Events
{
   Disposable onStartInvoke(Command command);

   Disposable onFinishInvoke(Command command);

   <T> SendToBuilder<T> onStartSend(T value);

   <T> SendToBuilder<T> onFinishSend(T value);

   Disposable sendAllEventsTo(LifeCycleCallback callback);

}
