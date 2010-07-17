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

package com.pietschy.gwt.pectin.client.form.metadata.binding;

import com.google.gwt.user.client.ui.FocusWidget;
import com.pietschy.gwt.pectin.client.binding.BindingContainer;
import com.pietschy.gwt.pectin.client.form.metadata.HasEnabled;
import com.pietschy.gwt.pectin.client.value.ValueModel;

/**
 * Created by IntelliJ IDEA.
* User: andrew
* Date: Jul 17, 2009
* Time: 1:47:19 PM
* To change this template use File | Settings | File Templates.
*/
public class EnabledBindingBuilder 
{
   private ValueModel<Boolean> field;
   private BindingContainer container;

   public EnabledBindingBuilder(BindingContainer container, ValueModel<Boolean> model)
   {
      this.container = container;
      this.field = model;
   }

   /**
    * Binds the visible metadata property to the speicified widget.
    * 
    * @param widget the widget to bind to.
    */
   public void to(HasEnabled widget) 
   {
      container.registerDisposableAndUpdateTarget(new HasEnabledBinding(field, widget));
   }
   
   /**
    * Binds the visible metadata property to the speicified widget.
    * 
    * @param widget the widget to bind to.
    */
   public void to(FocusWidget widget) 
   {
      container.registerDisposableAndUpdateTarget(new FocusWidgetEnabledBinding(field, widget));
   }
   
}
