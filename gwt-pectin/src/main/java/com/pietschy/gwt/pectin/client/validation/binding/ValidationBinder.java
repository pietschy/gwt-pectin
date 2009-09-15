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

import com.pietschy.gwt.pectin.client.FieldModel;
import com.pietschy.gwt.pectin.client.FormattedFieldModel;
import com.pietschy.gwt.pectin.client.ListFieldModel;
import com.pietschy.gwt.pectin.client.binding.AbstractBinder;
import com.pietschy.gwt.pectin.client.validation.ValidationPlugin;
import com.pietschy.gwt.pectin.client.validation.component.StyleApplicator;

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
   private StyleApplicator styleApplicator;

   public ValidationBinder()
   {
      this(StyleApplicator.defaultInstance());
   }

   public ValidationBinder(StyleApplicator styleApplicator)
   {
      if (styleApplicator == null)
      {
         throw new NullPointerException("styleApplicator is null");
      }
      this.styleApplicator = styleApplicator;
   }

   public ValidationBindingBuider bindValidationOf(FieldModel<?> field)
   {
      return new ValidationBindingBuider(this, ValidationPlugin.getFieldValidator(field), styleApplicator);
   }
   
   public ValidationBindingBuider bindValidationOf(FormattedFieldModel<?> field)
   {
      return new ValidationBindingBuider(this, ValidationPlugin.getFieldValidator(field), styleApplicator);
   }
   
   public IndexedValidationBindingBuider bindValidationOf(ListFieldModel<?> field)
   {
      return new IndexedValidationBindingBuider(this, ValidationPlugin.getFieldValidator(field), styleApplicator);
   }

}
