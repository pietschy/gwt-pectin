/*
 * Copyright 2009 Andrew Pietsch 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you 
 * may not use this file except in compliance with the License. You may 
 * obtain a copy of the License at 
 *      
 *      http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing permissions 
 * and limitations under the License. 
 */

package com.pietschy.gwt.pectin.client.condition;

import com.pietschy.gwt.pectin.client.function.Reduce;
import com.pietschy.gwt.pectin.client.list.ListModel;
import com.pietschy.gwt.pectin.client.list.ReducingValueModel;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * A condition that's based on a list based ReducingValueModel.
 */
public class ReducingListCondition<T> extends ReducingValueModel<Boolean, T> implements Condition
{
   public ReducingListCondition(ListModel<T> source, Reduce<Boolean, ? super T> function)
   {
      super(source, function);
   }

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