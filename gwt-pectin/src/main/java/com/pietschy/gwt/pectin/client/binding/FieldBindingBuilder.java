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
import com.pietschy.gwt.pectin.client.format.ToStringFormat;
import com.pietschy.gwt.pectin.client.value.MutableValue;

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
      final FieldToHasValueBinding<T> binding = new FieldToHasValueBinding<T>(field, widget);
      binder.registerBinding(binding, binding.getFieldModel(), binding.getTarget());
   }

   public void to(MutableValue<T> widget)
   {
      final FieldToStaticValueBinding<T> binding = new FieldToStaticValueBinding<T>(field, widget);
      binder.registerBinding(binding, binding.getFieldModel(), binding.getTarget());
   }

   public DisplayFormatBuilder<T> toLabel(HasText label)
   {
      FieldToHasTextBinding<T> binding = new FieldToHasTextBinding<T>(field, label, ToStringFormat.defaultInstance());
      binder.registerBinding(binding, binding.getFieldModel(), binding.getTarget());
      return new DisplayFormatBuilder<T>(binding);
   }

   /**
    * @deprecated use toLabel(label).withFormat(format) instead.
    */
   public void toLabel(HasText label, DisplayFormat<? super T> format)
   {
      toLabel(label).withFormat(format);
   }
   
   public WithValueBuilder withValue(T value)
   {
       return new WithValueBuilder(value);
   }
   
   public class WithValueBuilder
   {
      private T selectedValue;

      public WithValueBuilder(T selectedValue)
      {
         this.selectedValue = selectedValue;
      }

      public void to(HasValue<Boolean> selectable)
      {
         final FieldWithValueBinding<T> binding = new FieldWithValueBinding<T>(field, selectable, selectedValue);
         binder.registerBinding(binding, binding.getFieldModel(), binding.getTarget());
      }
   }

}
