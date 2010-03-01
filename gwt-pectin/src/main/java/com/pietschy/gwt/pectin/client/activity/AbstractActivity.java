package com.pietschy.gwt.pectin.client.activity;

import com.pietschy.gwt.pectin.client.value.ValueHolder;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 28, 2010
 * Time: 10:06:31 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractActivity implements Activity
{
   private ValueHolder<Boolean> enabledModel = new ValueHolder<Boolean>(true);

   public Activity intercept(Interceptor interceptor)
   {
      return new InterceptedActivity(this, interceptor);
   }

   public ValueModel<Boolean> isEnabled()
   {
      return enabledModel;
   }

   protected void setEnabledInternal(boolean enabled)
   {
      enabledModel.setValue(enabled);
   }

   protected boolean isEnabledInternal()
   {
      return enabledModel.getValue();
   }

}
