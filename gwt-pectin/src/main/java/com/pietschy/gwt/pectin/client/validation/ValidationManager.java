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


import com.pietschy.gwt.pectin.client.BindingCallback;
import com.pietschy.gwt.pectin.client.FieldModel;
import com.pietschy.gwt.pectin.client.FormattedFieldModel;
import com.pietschy.gwt.pectin.client.ListFieldModel;
import com.pietschy.gwt.pectin.client.binding.AbstractFieldBinding;
import com.pietschy.gwt.pectin.client.binding.AbstractFormattedBinding;
import com.pietschy.gwt.pectin.client.binding.AbstractListBinding;
import com.pietschy.gwt.pectin.client.binding.BindingContainer;
import com.pietschy.gwt.pectin.client.validation.binding.IndexedValidationDisplayBinding;
import com.pietschy.gwt.pectin.client.validation.binding.ValidationDisplayBinding;
import com.pietschy.gwt.pectin.client.validation.component.IndexedValidationDisplay;
import com.pietschy.gwt.pectin.client.validation.component.ValidationDisplay;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jun 19, 2008
 * Time: 12:27:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValidationManager
implements BindingCallback
{
   private HashMap<FieldModel<?>, FieldValidatorImpl<?>> fieldValidators = new HashMap<FieldModel<?>, FieldValidatorImpl<?>>();
   
   private HashMap<FormattedFieldModel<?>, FormattedFieldValidatorImpl<?>> formattedFieldValidators = new HashMap<FormattedFieldModel<?>, FormattedFieldValidatorImpl<?>>();
   
   private HashMap<ListFieldModel<?>, ListFieldValidatorImpl<?>> listFieldValidators = new HashMap<ListFieldModel<?>, ListFieldValidatorImpl<?>>();
   
   
   @SuppressWarnings("unchecked")
   public <T> FieldValidatorImpl<T> getFieldValidator(FieldModel<T> field, boolean create)
   {
      FieldValidatorImpl<T> validator = (FieldValidatorImpl<T>) fieldValidators.get(field);
      
      if (validator == null && create)
      {
         validator = new FieldValidatorImpl<T>(field);
         fieldValidators.put(field, validator);
      }
      
      return validator;
   }
   
   @SuppressWarnings("unchecked")
   public <T> FormattedFieldValidatorImpl<T> getFieldValidator(FormattedFieldModel<T> field, boolean create)
   {
      FormattedFieldValidatorImpl<T> validator = (FormattedFieldValidatorImpl<T>) formattedFieldValidators.get(field);
      
      if (validator == null && create)
      {
         validator = new FormattedFieldValidatorImpl<T>(field);
         formattedFieldValidators.put(field, validator);
      }
      
      return validator;
   }
   

   @SuppressWarnings("unchecked")
   public <T> ListFieldValidatorImpl<T> getFieldValidator(ListFieldModel<T> field, boolean create)
   {
      ListFieldValidatorImpl<T> validator = (ListFieldValidatorImpl<T>) listFieldValidators.get(field);
      
      if (validator == null && create)
      {
         validator = new ListFieldValidatorImpl<T>(field);
         listFieldValidators.put(field, validator);
      }
      
      return validator;
   }
   
   public boolean validate()
   {
      boolean valid = true;

      for (FieldValidatorImpl<?> validator : fieldValidators.values())
      {
         validator.validate();
         if (validator.getValidationResult().contains(Severity.ERROR))
         {
            valid = false;
         }
      }

      for (FormattedFieldValidatorImpl<?> validator : formattedFieldValidators.values())
      {
         validator.validate();
         if (validator.getValidationResult().contains(Severity.ERROR))
         {
            valid = false;
         }
      }

      for (ListFieldValidatorImpl<?> validator : listFieldValidators.values())
      {
         validator.validate();
         if (validator.getValidationResult().contains(Severity.ERROR))
         {
            valid = false;
         }
      }
      
      return valid;
   }
   
   public void clearValidation()
   {
      for (FieldValidatorImpl<?> validator : fieldValidators.values())
      {
         validator.clear();
      }

      for (ListFieldValidatorImpl<?> validator : listFieldValidators.values())
      {
         validator.clear();
      }
   }
   
   public <T> void onWidgetBinding(final AbstractFieldBinding<T> binding, FieldModel<T> field, Object target)
   {
      doBinding(binding, target, getFieldValidator(field, false));
   }

   public <T> void onWidgetBinding(AbstractFormattedBinding<T> binding, FormattedFieldModel<T> field, Object target)
   {
      doBinding(binding, target, getFieldValidator(field, false));
   }

   private <T> void doBinding(BindingContainer binding, Object target, FieldValidator<T> fieldValidator)
   {
      if (fieldValidator != null)
      {
         if (target instanceof ValidationDisplay)
         {
            ValidationDisplayBinding validationBinding = new ValidationDisplayBinding(fieldValidator, (ValidationDisplay) target);
            validationBinding.updateTarget();

            binding.registerAndInitialiseBinding(validationBinding);
         }
      }
   }

   public <T> void onWidgetBinding(AbstractListBinding binding, ListFieldModel<T> field, Object target)
   {
      ListFieldValidator<T> fieldValidator = getFieldValidator(field, false);

      if (fieldValidator != null)
      {
         if (target instanceof IndexedValidationDisplay)
         {
            IndexedValidationDisplayBinding validationBinding = new IndexedValidationDisplayBinding(fieldValidator, (IndexedValidationDisplay) target);
            validationBinding.updateTarget();
            binding.registerAndInitialiseBinding(validationBinding);
         }
      }
   }

}