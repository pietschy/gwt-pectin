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

import com.pietschy.gwt.pectin.client.value.Reduce;
import com.pietschy.gwt.pectin.client.value.ReducingValueModel;
import com.pietschy.gwt.pectin.client.value.ValueModel;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 20, 2009
 * Time: 12:09:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValueEqualsCondition<T>
extends ReducingValueModel<Boolean, T>
implements Condition
{
   public ValueEqualsCondition(ValueModel<T> sourceA, ValueModel<T> sourceB)
   {
      super(new EqualsFunction<T>(), sourceA, sourceB);
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

   private static class EqualsFunction<T> implements Reduce<Boolean, T>
   {
      public Boolean compute(List<T> source)
      {
         T a = source.get(0);
         T b = source.get(1);
         return a == null ? b == null : a.equals(b);
      }
   }
}