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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 
 */
public class BeanPropertyListModel<T>
extends ArrayListModel<T>
{
   private List<T> EMPTY_LIST = Collections.emptyList();
   
   private BeanModelProvider provider;
   private String propertyName;
   private CollectionConverter listConverter;
   private ValueHolder<Boolean> dirtyModel = new ValueHolder<Boolean>(false);


   public BeanPropertyListModel(BeanModelProvider provider, String propertyName, CollectionConverter listConverter)
   {
      this.provider = provider;
      this.propertyName = propertyName;
      this.listConverter = listConverter;
   }

   public void setElements(Collection<? extends T> elements)
   {
      super.setElements(elements);
      onMutation();
   }

   public void add(T element)
   {
      super.add(element);
      onMutation();
   }

   public void remove(T element)
   {
      super.remove(element);
      onMutation();
   }

   public ValueModel<Boolean> getDirtyModel()
   {
      return dirtyModel;
   }

   private void onMutation()
   {
      if (provider.isAutoCommit())
      {
         commit();
      }
      else
      {
         updateDirtyState();
      }
   }

   @SuppressWarnings("unchecked")
   protected void revert()
   {
      Collection<T> collection = (Collection<T>) provider.readValue(propertyName);
      // invoke setElementInternal() so we don't trigger onMutation() and write the value
      // back down to the bean again.
      setElementsInternal(collection != null ? collection : EMPTY_LIST);
      dirtyModel.setValue(false);
   }
   
   @SuppressWarnings("unchecked")
   protected void commit()
   {
      provider.writeValue(propertyName, listConverter.toBean(asUnmodifiableList()));
      dirtyModel.setValue(false);      
   }

   @SuppressWarnings("unchecked")
   void updateDirtyState()
   {
      Collection<T> beanCollection = (Collection<T>) provider.readValue(propertyName);

      if (beanCollection == null)
      {
         dirtyModel.setValue(size() != 0);
      }
      else
      {
         if (size() != beanCollection.size())
         {
            dirtyModel.setValue(true);
         }
         else
         {
            dirtyModel.setValue(!containsAll(beanCollection));
         }
      }
   }

}