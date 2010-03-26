package com.pietschy.gwt.pectin.client.activity;

import com.pietschy.gwt.pectin.client.value.DelegatingValueModel;
import com.pietschy.gwt.pectin.client.value.ValueModel;

import static com.pietschy.gwt.pectin.client.condition.Conditions.is;
import static com.pietschy.gwt.pectin.client.condition.Conditions.isNot;

/**
 * DelegatingActivity delegate their behaviour to another activity.  This is useful when different activity
 * implementations are used at runtime.  DelegatingActivity also supports additional constraints placed on
 * it's enabled state.  Thus it's possible to have a delegate disable itself even when it's delegate is
 * enabled.  The DelegatingActivity will never be enabled if it's delete is disabled.
 */
public class DelegatingActivity implements TemporalActivity
{
   // create our delegating models all with default values of false.
   private DelegatingValueModel<Boolean> delegateActive = new DelegatingValueModel<Boolean>(false);
   private DelegatingValueModel<Boolean> delegateEnabled = new DelegatingValueModel<Boolean>(false);
   private DelegatingValueModel<Boolean> userEnabled = new DelegatingValueModel<Boolean>(false);

   private ValueModel<Boolean> enabled = is(delegateEnabled).and(userEnabled);

   private Activity delegate;


   public void execute()
   {
      if (delegate == null)
      {
         throw new NullPointerException("delegate is null");
      }
      delegate.execute();
   }

   public ValueModel<Boolean> active()
   {
      return delegateActive;
   }

   public ValueModel<Boolean> enabled()
   {
      return enabled;
   }

   /**
    * Allows the delegate to add additional constraints to enable/disable the delegate.  This is combined with the
    * state of the delegate.  I.e. This activity will only be enabled if both the delegate is enabled and the
    * specified condition is met.
    *
    * @param enabled the additional condition.
    */
   public void enableWhen(ValueModel<Boolean> enabled)
   {
      userEnabled.setDelegate(enabled);
   }

   /**
    * Allows the delegate to add additional constraints to enable/disable the delegate.  This is combined with the
    * state of the delegate.  I.e. This activity will only be enabled if both the delegate is enabled and the
    * specified condition is met.
    *
    * @param disabled the additional condition.
    */
   public void disableWhen(ValueModel<Boolean> disabled)
   {
      userEnabled.setDelegate(isNot(disabled));
   }

   public void setDelegate(Activity delegate)
   {
      this.delegate = delegate;
      delegateEnabled.setDelegate(delegate != null ? delegate.enabled() : null);
      if (delegate instanceof TemporalActivity)
      {
         delegateActive.setDelegate(((TemporalActivity) delegate).active());
      }
      else
      {
         delegateActive.setDelegate(null);
      }
   }
}
