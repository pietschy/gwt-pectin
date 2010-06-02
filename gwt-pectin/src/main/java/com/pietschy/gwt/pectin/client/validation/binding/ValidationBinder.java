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

package com.pietschy.gwt.pectin.client.validation.binding;

import com.pietschy.gwt.pectin.client.FieldModelBase;
import com.pietschy.gwt.pectin.client.FormModel;
import com.pietschy.gwt.pectin.client.ListFieldModelBase;
import com.pietschy.gwt.pectin.client.binding.AbstractBindingContainer;
import com.pietschy.gwt.pectin.client.validation.HasIndexedValidationResult;
import com.pietschy.gwt.pectin.client.validation.HasValidationResult;
import com.pietschy.gwt.pectin.client.validation.ValidationPlugin;
import com.pietschy.gwt.pectin.client.validation.component.ValidationStyles;

/**
 * ValidationBinder binds the validation status of a given field to arbitrary widgets.
 *
 * @see com.pietschy.gwt.pectin.client.validation.component.ValidationDisplay
 */
public class ValidationBinder extends AbstractBindingContainer
{
   private ValidationStyles validationStyles;

   /**
    * Creates a new binder instance.
    */
   public ValidationBinder()
   {
      this(ValidationStyles.defaultInstance());
   }

   /**
    * Creates a new instance that uses the specified {@link com.pietschy.gwt.pectin.client.validation.component.ValidationStyles} to apply styles
    * to widgets.
    *
    * @param validationStyles the StyleApplicator to use.
    */
   public ValidationBinder(ValidationStyles validationStyles)
   {
      if (validationStyles == null)
      {
         throw new NullPointerException("validationStyles is null");
      }
      this.validationStyles = validationStyles;
   }


   public ValidationBindingBuilder bindValidationOf(HasValidationResult hasValidation)
   {
      return new ValidationBindingBuilder(this, hasValidation, validationStyles);
   }

   public ValidationBindingBuilder bindValidationOf(FormModel form)
   {
      return bindValidationOf(ValidationPlugin.getValidationManager(form).getFormValidator());
   }

   public ValidationBindingBuilder bindValidationOf(FieldModelBase<?> field)
   {
      return bindValidationOf(ValidationPlugin.getFieldValidator(field));
   }

   public IndexedValidationBindingBuilder bindValidationOf(ListFieldModelBase<?> field)
   {
      return bindValidationOf(ValidationPlugin.getFieldValidator(field));
   }

   public IndexedValidationBindingBuilder bindValidationOf(HasIndexedValidationResult hasValidationResult)
   {
      return new IndexedValidationBindingBuilder(this, hasValidationResult, validationStyles);
   }

}
