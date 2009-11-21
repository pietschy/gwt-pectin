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
import com.pietschy.gwt.pectin.client.ListFieldModel;
import com.pietschy.gwt.pectin.client.format.ListDisplayFormat;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 4:48:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ListBindingBuilder<T>
{
   private WidgetBinder binder;
   private ListFieldModel<T> field;

   public ListBindingBuilder(WidgetBinder binder, ListFieldModel<T> field)
   {
      this.binder = binder;
      this.field = field;
   }
   
   public void to(HasValue<Collection<T>> widget)
   {
      binder.registerBinding(new ListToHasValueBinding<T>(field, widget));
   }

   public void toLabel(HasText label)
   {
      toLabel(label, new CollectionToStringFormat<T>());
   }

   public void toLabel(HasText label, ListDisplayFormat<T> format)
   {
      binder.registerAndInitialiseBinding(new ListFieldToHasTextBinding(field, label, format));
   }
   
   public ContainingValueBulider containingValue(T value)
   {
       return new ContainingValueBulider(value);
   }
   
   public class ContainingValueBulider
   {
      private T selectedValue;

      public ContainingValueBulider(T selectedValue)
      {
         this.selectedValue = selectedValue;
      }

      public void to(HasValue<Boolean> selectable)
      {
         binder.registerBinding(new ListContainsValueBinding<T>(field, selectable, selectedValue));
      }
      
   }

}
