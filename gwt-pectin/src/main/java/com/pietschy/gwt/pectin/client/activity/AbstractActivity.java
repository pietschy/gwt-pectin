package com.pietschy.gwt.pectin.client.activity;

import com.pietschy.gwt.pectin.client.value.DelegatingValueModel;
import com.pietschy.gwt.pectin.client.value.ValueModel;

import static com.pietschy.gwt.pectin.client.condition.Conditions.isNot;

/**
 * AbstractActivity provides a support for managing enabled state.
 *
 * @see #enableWhen(com.pietschy.gwt.pectin.client.value.ValueModel)
 * @see #disableWhen(com.pietschy.gwt.pectin.client.value.ValueModel)
 */
public abstract class AbstractActivity implements Activity
{
   private DelegatingValueModel<Boolean> enabledModel = new DelegatingValueModel<Boolean>(true);

   public ValueModel<Boolean> enabled()
   {
      return enabledModel;
   }

   /**
    * Configures when this command should be enabled.  Multiple calls to this method will replace
    * the existing value with the new one.
    * <p/>
    * This activity well be referenced by the specified value model (think memory leak). So if the
    * specified model is long lived then you'll need to invoke enabledWhen(null) to clear the
    * reference.
    *
    * @param enabled a value model representing the enabled state of this activity or null to remove
    *                the enabledWhen condition.
    */
   public void enableWhen(ValueModel<Boolean> enabled)
   {
      enabledModel.setDelegate(enabled);
   }

   /**
    * Configures when this command should be disabled.  This method is equivalent to calling
    * {@link #enableWhen(com.pietschy.gwt.pectin.client.value.ValueModel) enableWhen(isNot(disabled))}.
    * <p/>
    * See {@link #enableWhen(com.pietschy.gwt.pectin.client.value.ValueModel)} for information on memory
    * leaks.
    *
    * @param enabled a value model representing the disabled state of this activity.
    */
   public void disableWhen(ValueModel<Boolean> disabled)
   {
      enableWhen(isNot(disabled));
   }
}
