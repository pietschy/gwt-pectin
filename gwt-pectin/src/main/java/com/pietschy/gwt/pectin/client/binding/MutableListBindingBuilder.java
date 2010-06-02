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

import com.google.gwt.user.client.ui.HasValue;
import com.pietschy.gwt.pectin.client.list.MutableListModel;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 4:48:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class MutableListBindingBuilder<T> extends ListBindingBuilder<T>
{
   public MutableListBindingBuilder(MutableListModel<T> model, BindingBuilderCallback callback)
   {
      super(model, callback);
   }
   
   public void to(HasValue<Collection<T>> widget)
   {
      final ListToHasValueBinding<T> binding = new ListToHasValueBinding<T>(getModel(), widget);
      getCallback().onBindingCreated(binding, widget);
   }

   public ContainingValueBuilder containingValue(T value)
   {
      return new ContainingValueBuilder(value);
   }

   @Override
   protected MutableListModel<T> getModel()
   {
      return (MutableListModel<T>) super.getModel();
   }

   public class ContainingValueBuilder
   {
      private T selectedValue;

      public ContainingValueBuilder(T selectedValue)
      {
         this.selectedValue = selectedValue;
      }

      public void to(HasValue<Boolean> selectable)
      {
         final ListContainsValueBinding<T> binding = new ListContainsValueBinding<T>(getModel(), selectable, selectedValue);
         getCallback().onBindingCreated(binding, binding.getTarget());
      }

   }
}
