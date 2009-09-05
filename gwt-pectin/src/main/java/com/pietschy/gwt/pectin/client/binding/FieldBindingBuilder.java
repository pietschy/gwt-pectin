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

package com.pietschy.gwt.pectin.client.binding;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.pietschy.gwt.pectin.client.FieldModel;
import com.pietschy.gwt.pectin.client.format.DisplayFormat;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 4:48:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class FieldBindingBuilder<T>
{
   private WidgetBinder binder;
   private FieldModel<T> field;

   public FieldBindingBuilder(WidgetBinder binder, FieldModel<T> field)
   {
      this.binder = binder;
      this.field = field;
   }
   
   public void to(HasValue<T> widget)
   {
      binder.registerBinding(new FieldToHasValueBinding<T>(field, widget));
   }
   
   public void toLabel(HasText label)
   {
      toLabel(label, new ToStringFormat<T>());
   }
   
   public void toLabel(HasText label, DisplayFormat<? super T> format)
   {
      binder.registerBinding(new FieldToHasTextBinding<T>(field, label, format));
   }
   
   public WithValueBulider withValue(T value)
   {
       return new WithValueBulider(value);
   }
   
   public class WithValueBulider
   {
      private T selectedValue;

      public WithValueBulider(T selectedValue)
      {
         this.selectedValue = selectedValue;
      }

      public void to(HasValue<Boolean> selectable)
      {
         binder.registerBinding(new FieldWithValueBinding<T>(field, selectable, selectedValue));
      }
   }

}
