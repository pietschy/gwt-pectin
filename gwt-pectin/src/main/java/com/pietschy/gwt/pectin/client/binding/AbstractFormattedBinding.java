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

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.pietschy.gwt.pectin.client.FormattedFieldModel;
import com.pietschy.gwt.pectin.client.value.GuardedValueChangeHandler;
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
   private GuardedValueChangeHandler<String> fieldMonitor = new GuardedValueChangeHandler<String>()
   {
      public void onGuardedValueChanged(ValueChangeEvent<String> event)
      {
         updateTarget(event.getValue());
      }
   };

   private FormattedFieldModel<T> model;
   private MutableValueModel<String> textModel;

   public AbstractFormattedBinding(FormattedFieldModel<T> model)
   {
      this.model = model;
      textModel = model.getTextModel();
      registerDisposable(textModel.addValueChangeHandler(fieldMonitor));
   }

   public FormattedFieldModel<T> getModel()
   {
      return model;
   }

   protected abstract void updateTarget(String value);

   public void updateTarget()
   {
      updateTarget(textModel.getValue());
   }

   protected void onWidgetChanged(String text)
   {
      fieldMonitor.setIgnoreEvents(true);
      try
      {
         model.getTextModel().setValue(text);
      }
      finally
      {
         fieldMonitor.setIgnoreEvents(false);
      }
   }

}