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

package com.pietschy.gwt.pectin.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.pietschy.gwt.pectin.client.format.Format;
import com.pietschy.gwt.pectin.client.format.FormatException;
import com.pietschy.gwt.pectin.client.value.GuardedValueChangeHandler;
import com.pietschy.gwt.pectin.client.value.MutableValueModel;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Aug 5, 2009
 * Time: 1:02:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormattedFieldModelImpl<T>
extends AbstractFieldModelBase<T>
implements FormattedFieldModel<T>
{
   private Format<T> format;
   private MutableValueModel<String> textModel = new ValueHolder<String>();
   private FormatExceptionPolicy<T> formatExceptionPolicy = new FormatExceptionPolicy<T>()
   {
      public void onFormatException(FormattedFieldModel<T> model, FormatException e)
      {
         // do nothing.
      }
   };

   private GuardedValueChangeHandler<T> valueMonitor = new GuardedValueChangeHandler<T>()
   {
      public void onGuardedValueChanged(ValueChangeEvent<T> event)
      {
         writeValueToText(event.getValue());
      }
   };

   private GuardedValueChangeHandler<String> textMonitor = new GuardedValueChangeHandler<String>()
   {
      public void onGuardedValueChanged(ValueChangeEvent<String> event)
      {
         writeTextToValue(event.getValue());
      }
   };
   
   private ValueChangeHandler<Format<T>> formatMonitor = new ValueChangeHandler<Format<T>>()
   {
      public void onValueChange(ValueChangeEvent<Format<T>> event)
      {
         writeValueToText(getValue());
      }
   };


   public FormattedFieldModelImpl(FormModel formModel, ValueModel<T> source, Format<T> format, Class valueType)
   {
      super(formModel, source, valueType);
      setFormat(format);

      addValueChangeHandler(valueMonitor);
      textModel.addValueChangeHandler(textMonitor);
   }

   public MutableValueModel<String> getTextModel()
   {
      return textModel;
   }

   public void setFormat(Format<T> format)
   {
      if (format == null)
      {
         throw new NullPointerException("format is null");
      }
      
      this.format = format;
      
      // this will trigger the text model to update using
      // the new format.
      writeValueToText(getValue());
   }

   public Format<T> getFormat()
   {
      return format;
   }

   public void setFormatExceptionPolicy(FormatExceptionPolicy<T> formatExceptionPolicy)
   {
      this.formatExceptionPolicy = formatExceptionPolicy;
   }

   protected void writeValueToText(T value)
   {
      try
      {
         textMonitor.setIgnoreEvents(true);
         textModel.setValue(getFormat().format(value));
      }
      finally
      {
         textMonitor.setIgnoreEvents(false);
      }
   }

   protected void writeTextToValue(String value)
   {
      try
      {
         valueMonitor.setIgnoreEvents(true);
         try
         {
            setValue(getFormat().parse(value));
         }
         catch (FormatException e)
         {
            formatExceptionPolicy.onFormatException(this, e);
         }
      }
      finally
      {
         valueMonitor.setIgnoreEvents(false);
      }
   }


}
