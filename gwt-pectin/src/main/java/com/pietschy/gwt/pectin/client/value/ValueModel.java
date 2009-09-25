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

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * ValueModels provide a standard interface for obtaining values and being notified when that
 * value changes.  Mutation of the value is supported by {@link MutableValueModel}.
 */
public interface ValueModel<T>
{
   /**
    * Gets the value held by this model.
    * @return the value held by this model.
    */
   T getValue();

   /**
    * Adds a handler that will be notified when ever this models value changes.
    * @param handler the handler to be notified.
    * @return a {@link HandlerRegistration}.
    */
   HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler);
}
