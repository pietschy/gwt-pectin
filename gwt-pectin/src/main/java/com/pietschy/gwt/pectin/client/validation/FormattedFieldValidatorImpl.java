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

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.GwtEvent;
import com.pietschy.gwt.pectin.client.FormattedFieldModel;
import com.pietschy.gwt.pectin.client.validation.message.ValidationMessage;
import com.pietschy.gwt.pectin.client.value.ValueModel;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 13, 2009
 * Time: 9:10:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class FormattedFieldValidatorImpl<T> implements FieldValidator<T>
{
   private HandlerManager handlers = new HandlerManager(this);
   
   private FormattedFieldModel<T> fieldModel;
   private LinkedHashMap<Validator<? super String>, ValueModel<Boolean>> textValidators = new LinkedHashMap<Validator<? super String>, ValueModel<Boolean>>();
   private LinkedHashMap<Validator<? super T>, ValueModel<Boolean>> validators = new LinkedHashMap<Validator<? super T>, ValueModel<Boolean>>();
   
   private ValidationResultImpl validationResult = new ValidationResultImpl();

   public FormattedFieldValidatorImpl(FormattedFieldModel<T> fieldModel)
   {
      this.fieldModel = fieldModel;
   }

   public FormattedFieldModel<T> getFieldModel()
   {
      return fieldModel;
   }

   public void 
   addValidator(Validator<? super T> validator, ValueModel<Boolean> condition)
   {
      validators.put(validator, condition);
   }

 
   public void 
   addTextValidator(Validator<? super String> validator, ValueModel<Boolean> condition)
   {
      textValidators.put(validator, condition);
   }
 
   public void runTextValidators(String value, ValidationResultCollector collector)
   {
      for (Map.Entry<Validator<? super String>, ValueModel<Boolean>> entry : textValidators.entrySet())
      {
         ValueModel<Boolean> condition = entry.getValue();
         Validator<? super String> validator = entry.getKey();
         
         if (condition.getValue())
         {
            validator.validate(value, collector);
         }
      }
   }
   
   public void runValidators(T value, ValidationResultCollector collector)
   {
      for (Map.Entry<Validator<? super T>, ValueModel<Boolean>> entry : validators.entrySet())
      {
         ValueModel<Boolean> condition = entry.getValue();
         Validator<? super T> validator = entry.getKey();
         
         if (condition.getValue())
         {
            validator.validate(value, collector);
         }
      }
   }
   
   public void validate()
   {
      ValidationResultImpl result = new ValidationResultImpl();
      runTextValidators(fieldModel.getTextModel().getValue(), result);
      runValidators(fieldModel.getValue(), result);
      setValidationResult(result);
   }

   public ValidationResult getValidationResult()
   {
      return validationResult;
   }

   private void setValidationResult(ValidationResultImpl result)
   {
      if (result == null)
      {
         throw new NullPointerException("validationResult is null");
      }
      
      this.validationResult = result;
      fireValidationChanged();
   }

   private void fireValidationChanged()
   {
      ValidationEvent.fire(this, validationResult);
   }


   public void addExternalMessage(ValidationMessage message)
   {
      validationResult.add(message);
      fireValidationChanged();
   }

   public void clear()
   {
      setValidationResult(new ValidationResultImpl());
   }
   
   public HandlerRegistration addValidationHandler(ValidationHandler handler)
   {
      return handlers.addHandler(ValidationEvent.getType(), handler);
   }

   public void fireEvent(GwtEvent<?> event)
   {
      handlers.fireEvent(event);
   }

}