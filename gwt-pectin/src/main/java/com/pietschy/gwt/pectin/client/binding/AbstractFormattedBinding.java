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

package com.pietschy.gwt.pectin.client.binding;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.pietschy.gwt.pectin.client.FormattedFieldModel;
import com.pietschy.gwt.pectin.client.value.MutableValueModel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 4:53:36 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFormattedBinding<T> extends AbstractBinding
{
   protected FormattedFieldModel<T> field;
   private FieldMonitor fieldMonitor = new FieldMonitor();
   protected MutableValueModel<String> textModel;

   public AbstractFormattedBinding(FormattedFieldModel<T> field)
   {
      this.field = field;
      textModel = field.getTextModel();
      registerHandler(textModel.addValueChangeHandler(fieldMonitor));
   }

   public FormattedFieldModel<T> getFieldModel()
   {
      return field;
   }

   public abstract String getWidgetText();

   protected abstract void setWidgetText(String value);

   public void updateTarget()
   {
      onModelChanged(textModel.getValue());
   }

   private void onModelChanged(String value)
   {
      setWidgetText(value);
   }

   protected void onWidgetChanged(String text)
   {
      fieldMonitor.setIgnoreEvents(true);
      try
      {
         field.getTextModel().setValue(text);
      }
      finally
      {
         fieldMonitor.setIgnoreEvents(false);
      }
   }

   private class FieldMonitor implements ValueChangeHandler<String>
   {
      private boolean ignoreEvents = false;

      public void onValueChange(ValueChangeEvent<String> event)
      {
         if (!ignoreEvents)
         {
            onModelChanged(event.getValue());
         }
      }

      public void setIgnoreEvents(boolean ignoreEvents)
      {
         this.ignoreEvents = ignoreEvents;
      }
   }
}