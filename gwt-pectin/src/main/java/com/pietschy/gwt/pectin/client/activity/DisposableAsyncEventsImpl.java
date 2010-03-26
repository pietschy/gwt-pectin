package com.pietschy.gwt.pectin.client.activity;

import com.pietschy.gwt.pectin.client.binding.GarbageCollector;
import com.pietschy.gwt.pectin.client.channel.Channel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Mar 21, 2010
 * Time: 3:20:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class DisposableAsyncEventsImpl<R, E> extends AbstractAsyncEvents<R, E> implements DisposableAsyncEvents<R, E>
{
   private GarbageCollector gc = new GarbageCollector();

   public DisposableAsyncEventsImpl(Channel<Void> startChannel, Channel<R> resultChannel, Channel<E> errorChannel)
   {
      gc.add(startChannel.sendTo(getStartChannel()));
      gc.add(resultChannel.sendTo(getResultChannel()));
      gc.add(errorChannel.sendTo(getErrorChannel()));
   }

   public void dispose()
   {
      gc.dispose();
   }
}
