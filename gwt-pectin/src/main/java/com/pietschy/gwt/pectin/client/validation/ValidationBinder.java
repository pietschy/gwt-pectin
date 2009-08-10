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

import com.pietschy.gwt.pectin.client.FieldModel;
import com.pietschy.gwt.pectin.client.ListFieldModel;
import com.pietschy.gwt.pectin.client.FormattedFieldModel;
import com.pietschy.gwt.pectin.client.binding.AbstractBinder;
import com.pietschy.gwt.pectin.client.validation.component.IndexedValidationDisplay;
import com.pietschy.gwt.pectin.client.validation.component.ValidationDisplay;
import com.pietschy.gwt.pectin.client.validation.binding.ValidationDisplayBinding;
import com.pietschy.gwt.pectin.client.validation.binding.IndexedValidationDisplayBinding;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 13, 2009
 * Time: 12:44:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValidationBinder
extends AbstractBinder
{
   public ValidationBindingBuider displayValidationOf(FieldModel<?> field)
   {
      return new ValidationBindingBuider(ValidationPlugin.getFieldValidator(field));
   }
   
   public ValidationBindingBuider displayValidationOf(FormattedFieldModel<?> field)
   {
      return new ValidationBindingBuider(ValidationPlugin.getFieldValidator(field));
   }
   
   public IndexedValidationBindingBuider displayValidationOf(ListFieldModel<?> field)
   {
      return new IndexedValidationBindingBuider(field);
   }
   
   public class ValidationBindingBuider 
   {
      protected FieldValidator<?> validator;

      public ValidationBindingBuider(FieldValidator<?> validator)
      {
         this.validator = validator;
      }
      
      public void using(final ValidationDisplay validationDisplay)
      {
         registerBinding(new ValidationDisplayBinding(validator, validationDisplay));
      }
   }
   
   public class IndexedValidationBindingBuider 
   {
      private ListFieldModel<?> field;

      public IndexedValidationBindingBuider(ListFieldModel<?> field)
      {
         this.field = field;
      }
      
      public void using(final IndexedValidationDisplay validationDisplay)
      {
         registerBinding(new IndexedValidationDisplayBinding(ValidationPlugin.getFieldValidator(field), validationDisplay));
      }
      
   }
}
