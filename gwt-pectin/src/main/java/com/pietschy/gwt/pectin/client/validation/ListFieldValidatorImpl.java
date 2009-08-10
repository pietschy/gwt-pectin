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

package com.pietschy.gwt.pectin.client.validation;

import com.pietschy.gwt.pectin.client.ListFieldModel;
import com.pietschy.gwt.pectin.client.value.ValueModel;
import com.pietschy.gwt.pectin.client.validation.message.ValidationMessage;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.GwtEvent;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 13, 2009
 * Time: 9:47:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class ListFieldValidatorImpl<T> implements ListFieldValidator<T>
{
   private HandlerManager handlers = new HandlerManager(this);
   
   private ListFieldModel<T> fieldModel;
   private IndexedValidationResultImpl validationResult = new IndexedValidationResultImpl();
   
   private LinkedHashMap<ListValidator<? super T>, ValueModel<Boolean>> validators = new LinkedHashMap<ListValidator<? super T>, ValueModel<Boolean>>();

   public ListFieldValidatorImpl(ListFieldModel<T> fieldModel)
   {
      this.fieldModel = fieldModel;
   }

   public void addValidator(ListValidator<? super T> validator, ValueModel<Boolean> condition)
   {
      validators.put(validator, condition);
   }

   public void runValidators(List<T> values, IndexedValidationResultCollector collector)
   {
      for (Map.Entry<ListValidator<? super T>, ValueModel<Boolean>> entry : validators.entrySet())
      {
         ListValidator<? super T> validator = entry.getKey();
         ValueModel<Boolean> condition = entry.getValue();
         if (condition.getValue())
         {
            validator.validate(values, collector);
         }
      }
   }

   public void validate()
   {
      IndexedValidationResultImpl result = new IndexedValidationResultImpl();
      runValidators(fieldModel.asUnmodifiableList(), result);
      setValidationResult(result);
   }

   public IndexedValidationResult getValidationResult()
   {
      return validationResult;
   }

   private void setValidationResult(IndexedValidationResultImpl result)
   {
      validationResult = result;
      fireValidationChanged();
   }

   public void addExternalMessage(int index, ValidationMessage message)
   {
      validationResult.add(index, message);
      fireValidationChanged();
   }

   public void clear()
   {
      setValidationResult(new IndexedValidationResultImpl());
   }

   private void fireValidationChanged()
   {
      IndexedValidationEvent.fire(this, validationResult);
   }

   public HandlerRegistration addValidationHandler(IndexedValidationHandler handler)
   {
      return handlers.addHandler(IndexedValidationEvent.getType(), handler);
   }

   public void fireEvent(GwtEvent<?> event)
   {
      handlers.fireEvent(event);
   }

}
