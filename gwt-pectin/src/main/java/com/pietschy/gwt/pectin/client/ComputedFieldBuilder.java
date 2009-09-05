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

import com.pietschy.gwt.pectin.client.value.Function;
import com.pietschy.gwt.pectin.client.value.ValueModel;
import com.pietschy.gwt.pectin.client.value.ValueModelFunction;

import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
* User: andrew
* Date: Sep 5, 2009
* Time: 10:40:33 AM
* To change this template use File | Settings | File Templates.
*/
public class ComputedFieldBuilder<T, S>
{
   private FormModel formModel;
   private List<ValueModel<S>> models;

   public ComputedFieldBuilder(FormModel formModel, ValueModel<S>... models)
   {
      this.formModel = formModel;
      this.models = Arrays.asList(models);
   }

   public FieldModel<T> using(Function<T, S> function)
   {
      ValueModelFunction<T, S> valueModel = new ValueModelFunction<T, S>(function);
      valueModel.addSourceModels(models);
      return formModel.createFieldModel(valueModel);
   }
}
