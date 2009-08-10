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

package com.pietschy.gwt.pectin.client.validation.validator;

import com.pietschy.gwt.pectin.client.validation.Validator;
import com.pietschy.gwt.pectin.client.validation.ValidationResultCollector;
import com.pietschy.gwt.pectin.client.validation.message.ErrorMessage;

/**
 * 
 */
public class NotEmptyValidator
implements Validator<String>
{
   private String message;
   private String infoMessage;

   public NotEmptyValidator(String message)
   {
      this(message, null);
   }

   public NotEmptyValidator(String message, String infoMessage)
   {
      this.message = message;
      this.infoMessage = infoMessage;
   }

   public void validate(String value, ValidationResultCollector results)
   {
      if (value == null || value.trim().length() < 1)
      {
         results.add(new ErrorMessage(message, infoMessage));
      }
   }
}