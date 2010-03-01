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

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 4:53:36 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractValueBinding<T> extends AbstractBinding
{
   private ValueModel<T> model;
   private ValueMonitor valueMonitor = new ValueMonitor();

   public AbstractValueBinding(ValueModel<T> valueModel)
   {
      this.model = valueModel;
      registerHandler(valueModel.addValueChangeHandler(valueMonitor));
   }

   public void updateTarget()
   {
      updateTarget(model.getValue());
   }

   protected abstract void updateTarget(T value);

   protected Boolean areEqual(T one, T two)
   {
      return one != null ? one.equals(two) : two == null;
   }

   private class ValueMonitor implements ValueChangeHandler<T>
   {
      public void onValueChange(ValueChangeEvent<T> event)
      {
         T value = event.getValue();
         updateTarget(value);
      }
   }
}