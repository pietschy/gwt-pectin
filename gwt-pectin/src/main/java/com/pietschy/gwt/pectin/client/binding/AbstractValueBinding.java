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
import com.pietschy.gwt.pectin.client.value.MutableValueModel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 4:53:36 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractValueBinding<T> extends AbstractBinding
{
   private MutableValueModel<T> model;
   private FieldMonitor fieldMonitor = new FieldMonitor();

   public AbstractValueBinding(MutableValueModel<T> model)
   {
      this.model = model;
      registerHandler(model.addValueChangeHandler(fieldMonitor));
   }

   public void updateTarget()
   {
      updateWidget(model.getValue());
   }

   protected abstract void updateWidget(T value);

   protected void updateModel(T value)
   {
      fieldMonitor.setIgnoreEvents(true);
      try
      {
         model.setValue(value);
      }
      finally
      {
         fieldMonitor.setIgnoreEvents(false);
      }
   }

   private class FieldMonitor implements ValueChangeHandler<T>
   {
      private boolean ignoreEvents = false;
      
      public void onValueChange(ValueChangeEvent<T> event)
      {
         if (!ignoreEvents)
         {
            T value = event.getValue();
            updateWidget(value);
         }
      }

      public void setIgnoreEvents(boolean ignoreEvents)
      {
         this.ignoreEvents = ignoreEvents;
      }
   }


   
}
