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
import com.google.gwt.user.client.ui.HasValue;
import com.pietschy.gwt.pectin.client.FormattedListFieldModel;
import com.pietschy.gwt.pectin.client.format.CollectionToStringFormat;
import com.pietschy.gwt.pectin.client.format.ListDisplayFormat;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 4:48:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormattedListFieldBindingBuilder<T>
{
   private WidgetBinder binder;
   private FormattedListFieldModel<T> field;

   public FormattedListFieldBindingBuilder(WidgetBinder binder, FormattedListFieldModel<T> field)
   {
      this.binder = binder;
      this.field = field;
   }

   public void to(HasValue<Collection<String>> widget)
   {
      binder.registerBinding(new FormattedListFieldToHasValueBinding<T>(field, widget), field, widget);
   }

   public ListDisplayFormatBuilder<T> toLabel(HasText label)
   {
      CollectionToStringFormat<T> format = CollectionToStringFormat.defaultInstance();

      ListFieldToHasTextBinding<T> binding = new ListFieldToHasTextBinding<T>(field, label, format);

      binder.registerBinding(binding, field, label);

      return new ListDisplayFormatBuilder<T>(binding);
   }

   /**
    * @deprecated use toLabel(label).withFormat(format) instead.
    */
   public void toLabel(HasText label, ListDisplayFormat<T> format)
   {
      toLabel(label).withFormat(format);
   }
}