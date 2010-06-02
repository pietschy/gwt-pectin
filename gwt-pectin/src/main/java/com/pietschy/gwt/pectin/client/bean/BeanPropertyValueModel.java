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

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.pietschy.gwt.pectin.client.value.AbstractMutableValueModel;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 *
 */
public class BeanPropertyValueModel<T> extends AbstractMutableValueModel<T> implements BeanPropertyModelBase
{
   private BeanPropertyAccessor accessor;
   private PropertyKey<T> propertyKey;
   private T checkpointValue;
   private T bufferedValue;
   private ValueHolder<Boolean> dirtyModel = new ValueHolder<Boolean>(false);
   private ValueHolder<Boolean> mutableModel = new ValueHolder<Boolean>(false);
   private ValueModel<?> source;
   private ValueModel<Boolean> autoCommit;

   public BeanPropertyValueModel(ValueModel<?> source, PropertyKey<T> key, BeanPropertyAccessor accessor, ValueModel<Boolean> autoCommit)
   {
      this.source = source;
      this.propertyKey = key;
      this.accessor = accessor;
      this.autoCommit = autoCommit;
      dirtyModel.setFireEventsEvenWhenValuesEqual(false);
      
      installValueChangeHandler(source);
      handleSourceModelChange();
   }

   @SuppressWarnings("unchecked")
   private void installValueChangeHandler(ValueModel<?> source)
   {
      // yep I know, no generics... I don't know or care what the type is since the generated accessor handles
      // all that.  We could get around this if ValueChangeHandler allowed for ValueChangeHandler<? super T>.
      source.addValueChangeHandler(new ValueChangeHandler()
      {
         public void onValueChange(ValueChangeEvent event)
         {
            handleSourceModelChange();
         }
      });
   }

   private void handleSourceModelChange()
   {
      readFromSource();
      onSourceModelChanged(source.getValue());
   }

   /**
    * This is an empty method that subclasses can override to perform
    * actions when the source bean changes.
    * @param sourceBean the new value of the source bean.
    */
   protected void onSourceModelChanged(Object sourceBean)
   {
   }

   public String getPropertyName()
   {
      return propertyKey.getPropertyName();
   }

   public void setValue(T value)
   {
      ensureMutable();
      setValueInternal(value);
   }

   private void ensureMutable()
   {
      if (!isMutableProperty())
      {
         throw new ReadOnlyPropertyException(propertyKey);
      }
      else if (!isNonNullSource())
      {
         throw new SourceBeanIsNullException(propertyKey);
      }
   }

   private void setValueInternal(T value)
   {
      T oldValue = bufferedValue;
      bufferedValue = value;
      if (isAutoCommit())
      {
         writeToSource(true);
      }
      else
      {
         updateDirtyState();
      }
      fireValueChangeEvent(oldValue, value);
   }

   protected boolean isAutoCommit()
   {
      // only true if not null and true.
      return Boolean.TRUE.equals(autoCommit.getValue());
   }

   @SuppressWarnings("unchecked")
   public T getValue()
   {
      return bufferedValue;
   }

   public void writeToSource(boolean checkpoint)
   {
      ensureMutable();
      T value = getValue();
      accessor.writeProperty(source.getValue(), getPropertyName(), value);
      if (checkpoint)
      {
         checkpoint();
      }
   }

   @SuppressWarnings("unchecked")
   public void readFromSource()
   {
      checkpointValue = (T) accessor.readProperty(source.getValue(), getPropertyName());
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
      mutableModel.setValue(isMutableProperty() && isNonNullSource());
   }

   private boolean isNonNullSource()
   {
      return source.getValue() != null;
   }

   public boolean isMutableProperty()
   {
      return accessor.isMutable(getPropertyName());
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