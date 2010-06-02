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
import com.pietschy.gwt.pectin.client.list.ArrayListModel;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import com.pietschy.gwt.pectin.client.value.ValueModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class BeanPropertyListModel<T> extends ArrayListModel<T> implements BeanPropertyModelBase
{
   private List<T> EMPTY_LIST = Collections.emptyList();

   private PropertyKey<T> propertyKey;
   private List<T> checkpointValue = null;
   private CollectionConverter listConverter;
   private ValueHolder<Boolean> dirtyModel = new ValueHolder<Boolean>(false);
   private BeanPropertyAccessor accessor;
   private ValueHolder<Boolean> mutableModel = new ValueHolder<Boolean>(false);
   private ValueModel<?> source;
   private ValueModel<Boolean> autoCommit;


   public BeanPropertyListModel(ValueModel<?> source, PropertyKey<T> propertyKey, BeanPropertyAccessor accessor, CollectionConverter listConverter, ValueModel<Boolean> autoCommit)
   {
      this.source = source;
      this.accessor = accessor;
      this.propertyKey = propertyKey;
      this.listConverter = listConverter;
      this.autoCommit = autoCommit;
      dirtyModel.setFireEventsEvenWhenValuesEqual(false);
      
      installValueChangeHandler();
      handleSourceModelChange();
   }

   @SuppressWarnings("unchecked")
   private void installValueChangeHandler()
   {
      // yep I know, no generics... I don't know or care what the type is since the accessor handles
      // all that.  And since ValueChangeHandler doesn't allow for ValueChangeHander<? super T> I can't
      // add a ValueChangeHandler<Object> to a ValueModel<?>
      this.source.addValueChangeHandler(new ValueChangeHandler()
      {
         public void onValueChange(ValueChangeEvent bValueChangeEvent)
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

   protected boolean isAutoCommit()
   {
      // only true if not null and true.
      return Boolean.TRUE.equals(autoCommit.getValue());
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

   private void setElementsInternal(Collection<? extends T> elements)
   {
      super.setElements(elements);
   }

   public void setElements(Collection<? extends T> elements)
   {
      ensureMutable();
      setElementsInternal(elements);
      afterMutate();
   }

   private void afterMutate()
   {
      if (isAutoCommit())
      {
         writeToSource(true);
      }
      else
      {
         updateDirtyState();
      }
   }

   public void add(T element)
   {
      ensureMutable();
      super.add(element);
      afterMutate();
   }

   public void remove(T element)
   {
      ensureMutable();
      super.remove(element);
      afterMutate();
   }

   public ValueModel<Boolean> getDirtyModel()
   {
      return dirtyModel;
   }

   @SuppressWarnings("unchecked")
   public void readFromSource()
   {
      Object propertyValue = accessor.readProperty(source.getValue(), getPropertyName());
      safelySetCheckpoint(listConverter.fromBean(propertyValue));
      updateMutableState();
      revertToCheckpoint();
   }

   /**
    * This method handles the case where the source is null.  It doesn't recompute
    * @param source
    */
   private void safelySetCheckpoint(Collection<T> source)
   {
      // we copy so mutations don't affect us.
      checkpointValue = source != null ? new ArrayList<T>(source) : EMPTY_LIST;
      updateDirtyState();
   }

   /**
    *
    * @return
    */
   private List<T> safelyGetCheckpoint()
   {
      return checkpointValue != null ? checkpointValue : EMPTY_LIST;
   }

   public void writeToSource(boolean clearDirtyState)
   {
      ensureMutable();
      accessor.writeProperty(source.getValue(), getPropertyName(), listConverter.toBean(asUnmodifiableList()));
      if (clearDirtyState)
      {
         checkpoint();
      }
   }

   /**
    * Checkpoints the models dirty state to the current value of the model.  After calling this
    * method the dirty state will be <code>false</code>.
    *
    * @see revertToCheckpoint()
    */
   public void checkpoint()
   {
      safelySetCheckpoint(asUnmodifiableList());
   }

   /**
    * Reverts the value of this model to the previous checkpoint.  If checkpoint hasn't been called
    * then it will revert to the last call to readFrom.
    */
   public void revertToCheckpoint()
   {
      // setElements makes a copy of the data (i.e it doesn't maintain a reference
      // to the list so we don't need copy it passing in.
      setElementsInternal(safelyGetCheckpoint());
      dirtyModel.setValue(false);
   }

   @SuppressWarnings("unchecked")
   void updateDirtyState()
   {
      dirtyModel.setValue(computeDirty());
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

   protected boolean computeDirty()
   {
      if (checkpointValue == null)
      {
         return size() != 0;
      }
      else if (size() != checkpointValue.size())
      {
         return true;
      }
      else
      {
         // the sizes are equal so we check the contents are the same.
         for (int i = 0; i < checkpointValue.size(); i++)
         {
            if (!areEqual(get(i), checkpointValue.get(i)))
            {
               return true;
            }
         }
         return false;
      }
   }
}