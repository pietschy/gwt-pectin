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

package com.pietschy.gwt.pectin.client.style;

import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 4:48:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class FieldStyleBindingBuilder<T>
{
   private StyleBinder binder;
   private ValueModel<T> field;

   public FieldStyleBindingBuilder(StyleBinder binder, ValueModel<T> field)
   {
      this.binder = binder;
      this.field = field;
   }
   
   public WithValueBuilder withValue(T value)
   {
       return new WithValueBuilder(field, value);
   }
   
   public class WithValueBuilder
   {
      private T selectedValue;
      private ValueModel<T> field;

      public WithValueBuilder(ValueModel<T> field, T selectedValue)
      {
         this.field = field;
         this.selectedValue = selectedValue;
      }

      public StyleBindingBuilder toStyle(String styleName)
      {
         return new StyleBindingBuilder<T>(binder, field, selectedValue, styleName);
      }
   }

}