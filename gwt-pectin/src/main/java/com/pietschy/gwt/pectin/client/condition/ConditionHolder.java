package com.pietschy.gwt.pectin.client.condition;

import com.pietschy.gwt.pectin.client.value.ValueHolder;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * A simple ValueHolder&gtBoolean&lt; that implements Condition.
 */
public class ConditionHolder extends ValueHolder<Boolean> implements Condition
{
   public Condition and(ValueModel<Boolean> condition, ValueModel<Boolean>... others)
   {
      return Conditions.and(this, condition, others);
   }

   public Condition or(ValueModel<Boolean> condition, ValueModel<Boolean>... others)
   {
      return Conditions.or(this, condition, others);
   }

   public Condition not()
   {
      return Conditions.isNot(this);
   }
}
