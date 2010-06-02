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
import com.pietschy.gwt.pectin.client.list.ListModel;
import com.pietschy.gwt.pectin.client.list.MutableListModel;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 4:35:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class ListToHasValueBinding<T> 
extends AbstractMutableListBinding<T>
{
   private HasValue<Collection<T>> widget;

   public ListToHasValueBinding(MutableListModel<T> field, HasValue<Collection<T>> widget)
   {
      super(field);
      this.widget = widget;
      registerDisposable(widget.addValueChangeHandler(new WidgetMonitor()));
   }

   @Override
   protected void updateTarget(ListModel<T> model)
   {
      // todo: we're copying in case the widget tries to modify in place... this will likely
      // lead to multiple copies being so it may be worth passing the unmodifiable list
      // and letting the widget developer decide what to do.
      widget.setValue(new ArrayList<T>(model.asUnmodifiableList()));
   }

   public HasValue<Collection<T>> getTarget()
   {
      return widget;
   }

   private class WidgetMonitor implements ValueChangeHandler<Collection<T>>
   {
      public void onValueChange(ValueChangeEvent<Collection<T>> event)
      {
         updateModel(new MutateOperation<T>()
         {
            public void execute(MutableListModel<T> model)
            {
               model.setElements(widget.getValue());
            }
         });
      }
   }
}