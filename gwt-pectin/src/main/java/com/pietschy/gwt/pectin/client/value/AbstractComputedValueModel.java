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
import com.google.gwt.event.logical.shared.ValueChangeEvent;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 20, 2009
 * Time: 12:10:58 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class
AbstractComputedValueModel<T, S>
extends AbstractValueModel<T>
{
   private ValueChangeHandler<S> changeMonitor = new ValueChangeHandler<S>()
   {
      public void onValueChange(ValueChangeEvent<S> event)
      {
         fireValueChangeEvent(getValue());
      }
   };
   private ValueModel<S> source;

   public AbstractComputedValueModel(ValueModel<S> source)
   {
      if (source == null)
      {
         throw new NullPointerException("source is null");
      }
      this.source = source;
      this.source.addValueChangeHandler(changeMonitor);
   }

   public T getValue()
   {
      return computeValue(source.getValue());
   }

   protected abstract T computeValue(S value);
   
}
