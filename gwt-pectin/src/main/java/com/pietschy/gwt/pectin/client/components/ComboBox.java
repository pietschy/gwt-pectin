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

package com.pietschy.gwt.pectin.client.components;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasValue;
import com.pietschy.gwt.pectin.client.list.ArrayListModel;
import com.pietschy.gwt.pectin.client.list.ListModel;
import com.pietschy.gwt.pectin.client.list.ListModelChangedEvent;
import com.pietschy.gwt.pectin.client.list.ListModelChangedHandler;
import com.pietschy.gwt.pectin.client.metadata.HasEnabled;
import com.pietschy.gwt.pectin.client.validation.ValidationResult;
import com.pietschy.gwt.pectin.client.validation.component.StyleApplicator;
import com.pietschy.gwt.pectin.client.validation.component.ValidationDisplay;

import java.util.Arrays;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jun 17, 2008
 * Time: 2:41:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class ComboBox<T>
extends Composite
implements HasValue<T>, HasEnabled, Focusable, HasFocusHandlers, HasBlurHandlers, ValidationDisplay
{
   public static Renderer<Object> DEFAULT_RENDERER = new Renderer<Object>()
      {
         public String toDisplayString(Object value)
         {
            return value != null ? value.toString() : "<null>";
         }
      };
   
   
   private ListBoxWorkAroundBug2689 listBox = new ListBoxWorkAroundBug2689();
   private ListModel<T> valueList;
   private ListBoxMonitor listBoxMonitor = new ListBoxMonitor();
   private Renderer<? super T> renderer = DEFAULT_RENDERER;

   public ComboBox(T[] values)
   {
      this(Arrays.asList(values));
   }
   
   public ComboBox(List<T> values)
   {
      ArrayListModel<T> model = new ArrayListModel<T>();
      model.setElements(values);
      init(model);
   }

   public ComboBox(ListModel<T> values)
   {
      init(values);
   }

   private void init(ListModel<T> values)
   {
      valueList = values;
      rebuildListBox();
      listBox.addChangeHandler(listBoxMonitor);
      valueList.addListModelChangedHandler(new ListModelChangedHandler<T>()
      {
         public void onListDataChanged(ListModelChangedEvent<T> event)
         {
            rebuildListBox();
         }
      });

      initWidget(listBox);
   }

   private void rebuildListBox()
   {
      listBoxMonitor.setIgnoreEvents(true);
      try
      {
         listBox.clear();
         for (int i = 0; i < valueList.size(); i++)
         {
            T value = valueList.get(i);
            listBox.addItem(renderer.toDisplayString(value), Integer.toString(i));
         }
      }
      finally
      {
         listBoxMonitor.setIgnoreEvents(false);
      }
   }

   public void setRenderer(Renderer<? super T> renderer)
   {
      if (renderer == null)
      {
         throw new NullPointerException("renderer is null");
      }
      
      this.renderer = renderer;
      rebuildListBox();
   }

   public void setEnabled(boolean enabled)
   {
      listBox.setEnabled(enabled);
   }

   public boolean isEnabled()
   {
      return listBox.isEnabled();
   }

   public T
   getValue()
   {
      String indexAsString = listBox.getSelectedValue();
      
      if (indexAsString == null)
      {
         return null;
      }
      
      return valueList.get(Integer.parseInt(indexAsString));
   }

   public void
   setValue(T value)
   {
       setValue(value, false);
   }

   public void setValue(T value, boolean fireEvents)
   {
      listBoxMonitor.setIgnoreEvents(true);
      try
      {
         if (value == null)
         {
            listBox.setSelectedValue(null);
         }
         else
         {
            listBox.setSelectedValue(Integer.toString(valueList.indexOf(value)));
         }
         
         if (fireEvents)
         {
            fireValueChanged(value);
         }
      }
      finally
      {
         listBoxMonitor.setIgnoreEvents(false);
      }
   }

   public void setValidationResult(ValidationResult result)
   {
      StyleApplicator.defaultInstance().applyStyles(listBox, result);
   }

   private void fireValueChanged(T value)
   {
      ValueChangeEvent.fire(this, value);
   }

   public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler)
   {
      return addHandler(handler, ValueChangeEvent.getType());
   }

   public HandlerRegistration addFocusHandler(FocusHandler handler)
   {
      return listBox.addFocusHandler(handler);  
   }

   public HandlerRegistration addBlurHandler(BlurHandler handler)
   {
      return listBox.addBlurHandler(handler);
   }

   public int getTabIndex()
   {
      return listBox.getTabIndex();
   }

   public void setAccessKey(char key)
   {
      listBox.setAccessKey(key);
   }

   public void setFocus(boolean focused)
   {
      listBox.setFocus(focused);
   }

   public void setTabIndex(int index)
   {
      listBox.setTabIndex(index);
   }

   public static interface Renderer<T>
   {
      String toDisplayString(T value);
   }
   
   

   private class ListBoxMonitor implements ChangeHandler
   {
      private boolean ignoreEvents = false;
      
      public void onChange(ChangeEvent event)
      {
         if (!ignoreEvents)
         {
            fireValueChanged(getValue());
         }
      }

      public void setIgnoreEvents(boolean ignoreEvents)
      {
         this.ignoreEvents = ignoreEvents;
      }
   }
}