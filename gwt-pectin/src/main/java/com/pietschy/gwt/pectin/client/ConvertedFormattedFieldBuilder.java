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
import com.pietschy.gwt.pectin.client.value.*;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Sep 5, 2009
 * Time: 10:40:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConvertedFormattedFieldBuilder<T, S>
{
   private FormModel formModel;
   private ValueModel<S> from;
   private Format<T> format;
   private FormatExceptionPolicy<T> exceptionPolicy;
   private Class<T> valueType;

   protected ConvertedFormattedFieldBuilder(FormModel formModel, ValueModel<S> from, Format<T> format, FormatExceptionPolicy<T> exceptionPolicy, Class<T> valueType)
   {
      this.formModel = formModel;
      this.from = from;
      this.format = format;
      this.exceptionPolicy = exceptionPolicy;
      this.valueType = valueType;
   }

   public FormattedFieldModel<T> using(Converter<T, S> converter)
   {
      return formModel.createFormattedFieldModel(createConvertingModel(converter), format, exceptionPolicy, valueType);
   }

   protected ConvertingValueModel<T, S> createConvertingModel(Converter<T, S> converter)
   {
      return from instanceof MutableValueModel 
         ? new ConvertingMutableValueModel<T, S>((MutableValueModel<S>) from, converter) 
         : new ConvertingValueModel<T, S>(from, converter);
   }
}