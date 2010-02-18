package com.pietschy.gwt.pectin.client.value;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

/**
 * A simple ValueModel that will latch when it's source transitions from one state to another.  The latch
 * can be reset.
 */
public class Latch extends AbstractValueModel<Boolean>
{
   /**
    * Creates a latch that will latch on a transition of false to true.
    *
    * @param source the source model.
    * @return a new latch instance.
    */
   public static Latch onTrue(ValueModel<Boolean> source)
   {
      return new Latch(source, true);
   }

   /**
    * Creates a latch that will latch on a transition of true to false.
    *
    * @param source the source model.
    * @return a new latch instance.
    */
   public static Latch onFalse(ValueModel<Boolean> source)
   {
      return new Latch(source, false);
   }

   // the value we're latching to
   private boolean latchValue;
   // the current value
   private boolean value;
   // the source model we're latching on
   private ValueModel<Boolean> source;

   protected Latch(ValueModel<Boolean> source, boolean latchValue)
   {
      this.source = source;
      this.latchValue = latchValue;
      this.value = !latchValue;
      this.source.addValueChangeHandler(new ValueChangeHandler<Boolean>()
      {
         public void onValueChange(ValueChangeEvent<Boolean> booleanValueChangeEvent)
         {
            doLatch();
         }
      });
   }

   public Boolean getValue()
   {
      return value;
   }

   private void doLatch()
   {
      Boolean sourceValue = source.getValue();

      if (sourceValue == null)
      {
         return;
      }

      if (value != latchValue && sourceValue == latchValue)
      {
         value = sourceValue;
         fireValueChangeEvent(value);
      }
   }


   public void reset()
   {
      if (value == latchValue)
      {
         value = !latchValue;
         fireValueChangeEvent(value);
      }
   }
}
