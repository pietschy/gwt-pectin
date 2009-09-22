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

import com.pietschy.gwt.pectin.client.BindingCallback;
import com.pietschy.gwt.pectin.client.FieldModel;
import com.pietschy.gwt.pectin.client.FormattedFieldModel;
import com.pietschy.gwt.pectin.client.ListFieldModel;

/**
 * WidgetBinder provides a builders to bind widgets to field models.  During the binding process
 * any {@link BindingCallback}s installed by plugins will be automatically invoked. 
 */
public class WidgetBinder 
extends AbstractBinder
{

   public <T> FieldBindingBuilder<T> bind(FieldModel<T> field)
   {
      return new FieldBindingBuilder<T>(this, field);
   }
   
   public <T> FormattedFieldBindingBuilder<T> bind(FormattedFieldModel<T> field)
   {
      return new FormattedFieldBindingBuilder<T>(this, field);
   }
   
   public <T> ListBindingBuilder<T> bind(ListFieldModel<T> field)
   {
      return new ListBindingBuilder<T>(this, field);
   }

   protected <T> void registerBinding(AbstractFieldBinding<T> binding)
   {
      super.registerBinding(binding);

      for (BindingCallback callback : binding.getFieldModel().getFormModel().getBindingCallbacks())
      {
         callback.onWidgetBinding(binding, binding.getFieldModel(), binding.getTarget());
      }
   }

   protected <T> void registerBinding(AbstractFormattedBinding<T> binding)
   {
      super.registerBinding(binding);

      for (BindingCallback callback : binding.getFieldModel().getFormModel().getBindingCallbacks())
      {
         callback.onWidgetBinding(binding, binding.getFieldModel(), binding.getTarget());
      }
   }

   protected <T> void registerBinding(AbstractListBinding<T> binding)
   {
      super.registerBinding(binding);

      for (BindingCallback callback : binding.getFieldModel().getFormModel().getBindingCallbacks())
      {
         callback.onWidgetBinding(binding, binding.getFieldModel(), binding.getTarget());
      }
   }


}
