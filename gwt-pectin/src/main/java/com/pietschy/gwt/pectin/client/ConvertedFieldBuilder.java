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

import com.pietschy.gwt.pectin.client.value.Converter;
import com.pietschy.gwt.pectin.client.value.ConvertingMutableValueModel;
import com.pietschy.gwt.pectin.client.value.ConvertingValueModel;
import com.pietschy.gwt.pectin.client.value.MutableValueModel;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * Created by IntelliJ IDEA.
* User: andrew
* Date: Sep 5, 2009
* Time: 10:40:21 AM
* To change this template use File | Settings | File Templates.
*/
public class ConvertedFieldBuilder<T, S>
{
   private FormModel formModel;
   private ValueModel<S> from;

   protected ConvertedFieldBuilder(FormModel formModel, ValueModel<S> from)
   {
      this.formModel = formModel;
      this.from = from;
   }

   public FieldModel<T> using(Converter<T, S> converter)
   {
      if (from instanceof MutableValueModel)
      {
         ValueModel<T> vm = new ConvertingMutableValueModel<T, S>((MutableValueModel<S>) from, converter);
         return formModel.createFieldModel(vm);
      }
      else
      {
         return formModel.createFieldModel(new ConvertingValueModel<T, S>(from, converter));
      }
   }
}
