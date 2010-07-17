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

package com.pietschy.gwt.pectin.client.form.binding;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HasText;
import com.pietschy.gwt.pectin.client.binding.AbstractListBinding;
import com.pietschy.gwt.pectin.client.binding.HasListDisplayFormat;
import com.pietschy.gwt.pectin.client.form.FormattedListFieldModel;
import com.pietschy.gwt.pectin.client.format.CollectionToStringFormat;
import com.pietschy.gwt.pectin.client.format.DisplayFormat;
import com.pietschy.gwt.pectin.client.format.Format;
import com.pietschy.gwt.pectin.client.format.ListDisplayFormat;
import com.pietschy.gwt.pectin.client.list.ListModel;

/**
 *
 */
public class FormattedListFieldToHasTextBinding<T>
extends AbstractListBinding<T> implements HasListDisplayFormat<T>
{
   private HasText widget;
   private ListDisplayFormat<? super T> userFormat;
   private CollectionToStringFormat<T> defaultFormat = new CollectionToStringFormat<T>()
   {
      @Override
      public DisplayFormat<? super T> getValueFormat()
      {
         return getModel().getFormat();
      }
   };

   public FormattedListFieldToHasTextBinding(FormattedListFieldModel<T> field, HasText widget, ListDisplayFormat<? super T> userFormat)
   {
      super(field);
      this.widget = widget;
      this.userFormat = userFormat;
      registerDisposable(field.getFormatModel().addValueChangeHandler(new FormatChangeHandler<T>()));
   }

   public HasText getTarget()
   {
      return widget;
   }

   @Override
   protected void updateTarget(ListModel<T> model)
   {
      getTarget().setText(format(model));
   }

   String format(ListModel<T> model)
   {
      return userFormat != null ?
             userFormat.format(model.asUnmodifiableList()) :
             defaultFormat.format(model.asUnmodifiableList());
   }

   @Override
   protected FormattedListFieldModel<T> getModel()
   {
      return (FormattedListFieldModel<T>) super.getModel();
   }

   public void setFormat(ListDisplayFormat<? super T> format)
   {
      if (format == null)
      {
         throw new NullPointerException("format is null");
      }
      this.userFormat = format;
      updateTarget();
   }

   private class FormatChangeHandler<T> implements ValueChangeHandler<Format<T>>
   {
      public void onValueChange(ValueChangeEvent<Format<T>> event)
      {
         updateTarget();
      }
   }

}