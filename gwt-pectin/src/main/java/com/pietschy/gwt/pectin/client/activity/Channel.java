package com.pietschy.gwt.pectin.client.activity;

import com.pietschy.gwt.pectin.client.value.MutableValue;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 23, 2010
 * Time: 2:34:48 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Channel<T>
{
   ChannelRegistration sendTo(Destination<T> destination);

   ChannelRegistration sendTo(MutableValue<T> destination);
}
