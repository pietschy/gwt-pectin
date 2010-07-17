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
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.pietschy.gwt.pectin.client.form.validation.IndexedValidationResult;
import com.pietschy.gwt.pectin.client.form.validation.Severity;
import com.pietschy.gwt.pectin.client.form.validation.ValidationResult;
import com.pietschy.gwt.pectin.client.form.validation.message.ValidationMessage;

/**
 * This class displays validation messages in a vertical panel.
 * <p>
 * <b>CSS</b>
 * <pre>
 * div.pectin-ValidationDisplayPanel
 * div.pectin-ValidationDisplayPanel div.Message
 * div.pectin-ValidationDisplayPanel div.Message-validationError
 * div.pectin-ValidationDisplayPanel div.Message-validationWarning
 * div.pectin-ValidationDisplayPanel div.Message-validationInfo
 * </pre>
 */
public class ValidationDisplayPanel
extends Composite implements ValidationDisplay, IndexedValidationDisplay
{
   private ValidationStyles validationStyles = ValidationStyles.defaultDependentStyleNameInstance();

   private FlowPanel content = new FlowPanel();
   private int displayCount = 1;

   public ValidationDisplayPanel()
   {
      this(Integer.MAX_VALUE);
   }

   /**
    * Creates an instance that displays messages up the the specified number.
    * @param displayCount the maximum number of messages to display.
    */
   public ValidationDisplayPanel(int displayCount)
   {
      initWidget(content);
      setStylePrimaryName("pectin-ValidationDisplayPanel");
      this.displayCount = displayCount;
   }

   public void setValidationResult(ValidationResult validationResult)
   {
      int added = 0;
      if (!validationResult.isEmpty())
      {
         content.clear();
         for (Severity severity : validationResult.getSeverities())
         {
            for (ValidationMessage message : validationResult.getMessages(severity))
            {
               if (added++ > displayCount)
               {
                  setVisible(true);
                  return;
               }
               
               Label label = new Label(message.getMessage());
               label.setStylePrimaryName("Message");
               validationStyles.applyStyle(label, message.getSeverity());
               content.add(label);
               added++;
            }
         }
      }

      setVisible(added > 0);
   }

   public void setValidationResult(IndexedValidationResult result)
   {
      setValidationResult((ValidationResult) result);
   }

   public int getDisplayCount()
   {
      return displayCount;
   }

   public void setDisplayCount(int displayCount)
   {
      this.displayCount = displayCount;
   }
}
