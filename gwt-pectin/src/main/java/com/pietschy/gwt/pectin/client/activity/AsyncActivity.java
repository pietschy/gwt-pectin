package com.pietschy.gwt.pectin.client.activity;

import com.pietschy.gwt.pectin.client.channel.Channel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 23, 2010
 * Time: 11:22:45 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AsyncActivity<R,E> extends HasInterceptors, HasAsyncEvents<R,E>, TemporalActivity
{

   Channel<R> getResults();

   Channel<E> getErrors();

}
