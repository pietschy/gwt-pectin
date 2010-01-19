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

package com.pietschy.gwt.pectin.client.bean;

import com.pietschy.gwt.pectin.client.value.AbstractMutableValueModel;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * 
 */
public class BeanPropertyValueModel<T>
extends AbstractMutableValueModel<T>
{
   private BeanModelProvider provider;
   private String propertyName;
   private T bufferedValue;
   private ValueHolder<Boolean> dirtyModel = new ValueHolder<Boolean>(false);

   public BeanPropertyValueModel(BeanModelProvider provider, String propertyName)
   {
      this.provider = provider;
      this.propertyName = propertyName;
      dirtyModel.setFireEventsEvenWhenValuesEqual(false);
   }

   public void setValue(T value)
   {
      bufferedValue = value;
      if (provider.isAutoCommit())
      {
         commit();
      }
      else
      {
         updateDirtyState();
      }
      fireValueChangeEvent(value);
   }

   @SuppressWarnings("unchecked")
   public T getValue()
   {
      return bufferedValue;
   }
   
   @SuppressWarnings("unchecked")
   public Class<T> getValueType()
   {
      return (Class<T>) provider.getPropertyType(propertyName);
   }
   
   @SuppressWarnings("unchecked")
   public void revert()
   {
      bufferedValue = (T) provider.readValue(propertyName);
      dirtyModel.setValue(false);
      fireValueChangeEvent(bufferedValue);
   }

   public void commit()
   {
      provider.writeValue(propertyName, getValue());
      dirtyModel.setValue(false);
   }

   public ValueModel<Boolean> getDirtyModel()
   {
      return dirtyModel;
   }

   void updateDirtyState()
   {
      Object beanValue = provider.readValue(propertyName);
      dirtyModel.setValue(!areEqual(bufferedValue, beanValue));
   }

   boolean areEqual(T bufferedValue, Object beanValue)
   {
      return bufferedValue == null ? beanValue == null : bufferedValue.equals(beanValue);
   }
}
