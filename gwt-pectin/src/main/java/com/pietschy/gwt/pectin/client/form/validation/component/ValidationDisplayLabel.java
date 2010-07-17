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

package com.pietschy.gwt.pectin.client.form.validation.component;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHTML;
import com.pietschy.gwt.pectin.client.form.validation.IndexedValidationResult;
import com.pietschy.gwt.pectin.client.form.validation.Severity;
import com.pietschy.gwt.pectin.client.form.validation.ValidationResult;
import com.pietschy.gwt.pectin.client.form.validation.message.ValidationMessage;

/**
 * This class displays validation message in the form of an InlineLabel.  Only one message is
 * displayed from the {@link com.pietschy.gwt.pectin.client.form.validation.ValidationResult}.  The message choosen
 * is the first message to be added at the highest {@link com.pietschy.gwt.pectin.client.form.validation.Severity}.
 * <p>
 * <b>CSS</b>
 * <pre>
 * pectin-ValidationDisplayLabel
 * pectin-ValidationDisplayLabel-validationError
 * pectin-ValidationDisplayLabel-validationWarning
 * pectin-ValidationDisplayLabel-validationInfo
 * </pre>
 */
public class ValidationDisplayLabel
extends Composite
implements ValidationDisplay, IndexedValidationDisplay
{
   private InlineHTML label = new InlineHTML();

   private ValidationStyles validationStyles = ValidationStyles.defaultDependentStyleNameInstance();

   public ValidationDisplayLabel()
   {
      initWidget(label);
      setStylePrimaryName("pectin-ValidationDisplayLabel");
      clearLabel();
   }

   public void setValidationResult(ValidationResult result)
   {
      updateDisplay(result);
   }

   public void setValidationResult(IndexedValidationResult result)
   {
      updateDisplay(result);
   }

   private void updateDisplay(ValidationResult result)
   {
      if (result.isEmpty())
      {
         clearLabel();
      }
      else
      {
         ValidationMessage message = getHightestSeverityMessage(result);
         label.setText(message.getMessage());
         validationStyles.applyStyle(label, message.getSeverity());
      }
   }

   protected ValidationMessage getHightestSeverityMessage(ValidationResult result)
   {
      Severity worstSeverity = result.getSeverities().iterator().next();
      return result.getMessages(worstSeverity).get(0);

   }

   private void clearLabel()
   {
      label.setHTML("&nbsp;");
   }
}
