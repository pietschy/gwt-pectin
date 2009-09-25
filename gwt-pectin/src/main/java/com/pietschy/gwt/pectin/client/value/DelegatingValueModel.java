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

package com.pietschy.gwt.pectin.client.value;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 *
 */
public class DelegatingValueModel<T> implements ValueModel<T>, HasValueChangeHandlers<T>
{
   private HandlerManager handlerManager = new HandlerManager(this);
   private DelegateMonitor delegateChangeMonitor = new DelegateMonitor();
   
   private T defaultValue;
   private ValueModel<T> delegate;
   private HandlerRegistration registration;

   public DelegatingValueModel()
   {
   }

   public DelegatingValueModel(T defaultValue)
   {
      this.defaultValue = defaultValue;
   }

   public void setDelegate(ValueModel<T> delegate)
   {
      if (registration != null)
      {
         registration.removeHandler();
      }
      
      this.delegate = delegate;

      registration = delegate.addValueChangeHandler(delegateChangeMonitor);

      fireValueChanged();
   }

   public T getValue()
   {
      return delegate == null ? defaultValue : delegate.getValue();
   }

   private void fireValueChanged()
   {
      ValueChangeEvent.fire(DelegatingValueModel.this, getValue());
   }

   public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler)
   {
      return handlerManager.addHandler(ValueChangeEvent.getType(), handler);
   }

   public void fireEvent(GwtEvent<?> event)
   {
      handlerManager.fireEvent(event);
   }

   private class DelegateMonitor implements ValueChangeHandler<T>
   {
      public void onValueChange(ValueChangeEvent<T> event)
      {                                                    
         fireValueChanged();
      }
   }
}
