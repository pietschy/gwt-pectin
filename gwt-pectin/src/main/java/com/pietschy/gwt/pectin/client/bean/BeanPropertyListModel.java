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
public class BeanPropertyListModel<B, T> extends ArrayListModel<T> implements BeanPropertyModelBase<B>
{
   private List<T> EMPTY_LIST = Collections.emptyList();

   private String propertyName;
   private List<T> checkpointValue = null;
   private CollectionConverter listConverter;
   private ValueHolder<Boolean> dirtyModel = new ValueHolder<Boolean>(false);
   private BeanPropertyAdapter<B> provider;
   private ValueHolder<Boolean> mutableModel = new ValueHolder<Boolean>(false);


   public BeanPropertyListModel(BeanPropertyAdapter<B> provider, String propertyName, CollectionConverter listConverter)
   {
      this.provider = provider;
      this.propertyName = propertyName;
      this.listConverter = listConverter;
      dirtyModel.setFireEventsEvenWhenValuesEqual(false);
      updateMutableState();
   }

   public String getPropertyName()
   {
      return propertyName;
   }

   private void ensureMutable()
   {
      if (!isMutable())
      {
         throw new IllegalStateException("Underlying bean list property is read only: " + getPropertyName());
      }
   }

   public void setElements(Collection<? extends T> elements)
   {
      ensureMutable();
      setElementsInternal(elements);
   }

   private void setElementsInternal(Collection<? extends T> elements)
   {
      super.setElements(elements);
      updateDirtyState();
   }

   public void add(T element)
   {
      ensureMutable();
      super.add(element);
      updateDirtyState();
   }

   public void remove(T element)
   {
      ensureMutable();
      super.remove(element);
      updateDirtyState();
   }

   public ValueModel<Boolean> getDirtyModel()
   {
      return dirtyModel;
   }

   @SuppressWarnings("unchecked")
   public void readFrom(B bean)
   {
      safelySetCheckpoint(listConverter.fromBean(provider.readProperty(bean, getPropertyName())));
      updateMutableState();
      revertToCheckpoint();
   }

   /**
    * This method handles the case where the source is null.  It doesn't recompute
    * @param source
    */
   private void safelySetCheckpoint(Collection<T> source)
   {
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

   public void copyTo(B bean, boolean clearDirtyState)
   {
      provider.writeProperty(bean, getPropertyName(), listConverter.toBean(asUnmodifiableList()));
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
      // we copy so mutations don't affect us.
      safelySetCheckpoint(asUnmodifiableList());
   }

   /**
    * Reverts the value of this model to the previous checkpoint.  If checkpoint hasn't been called
    * then it will revert to the last call to readFrom.
    */
   public void revertToCheckpoint()
   {
      // set elements makes a copy of the data (i.e it doesn't maintain a reference
      // to the list so we don't need copy it passing in.
      setElementsInternal(safelyGetCheckpoint());
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
      mutableModel.setValue(provider.isMutable(propertyName));
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