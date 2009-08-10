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

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 9:37:17 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractValueModel<T> 
implements ValueModel<T>, HasValueChangeHandlers<T>
{
   protected HandlerManager handlers = new HandlerManager(this);
   
   public HandlerRegistration
   addValueChangeHandler(ValueChangeHandler<T> handler)
   {
      return handlers.addHandler(ValueChangeEvent.getType(), handler);
   }

   protected void 
   fireValueChangeEvent(T newValue)
   {
      ValueChangeEvent.fire(this, newValue);
   }
   
   protected void 
   fireValueChangeEvent(T oldValue, T newValue)
   {
      ValueChangeEvent.fireIfNotEqual(this, oldValue, newValue);
   }

   public void 
   fireEvent(GwtEvent<?> event)
   {
      handlers.fireEvent(event);
   }

   protected <H extends EventHandler> HandlerRegistration 
   addHandler(H handler, GwtEvent.Type<H> type)
   {
      return handlers.addHandler(type, handler);
   }
}
