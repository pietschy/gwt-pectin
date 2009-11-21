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

import com.google.gwt.user.client.ui.HasText;
import com.pietschy.gwt.pectin.client.format.ListDisplayFormat;
import com.pietschy.gwt.pectin.client.list.ListModel;
import com.pietschy.gwt.pectin.client.list.ListModelChangedEvent;
import com.pietschy.gwt.pectin.client.list.ListModelChangedHandler;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 4:35:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class ListFieldToHasTextBinding<T>
extends AbstractBinding
{
   private HasText widget;
   private ListModel<T> field;
   private ListDisplayFormat<T> format;

   public ListFieldToHasTextBinding(ListModel<T> field, HasText widget, ListDisplayFormat<T> format)
   {
      this.field = field;
      this.widget = widget;
      this.format = format;
      registerHandler(field.addListModelChangedHandler(new FieldMonitor()));
   }

   public HasText getTarget()
   {
      return widget;
   }


   public void updateTarget()
   {
      updateTarget(field.asUnmodifiableList());
   }

   protected void updateTarget(Collection<T> values)
   {
      getTarget().setText(format.format(values));
   }

   private class FieldMonitor implements ListModelChangedHandler<T>
   {
      public void onListDataChanged(ListModelChangedEvent<T> event)
      {
         updateTarget();
      }
   }

}