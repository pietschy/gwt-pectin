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

package com.pietschy.gwt.pectin.client.form.validation.binding;

import com.google.gwt.user.client.ui.UIObject;
import com.pietschy.gwt.pectin.client.binding.AbstractBinding;
import com.pietschy.gwt.pectin.client.form.validation.*;
import com.pietschy.gwt.pectin.client.form.validation.component.ValidationStyles;


/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 17, 2009
 * Time: 1:06:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValidationStyleBinding
extends AbstractBinding
implements ValidationHandler, IndexedValidationHandler
{
   private HasValidationResult validator;
   private UIObject widget;
   private ValidationStyles validationStyles;

   public ValidationStyleBinding(HasValidationResult validator, UIObject widget, ValidationStyles applicator)
   {
      this.validator = validator;
      this.widget = widget;
      validationStyles = applicator;
      registerDisposable(validator.addValidationHandler(this));
   }

   public ValidationStyleBinding(HasIndexedValidationResult validator, UIObject widget, ValidationStyles applicator)
   {
      this.validator = validator;
      this.widget = widget;
      validationStyles = applicator;
      registerDisposable(validator.addValidationHandler((IndexedValidationHandler) this));
   }

   public void updateTarget()
   {
      updateStyles(validator.getValidationResult());
   }

   public UIObject getTarget()
   {
      return widget;
   }

   private void updateStyles(ValidationResult result)
   {
      validationStyles.applyStyle(widget, result);
   }

   public void onValidate(ValidationEvent event)
   {
      updateStyles(event.getValidationResult());
   }

   public void onValidate(IndexedValidationEvent event)
   {
      updateStyles(event.getValidationResult());
   }
}