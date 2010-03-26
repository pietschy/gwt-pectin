package com.pietschy.gwt.pectin.client.activity;

import com.pietschy.gwt.pectin.client.value.ValueHolder;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Mar 25, 2010
 * Time: 2:42:09 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTemporalActivity extends AbstractActivity implements TemporalActivity
{
   protected ValueHolder<Boolean> active = new ValueHolder<Boolean>(false);

   public ValueModel<Boolean> active()
   {
      return active;
   }

   /**
    * This is only for use in base type classes, not user end type work.
    * @param active <code>true</code> if the activity is active, <code>false</code> otherwise.
    */
   void setActive(boolean active)
   {
      this.active.setValue(active);
   }
}
