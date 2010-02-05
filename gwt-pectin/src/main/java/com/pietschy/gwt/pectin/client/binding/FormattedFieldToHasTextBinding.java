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
import com.google.gwt.user.client.ui.HasText;
import com.pietschy.gwt.pectin.client.FormattedFieldModel;
import com.pietschy.gwt.pectin.client.format.DisplayFormat;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 4:35:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormattedFieldToHasTextBinding<T>
extends AbstractBinding implements HasDisplayFormat<T>
{
   private HasText widget;
   private FormattedFieldModel<T> field;
   private DisplayFormat<? super T> format;

   public FormattedFieldToHasTextBinding(FormattedFieldModel<T> field, HasText widget, DisplayFormat<? super T> format)
   {
      this.field = field;
      this.widget = widget;
      this.format = format;
      registerHandler(field.addValueChangeHandler(new FieldMonitor()));
   }

   public HasText getTarget()
   {
      return widget;
   }


   public void updateTarget()
   {
      T value = field.getValue();
      updateTarget(value);
   }

   protected void updateTarget(T value)
   {
      getTarget().setText(format.format(value));
   }

   private void onModelChanged(T value)
   {
      updateTarget(value);
   }

   private class FieldMonitor implements ValueChangeHandler<T>
   {
      public void onValueChange(ValueChangeEvent<T> event)
      {
         onModelChanged(event.getValue());
      }
   }

   public DisplayFormat<? super T> getFormat()
   {
      return format;
   }

   public void setFormat(DisplayFormat<? super T> format)
   {
      this.format = format;
   }
}