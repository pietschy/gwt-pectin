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

package com.pietschy.gwt.pectin.client.metadata.binding;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.pietschy.gwt.pectin.client.binding.AbstractBinding;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 *
 */
public class WatermarkBinding extends AbstractBinding
   implements BlurHandler, FocusHandler, ValueChangeHandler<String>
{
   private static final String WATERMARK_STYLE = "pectin-Watermark";

   private ValueModel<String> valueModel;
   private ValueModel<String> watermarkModel;
   private TextBox textBox;
   private boolean focused;
   private boolean watermarkApplied;

   public WatermarkBinding(ValueModel<String> value, ValueModel<String> watermark, TextBox widget)
   {
      this.valueModel = value;
      this.watermarkModel = watermark;
      this.textBox = widget;
      registerDisposable(valueModel.addValueChangeHandler(this));
      registerDisposable(watermarkModel.addValueChangeHandler(this));
      registerDisposable(textBox.addBlurHandler(this));
      registerDisposable(textBox.addFocusHandler(this));
   }

   /**
    * This binding works by clobbering the value in the widget without fireing events.  It
    * only works because this binding is added after the value binding and get's to have the
    * last say.  This would fail if other bindings invoked after this one were to also clobber
    * the value.
    */
   public void updateTarget()
   {
      if (!focused && isEmpty(valueModel) && !isEmpty(watermarkModel))
      {
         ensureWatermarked();
      }
      else
      {
         ensureNotWatermarked();
      }
   }

   private void ensureWatermarked()
   {
      // we apply not matter what, since we may be reapplying the watermark
      // text after the watermark value changed.
      watermarkApplied = true;
      textBox.setValue(watermarkModel.getValue(), false);
      textBox.addStyleName(WATERMARK_STYLE);
   }

   private void ensureNotWatermarked()
   {
      if (watermarkApplied)
      {
         textBox.setValue(valueModel.getValue(), false);
         textBox.removeStyleName(WATERMARK_STYLE);
         watermarkApplied = false;
      }
   }

   private boolean isEmpty(ValueModel<String> model)
   {
      return model.getValue() == null || model.getValue().trim().length() < 1;
   }

   public Object getTarget()
   {
      return textBox;
   }

   public void onBlur(BlurEvent event)
   {
      focused = false;
      updateTarget();
   }

   public void onFocus(FocusEvent event)
   {
      focused = true;
      updateTarget();
   }

   public void onValueChange(ValueChangeEvent<String> event)
   {
      // this is called for both watermark and value changes
      updateTarget();
   }
}
