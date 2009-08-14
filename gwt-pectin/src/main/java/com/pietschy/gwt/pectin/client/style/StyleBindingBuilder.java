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
import com.pietschy.gwt.pectin.client.binding.BindingContainer;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * Created by IntelliJ IDEA.
* User: andrew
* Date: Aug 14, 2009
* Time: 2:09:27 PM
* To change this template use File | Settings | File Templates.
*/
public class StyleBindingBuilder<T>
{
   private ValueModel<T> field;
   private T value;
   private String styleName;
   private BindingContainer builder;

   public StyleBindingBuilder(BindingContainer builder, ValueModel<T> model, T value, String styleName)
   {
      this.builder = builder;
      this.field = model;
      this.value = value;
      this.styleName = styleName;
   }

   public void on(UIObject widget)
   {
      builder.registerBinding(new StyledValueBinding<T>(field, widget, value, styleName));
   }
}
