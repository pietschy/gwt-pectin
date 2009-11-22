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

package com.pietschy.gwt.pectin.client.components;

import com.pietschy.gwt.pectin.client.validation.IndexedValidationResult;
import com.pietschy.gwt.pectin.client.validation.component.IndexedValidationDisplay;
import com.pietschy.gwt.pectin.client.validation.component.ValidationDisplay;

/**
 * A subclass of {@link TextSplitter} that implements {@link IndexedValidationDisplay} and
 * delegates to either an {@link EnhancedTextBox} or {@link EnhancedTextArea}.
 */
public class EnhancedTextSplitter extends TextSplitter
implements IndexedValidationDisplay
{
   public EnhancedTextSplitter(EnhancedTextBox textBox)
   {
      super(textBox);
   }

   public EnhancedTextSplitter(EnhancedTextBox textBox, String splitRegex, String joinText)
   {
      super(textBox, splitRegex, joinText);
   }

   public EnhancedTextSplitter(EnhancedTextArea textBox)
   {
      super(textBox);
   }
   public EnhancedTextSplitter(EnhancedTextArea textBox, String splitRegex, String joinText)
   {
      super(textBox, splitRegex, joinText);
   }

   public void setValidationResult(IndexedValidationResult result)
   {
      ((ValidationDisplay) getTextBox()).setValidationResult(result);
   }
}


