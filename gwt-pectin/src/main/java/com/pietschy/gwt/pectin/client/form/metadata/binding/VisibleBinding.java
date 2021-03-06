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

import com.google.gwt.user.client.ui.UIObject;
import com.pietschy.gwt.pectin.client.value.ValueModel;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
* User: andrew
* Date: Jul 17, 2009
* Time: 12:59:25 PM
* To change this template use File | Settings | File Templates.
*/
public class VisibleBinding 
extends AbstractVisibleBinding<UIObject>
{
   public VisibleBinding(ValueModel<Boolean> model, UIObject target)
   {
      super(model, target);
   }

   public VisibleBinding(ValueModel<Boolean> model, UIObject target, UIObject... others)
   {
      super(model, target, others);
   }

   public VisibleBinding(ValueModel<Boolean> model, Collection<UIObject> widget)
   {
      super(model, widget);
   }

   protected void updateVisibility(UIObject target, boolean visible)
   {
      target.setVisible(visible);
   }

}
