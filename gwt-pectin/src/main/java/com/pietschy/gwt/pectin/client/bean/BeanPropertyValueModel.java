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
public class BeanPropertyValueModel<B, T>
   extends AbstractMutableValueModel<T> implements BeanPropertyModelBase<B>
{
   private BeanPropertyAdapter<B> provider;
   private String propertyName;
   private T checkpointValue;
   private T bufferedValue;
   private ValueHolder<Boolean> dirtyModel = new ValueHolder<Boolean>(false);
   private ValueHolder<Boolean> mutableModel = new ValueHolder<Boolean>(false);

   public BeanPropertyValueModel(BeanPropertyAdapter<B> provider, String propertyName)
   {
      this.provider = provider;
      this.propertyName = propertyName;
      dirtyModel.setFireEventsEvenWhenValuesEqual(false);
      updateMutableState();
   }


   public String getPropertyName()
   {
      return propertyName;
   }

   public void setValue(T value)
   {
      if (!isMutable())
      {
         throw new IllegalStateException("Underlying bean property is read only: " + getPropertyName());
      }

      setValueInternal(value);
   }

   private void setValueInternal(T value)
   {
      bufferedValue = value;
      updateDirtyState();
      fireValueChangeEvent(value);
   }

   @SuppressWarnings("unchecked")
   public T getValue()
   {
      return bufferedValue;
   }

   public void copyTo(B bean, boolean clearDirtyState)
   {
      T value = getValue();
      provider.writeProperty(bean, getPropertyName(), value);
      if (clearDirtyState)
      {
         checkpoint();
      }
   }

   @SuppressWarnings("unchecked")
   public void readFrom(B bean)
   {
      checkpointValue = (T) provider.readProperty(bean, getPropertyName());
      updateMutableState();
      setValueInternal(checkpointValue);
   }

   /**
    * Checkpoints the models dirty state to the current value of the model.  After calling this
    * method the dirty state will be <code>false</code>.
    *
    * @see revertToCheckpoint()
    */
   public void checkpoint()
   {
      checkpointValue = getValue();
      dirtyModel.setValue(false);
   }

   /**
    * Reverts the value of this model to the previous checkpoint.  If checkpoint hasn't been called
    * then it will revert to the last call to readFrom.
    */
   public void revertToCheckpoint()
   {
      setValueInternal(checkpointValue);
   }

   public ValueModel<Boolean> getMutableModel()
   {
      return mutableModel;
   }

   public boolean isMutable()
   {
      return getMutableModel().getValue();
   }

   private void updateMutableState()
   {
      mutableModel.setValue(provider.isMutable(propertyName));
   }


   public ValueModel<Boolean> getDirtyModel()
   {
      return dirtyModel;
   }

   void updateDirtyState()
   {
      dirtyModel.setValue(computeDirty());
   }

   boolean computeDirty()
   {
      return bufferedValue == null ? checkpointValue != null : !bufferedValue.equals(checkpointValue);
   }
}
