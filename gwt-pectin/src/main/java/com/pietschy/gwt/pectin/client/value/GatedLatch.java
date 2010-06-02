package com.pietschy.gwt.pectin.client.value;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

/**
 * A value model that tracks the value of another model until a trigger activates, at which
 * point it latches to the last known value until the trigger is released.
 */
public class GatedLatch<T> extends AbstractValueModel<T>
{
   // the value we're latching to
   private ValueModel<Boolean> latchTrigger;
   // the source model we're latching on
   private ValueModel<T> source;
   private T latchedValue;

   protected GatedLatch(ValueModel<T> source, ValueModel<Boolean> latchTrigger)
   {
      this.source = source;
      this.latchTrigger = latchTrigger;
      this.source.addValueChangeHandler(new ValueChangeHandler<T>()
      {
         public void onValueChange(ValueChangeEvent<T> event)
         {
            onSourceChange();
         }
      });
      this.latchTrigger.addValueChangeHandler(new ValueChangeHandler<Boolean>()
      {
         public void onValueChange(ValueChangeEvent<Boolean> event)
         {
            onTriggerChange();
         }
      });

      onTriggerChange();
   }

   public T getValue()
   {
      return isLatchActive() ? latchedValue : source.getValue();
   }

   private void onTriggerChange()
   {
      if (isLatchActive())
      {
         latchedValue = source.getValue();
      }
      else
      {
         fireValueChangeEvent(latchedValue, source.getValue());
      }
   }

   private void onSourceChange()
   {
      if (!isLatchActive())
      {
         fireValueChangeEvent(getValue());
      }
   }

   private Boolean isLatchActive()
   {
      Boolean value = latchTrigger.getValue();
      return value != null && value;
   }
}