package com.pietschy.gwt.pectin.client.bean.runner;

import com.pietschy.gwt.pectin.client.bean.AbstractBeanModelProvider;
import com.pietschy.gwt.pectin.client.bean.ResultCallback;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 29, 2010
 * Time: 12:08:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractRunner<T>
{

   protected AbstractBeanModelProvider<T> provider;
   protected ResultCallback callback;

   public AbstractRunner(AbstractBeanModelProvider<T> provider, ResultCallback callback)
   {
      this.provider = provider;
      this.callback = callback;
   }
}
