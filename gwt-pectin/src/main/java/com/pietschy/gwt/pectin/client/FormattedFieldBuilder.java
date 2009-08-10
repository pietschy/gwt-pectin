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

package com.pietschy.gwt.pectin.client;

import com.pietschy.gwt.pectin.client.ValueModelProvider;
import com.pietschy.gwt.pectin.client.value.ValueModel;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import com.pietschy.gwt.pectin.client.format.Format;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 12:20:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormattedFieldBuilder<T>
{
   private FormModel formModel;
   private Class<T> valueType;

   protected FormattedFieldBuilder(FormModel formModel, Class<T> valueType)
   {
      this.formModel = formModel;
      this.valueType = valueType;
   }

   public BindingBuilder using(Format<T> formatter)
   {
      return new BindingBuilder(formatter);
   }

   public class BindingBuilder
   {
      private Format<T> formatter;

      protected BindingBuilder(Format<T> formatter)
      {
         this.formatter = formatter;
      }

      public FormattedFieldModel<T> create()
      {
         return formModel.createFormattedFieldModel(new ValueHolder<T>(), formatter);
      }

      public FormattedFieldModel<T> createWithValue(T initialValue)
      {
         return formModel.createFormattedFieldModel(new ValueHolder<T>(initialValue), formatter);
      }

      public FormattedFieldModel<T> boundTo(ValueModel<T> source)
      {
         return formModel.createFormattedFieldModel(source, formatter);
      }

      public FormattedFieldModel<T> boundTo(ValueModelProvider provider, String propertyName)
      {
         return boundTo(provider.getValueModel(propertyName, valueType));
      }
   }
}