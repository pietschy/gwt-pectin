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

import java.util.List;

/**
 * Created by IntelliJ IDEA.
* User: andrew
* Date: Aug 14, 2009
* Time: 2:09:27 PM
* To change this template use File | Settings | File Templates.
*/
public class StyleBindingBuilder
{
   private BindingContainer builder;
   private List<UIObject> widgets;

   public StyleBindingBuilder(BindingContainer builder, List<UIObject> widgets)
   {
      this.builder = builder;
      this.widgets = widgets;
   }

   public WidgetStyleBindingBuilder with(String style)
   {
      return new WidgetStyleBindingBuilder(builder, widgets, style);
   }
}
