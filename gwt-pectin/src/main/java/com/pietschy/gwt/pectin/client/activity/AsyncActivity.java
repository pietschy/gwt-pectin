package com.pietschy.gwt.pectin.client.activity;

import com.google.gwt.user.client.Command;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 23, 2010
 * Time: 11:22:45 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AsyncActivity<R,E> extends Activity
{

   ValueModel<Boolean> isActive();

   AsyncActivity<R, E> intercept(Interceptor interceptor);

   Channel<R> getResults();

   Channel<E> getErrors();

   ChannelRegistration onSuccessExecute(Command command);

   ChannelRegistration onSuccessCall(Callback<R> callback);

   ChannelRegistration onErrorExecute(Command command);

   ChannelRegistration onErrorCall(Callback<E> callback);
}
