package com.pietschy.gwt.pectin.client.activity;

import com.google.gwt.user.client.Command;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 27, 2010
 * Time: 2:30:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Executor extends Command
{
   Executor intercept(Interceptor interceptor);
   ValueModel<Boolean> isEnabled();
   void detach();
   void attach();
}
