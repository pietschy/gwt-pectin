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

package com.pietschy.gwt.pectin.client.list;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.pietschy.gwt.pectin.client.AbstractHasHandlers;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 2, 2009
 * Time: 11:29:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class ArrayListModel<T> 
extends AbstractHasHandlers
implements MutableListModel<T>
{
   protected HandlerManager handlers = new HandlerManager(this);
   private ArrayList<T> internalList = new ArrayList<T>();

   public ArrayListModel()
   {
   }

   public ArrayListModel(T... initialValues)
   {
      setElements(Arrays.asList(initialValues));
   }
   
   public ArrayListModel(Collection<T> initialValues)
   {
      setElements(initialValues);
   }

   public void add(T element)
   {
      internalList.add(element);
      fireListChanged();
   }

   public void remove(T element)
   {
      internalList.remove(element);
      fireListChanged();
   }

   public void setElements(Collection<? extends T> elements)
   {
      internalList.clear();
      internalList.addAll(elements);
      fireListChanged();
   }

   protected void fireListChanged()
   {
      ListModelChangedEvent.fire(this);
   }

   public int size()
   {
      return internalList.size();
   }

   public boolean isEmpty()
   {
      return internalList.isEmpty();
   }

   public T get(int index)
   {
      return internalList.get(index);
   }

   public boolean contains(T element)
   {
      return internalList.contains(element);  
   }

   public boolean containsAll(Collection<?> c)
   {
      return internalList.containsAll(c);
   }

   public List<T> asUnmodifiableList()
   {
      return Collections.unmodifiableList(internalList);
   }

   public int indexOf(T value)
   {
      return internalList.indexOf(value);
   }

   public Iterator<T> iterator()
   {
      return internalList.iterator();  
   }

   public HandlerRegistration addListModelChangedHandler(ListModelChangedHandler<T> handler)
   {
      return addHandler(handler, ListModelChangedEvent.getType());
   }

   protected boolean 
   isMutableSource()
   {
      return true;
   }
}