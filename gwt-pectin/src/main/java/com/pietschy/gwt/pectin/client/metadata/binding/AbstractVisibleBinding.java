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

package com.pietschy.gwt.pectin.client.metadata.binding;

import com.pietschy.gwt.pectin.client.binding.AbstractBinding;
import com.pietschy.gwt.pectin.client.value.ValueModel;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 29, 2009
 * Time: 2:44:47 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractVisibleBinding<T> 
extends AbstractBinding implements ValueChangeHandler<Boolean>
{
   private T widget;
   private ValueModel<Boolean> model;

   public AbstractVisibleBinding(ValueModel<Boolean> model, T widget)
   {
      this.model = model;
      this.widget = widget;
      registerHandler(model.addValueChangeHandler(this));
   }

   public void updateTarget()
   {
      updateWidget(model.getValue());
   }

   public T getTarget()
   {
      return widget;
   }

   protected abstract void updateWidget(boolean visible);

   public void onValueChange(ValueChangeEvent<Boolean> event)
   {
      updateWidget(event.getValue());
   }
}
