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
 * Date: Jul 16, 2009
 * Time: 4:08:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Conditions
{
   
   public static <T> ValueConditionBuilder<T> valueOf(ValueModel<T> model)
   {
      return new ValueConditionBuilder<T>(model);
   }
   
   public static Condition and(ValueModel<Boolean> a, ValueModel<Boolean> b, ValueModel<Boolean>... others)
   {
      AndCondition condition = new AndCondition();
      condition.addSourceModels(a, b);
      condition.addSourceModels(others);
      return condition;
   }
   
   public static Condition or(ValueModel<Boolean> a, ValueModel<Boolean> b, ValueModel<Boolean>... others)
   {
      OrCondition condition = new OrCondition();
      condition.addSourceModels(a, b);
      condition.addSourceModels(others);
      return condition;
   }
   
   public static Condition not(ValueModel<Boolean> condition)
   {
      return new NotCondition(condition);
   }
}
