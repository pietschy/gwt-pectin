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

import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 8:03:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValueConditionBuilder<T>
{
   private ValueModel<T> model;

   public ValueConditionBuilder(ValueModel<T> model)
   {
      this.model = model;
   }

   public Condition is(T value)
   {
      return new ValueIsCondition<T>(model, value);
   }
   
   public Condition isNot(T value)
   {
      return is(value).not();
   }
   
   public Condition equals(ValueModel<T> model)
   {
      return new ValueEqualsCondition<T>(this.model, model);
   }

   public Condition doesNotEqual(ValueModel<T> model)
   {
      return equals(model).not();
   }

}
