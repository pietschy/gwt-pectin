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

package com.pietschy.gwt.pectin.client.validation.component;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHTML;
import com.pietschy.gwt.pectin.client.validation.ValidationEvent;
import com.pietschy.gwt.pectin.client.validation.ValidationHandler;
import com.pietschy.gwt.pectin.client.validation.ValidationResult;
import com.pietschy.gwt.pectin.client.validation.message.ValidationMessage;

import java.util.List;

/**
 * Class
 * <pre>
 * gwt-form-ValidationMessageDisplay
 * </pre>
 */
public class ValidationMessageDisplay 
extends Composite
implements ValidationHandler, ValidationDisplay
{
   private InlineHTML label = new InlineHTML();
   
   public ValidationMessageDisplay()
   {
      initWidget(label);
      setStylePrimaryName("gwt-pectin-ValidationMessageDisplay");
      clearLabel();
   }

   public void onValidate(ValidationEvent event)
   {
      setValidationResult(event.getValidationResult());
   }

   public void setValidationResult(ValidationResult result)
   {
      List<ValidationMessage> messages = result.getMessages();
      if (messages.size() > 0)
      {
         label.setText(messages.get(0).getMessage());
      }
      else
      {
         clearLabel();
      }
   }

   private void clearLabel()
   {
      label.setHTML("&nbsp;");
   }
}
