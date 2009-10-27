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

package com.pietschy.gwt.pectin.demo.client.style;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.pietschy.gwt.pectin.client.FieldModel;
import com.pietschy.gwt.pectin.client.FormModel;
import static com.pietschy.gwt.pectin.client.metadata.MetadataPlugin.enable;
import static com.pietschy.gwt.pectin.client.validation.ValidationPlugin.getValidationManager;
import static com.pietschy.gwt.pectin.client.validation.ValidationPlugin.validateField;
import com.pietschy.gwt.pectin.client.validation.validator.NotEmptyValidator;

/**
 * There's nothing much to this model.  The style bindings in the form are
 * of more interest in this example.
 */
public class StyleFormModel
extends FormModel
{
   protected final FieldModel<String> title;
   protected final FieldModel<String> name;
   protected final FieldModel<Boolean> hasNickName;
   protected final FieldModel<String> nickName;

   public StyleFormModel()
   {
      title = fieldOfType(String.class).create();

      // create a validation field...
      name = fieldOfType(String.class).createWithValue("Fred");
      validateField(name).using(new NotEmptyValidator("Name is required"));
      
      // create an enabled field...
      hasNickName = fieldOfType(Boolean.class).createWithValue(false);
      nickName = fieldOfType(String.class).create();
      enable(nickName).when(hasNickName);
      
      // the validation plugin doesn't have VALIDATE_ON_EDIT functionality
      // yet so we do it manually. 
      name.addValueChangeHandler(new ValueChangeHandler<String>()
      {
         public void onValueChange(ValueChangeEvent<String> event)
         {
            validate();
         }
      });
   }

   public void validate()
   {
      getValidationManager(this).validate();
   }
}
