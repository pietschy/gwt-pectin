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

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.pietschy.gwt.pectin.client.value.GuardedValueChangeHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * This class can be used to turn a regular TextBox or TextArea into a HasValue&lt;Collection&lt;String&gt;&gt; based on a
 * regular expression.
 */
public class TextSplitter extends Composite implements HasValue<Collection<String>>, Focusable, HasAllFocusHandlers
{
   private static final String DEFAULT_SPLIT_REGEX = ",";
   private static final String DEFAULT_JOIN_TEXT = ", ";

   private GuardedValueChangeHandler<String> textChangeHandler = new GuardedValueChangeHandler<String>() {
      public void onGuardedValueChanged(ValueChangeEvent<String> event)
      {
         fireValueChanged(getValue());
      }
   };

   private TextBoxBase textBox;
   private String splitRegex;
   private String joinText;
   private boolean keepEmptyValues = false;

   public TextSplitter(TextBoxBase textBox)
   {
      this(textBox, DEFAULT_SPLIT_REGEX, DEFAULT_JOIN_TEXT);
   }

   public TextSplitter(TextBoxBase textBox, String splitRegex, String joinText)
   {
      this.splitRegex = splitRegex;
      this.joinText = joinText;
      this.textBox = textBox;
      textBox.addValueChangeHandler(textChangeHandler);
      initWidget(textBox);
   }

   public TextBoxBase getTextBox()
   {
      return textBox;
   }

   public boolean isKeepEmptyValues()
   {
      return keepEmptyValues;
   }

   public void setKeepEmptyValues(boolean keepEmptyValues)
   {
      this.keepEmptyValues = keepEmptyValues;
   }

   public Collection<String> getValue()
   {
      return split(textBox.getText());
   }

   public void setValue(Collection<String> value)
   {
      setValue(value, false);
   }

   public void setValue(Collection<String> value, boolean fireEvents)
   {
      try
      {
         textChangeHandler.setIgnoreEvents(true);
         textBox.setText(join(value));

         if (fireEvents)
         {
            fireValueChanged(value);
         }
      }
      finally
      {
         textChangeHandler.setIgnoreEvents(false);
      }
   }

   protected void fireValueChanged(Collection<String> value)
   {
      ValueChangeEvent.fire(this, value);
   }

   public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Collection<String>> handler)
   {
      return addHandler(handler, ValueChangeEvent.getType());
   }

   protected Collection<String> split(String text)
   {
      if (text == null || text.trim().length() < 1)
      {
         return Collections.emptyList();
      }

      String[] strings = text.split(splitRegex);
      ArrayList<String> result = new ArrayList<String>(strings.length);
      for (String string : strings)
      {
         String cleanValue = cleanValue(string);
         if (isKeepEmptyValues() || (cleanValue != null && cleanValue.trim().length() > 0))
         {
            result.add(cleanValue);
         }
      }

      return result;
   }

   protected String cleanValue(String string)
   {
      return string != null ? string.trim() : "";
   }

   protected String join(Collection<String> values)
   {
      StringBuilder buf = null;
      for (String value : values)
      {
         if (buf == null)
         {
            buf = new StringBuilder();
         }
         else
         {
            buf.append(joinText);
         }
         buf.append(value);
      }

      return buf != null ? buf.toString() : "";
   }

   public int getTabIndex()
   {
      return textBox.getTabIndex();
   }

   public void setAccessKey(char key)
   {
      textBox.setAccessKey(key);
   }

   public void setFocus(boolean focused)
   {
      textBox.setFocus(focused);
   }

   public void setTabIndex(int index)
   {
      textBox.setTabIndex(index);
   }

   public HandlerRegistration addFocusHandler(FocusHandler handler)
   {
      return textBox.addFocusHandler(handler);
   }

   public HandlerRegistration addBlurHandler(BlurHandler handler)
   {
      return textBox.addBlurHandler(handler);
   }
}
