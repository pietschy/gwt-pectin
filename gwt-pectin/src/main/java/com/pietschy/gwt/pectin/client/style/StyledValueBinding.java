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

package com.pietschy.gwt.pectin.client.style;

import com.google.gwt.user.client.ui.UIObject;
import com.pietschy.gwt.pectin.client.value.ValueModel;
import com.pietschy.gwt.pectin.client.binding.AbstractFieldBinding;
import com.pietschy.gwt.pectin.client.binding.AbstractValueBinding;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 4:35:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class StyledValueBinding<T>
extends AbstractValueBinding<T>
{
   private UIObject widget;
   private T value;
   private String styleName;

   public StyledValueBinding(ValueModel<T> field, UIObject widget, T value, String styleName)
   {
      super(field);
      this.widget = widget;
      this.value = value;
      this.styleName = styleName;
   }

   protected void updateWidget(T value)
   {
      if (areEqual(this.value, value))
      {
         widget.addStyleName(styleName);
      }
      else
      {
         widget.removeStyleName(styleName);
      }
   }

   public UIObject getTarget()
   {
      return widget;
   }
}