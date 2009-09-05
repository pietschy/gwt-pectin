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

import com.pietschy.gwt.pectin.client.format.Format;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * Created by IntelliJ IDEA.
* User: andrew
* Date: Sep 5, 2009
* Time: 11:42:50 AM
* To change this template use File | Settings | File Templates.
*/
public class FormattedFieldBindingBuilder<T>
{
   private Format<T> formatter;
   private FormModel formModel;
   private Class<T> valueType;

   protected FormattedFieldBindingBuilder(FormModel formModel, Class<T> type, Format<T> formatter)
   {
      this.formModel = formModel;
      valueType = type;
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
   
   public <S> ConvertedFormattedFieldBuilder<T,S> convertedFrom(ValueModel<S> source)
   {
      return new ConvertedFormattedFieldBuilder<T,S>(formModel, source, formatter);
   }
}
