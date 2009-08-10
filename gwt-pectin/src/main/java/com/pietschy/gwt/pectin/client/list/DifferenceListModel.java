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

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.GwtEvent;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 10, 2009
 * Time: 5:45:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class DifferenceListModel<T>
implements ListModel<T>
{
   private ArrayListModel<T> delegate = new ArrayListModel<T>();
   private ListModel<T> modelA;
   private ListModel<T> modelB;
   
   private SourceListener<T> sourceListener = new SourceListener<T>();

   public DifferenceListModel(ListModel<T> a, ListModel<T> b)
   {
      modelA = a;
      modelB = b;
      
      modelA.addListModelChangedHandler(sourceListener);
      modelB.addListModelChangedHandler(sourceListener);
      
      recompute();
   }

   private void recompute()
   {
      ArrayList<T> list = new ArrayList<T>(modelA.asUnmodifiableList());
      list.removeAll(modelB.asUnmodifiableList());
      delegate.setElements(list);
   }

   public int size()
   {
      return delegate.size();
   }

   public boolean isEmpty()
   {
      return delegate.isEmpty();
   }

   public T get(int index)
   {
      return delegate.get(index);
   }

   public boolean contains(T element)
   {
      return delegate.contains(element);
   }

   public List<T> asUnmodifiableList()
   {
      return delegate.asUnmodifiableList();
   }

   public int indexOf(T value)
   {
      return delegate.indexOf(value);
   }

   public Iterator<T> iterator()
   {
      return delegate.iterator();
   }

   public HandlerRegistration addListModelChangedHandler(ListModelChangedHandler<T> handler)
   {
      return delegate.addListModelChangedHandler(handler);
   }

   public void fireEvent(GwtEvent<?> event)
   {
      delegate.fireEvent(event);
   }
   
   


   private class SourceListener<T> implements ListModelChangedHandler<T>
   {
      public void onListDataChanged(ListModelChangedEvent<T> event)
      {
         recompute();
      }
   }
}
