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
import com.pietschy.gwt.pectin.client.value.ValueModelFunction;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import com.pietschy.gwt.pectin.client.value.MutableValueModel;
import com.pietschy.gwt.pectin.client.value.ConvertingValueModel;
import com.pietschy.gwt.pectin.client.value.ConvertingMutableValueModel;
import com.pietschy.gwt.pectin.client.value.Converter;
import com.pietschy.gwt.pectin.client.value.Function;

import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 12:20:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class FieldBuilder<T>
{
   private FormModel formModel;
   private Class<T> valueType;

   protected FieldBuilder(FormModel formModel, Class<T> valueType)
   {
      this.formModel = formModel;
      this.valueType = valueType;
   }
   
   public FieldModel<T> create()
   {
      return formModel.createFieldModel(new ValueHolder<T>());
   }
   
   public FieldModel<T> createWithValue(T initialValue)
   {
      return formModel.createFieldModel(new ValueHolder<T>(initialValue));
   }

   public FieldModel<T> boundTo(ValueModel<T> source)
   {
      return formModel.createFieldModel(source);
   }
   
   public FieldModel<T> boundTo(ValueModelProvider provider, String propertyName)
   {
      return boundTo(provider.getValueModel(propertyName, valueType));
   }

   public <S> ConvertedFieldBuilder<S> convertedFrom(ValueModel<S> source)
   {
      return new ConvertedFieldBuilder<S>(source);
   }

   public <S> ComputedFieldBuilder<S> computedFrom(ValueModel<S>... source)
   {
      return new ComputedFieldBuilder<S>(source);
   }


   public class ConvertedFieldBuilder<S>
   {
      private ValueModel<S> from;

      protected ConvertedFieldBuilder(ValueModel<S> from)
      {
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


   public class ComputedFieldBuilder<S>
   {
      private List<ValueModel<S>> models;

      public ComputedFieldBuilder(ValueModel<S>... models)
      {
         this.models = Arrays.asList(models);
      }

      public FieldModel<T> using(Function<T, S> function)
      {
         ValueModelFunction<T, S> valueModel = new ValueModelFunction<T, S>(function);
         valueModel.addSourceModels(models);
         return formModel.createFieldModel(valueModel);
      }
   }
}
