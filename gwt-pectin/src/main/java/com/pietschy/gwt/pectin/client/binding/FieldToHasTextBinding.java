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

import com.google.gwt.user.client.ui.HasText;
import com.pietschy.gwt.pectin.client.FieldModel;
import com.pietschy.gwt.pectin.client.format.DisplayFormat;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 4:35:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class FieldToHasTextBinding<T> 
extends AbstractFieldBinding<T> implements HasDisplayFormat<T>
{
   private HasText widget;
   private DisplayFormat<? super T> format;

   public FieldToHasTextBinding(FieldModel<T> field, HasText widget, DisplayFormat<? super T> format)
   {
      super(field);
      this.widget = widget;
      this.format = format;
   }

   protected void updateWidget(T value)
   {
      widget.setText(format.format(value));
   }

   public HasText getTarget()
   {
      return widget;
   }


   public void setFormat(DisplayFormat<? super T> format)
   {
      this.format = format;
      updateTarget();
   }
}
