package com.pietschy.gwt.pectin.client.command;

import com.pietschy.gwt.pectin.client.binding.Disposable;
import com.pietschy.gwt.pectin.client.channel.Destination;
import com.pietschy.gwt.pectin.client.channel.Publisher;
import com.pietschy.gwt.pectin.client.value.HasValueSetter;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Mar 6, 2010
 * Time: 1:08:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SendToBuilder<T>
{
   Disposable to(Destination<? super T> destination);

   Disposable to(Publisher<? super T> destination);

   Disposable to(HasValueSetter<? super T> destination);

   Disposable to(ParameterisedCommand<? super T> destination);
}
