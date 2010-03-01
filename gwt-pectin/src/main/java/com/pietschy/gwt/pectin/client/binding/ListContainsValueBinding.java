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

package com.pietschy.gwt.pectin.client.binding;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HasValue;
import com.pietschy.gwt.pectin.client.ListFieldModel;
import com.pietschy.gwt.pectin.client.list.MutableListModel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 4:35:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class ListContainsValueBinding<T> 
extends AbstractListBinding<T>
{
   private HasValue<Boolean> widget;

   private T value;

   public ListContainsValueBinding(ListFieldModel<T> field, HasValue<Boolean> widget, T value)
   {
      super(field);
      this.widget = widget;
      this.value = value;
      registerHandler(widget.addValueChangeHandler(new WidgetMonitor()));
   }

   public void updateTarget()
   {
      widget.setValue(getModel().contains(value), false);
   }

   public HasValue<Boolean> getTarget()
   {
      return widget;
   }

   private class WidgetMonitor implements ValueChangeHandler<Boolean>
   {
      public void onValueChange(final ValueChangeEvent<Boolean> event)
      {
         updateModel(new ModelUpdater<T>()
         {
            public void update(MutableListModel<T> model)
            {
               if (event.getValue())
               {
                  model.add(value);
               }
               else
               {
                  model.remove(value);
               }
            }
         });
      }
   }
}