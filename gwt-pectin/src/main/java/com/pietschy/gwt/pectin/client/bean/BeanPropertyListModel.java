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

import java.util.Collection;

/**
 * 
 */
public class BeanPropertyListModel<T>
extends ArrayListModel<T>
{
   private BeanModelProvider provider;
   private String propertyName;
   private CollectionConverter listConverter;

   public BeanPropertyListModel(BeanModelProvider provider, String propertyName, CollectionConverter listConverter)
   {
      this.provider = provider;
      this.propertyName = propertyName;
      this.listConverter = listConverter;
   }

   public void setElements(Collection<? extends T> elements)
   {
      super.setElements(elements);
      doAutoCommit();
   }

   public void add(T element)
   {
      super.add(element);
      doAutoCommit();
   }

   public void remove(T element)
   {
      super.remove(element);
      doAutoCommit();
   }

   private void doAutoCommit()
   {
      if (provider.isAutoCommit())
      {
         commit();
      }
   }

   @SuppressWarnings("unchecked")
   protected void revert()
   {
      setElements((Collection<T>) provider.readValue(propertyName));
   }
   
   @SuppressWarnings("unchecked")
   protected void commit()
   {
      provider.writeValue(propertyName, listConverter.toBean(asUnmodifiableList()));
   }
}