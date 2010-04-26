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

import com.pietschy.gwt.pectin.client.FieldModel;
import com.pietschy.gwt.pectin.client.command.ParameterisedCommand;
import com.pietschy.gwt.pectin.client.value.HasValueSetter;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 4:35:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValueModelToStaticValueBinding<T>
extends AbstractValueBinding<T>
{
   private HasValueSetter<T> widget;

   public ValueModelToStaticValueBinding(FieldModel<T> field, HasValueSetter<T> widget)
   {
      super(field);
      this.widget = widget;
   }

   public ValueModelToStaticValueBinding(FieldModel<T> field, final ParameterisedCommand<T> command)
   {
      this(field, new HasValueSetter<T>()
      {
         public void setValue(T value)
         {
            command.execute(value);
         }
      });
   }

   protected void updateWidget(T value)
   {
      widget.setValue(value);
   }

   public HasValueSetter<T> getTarget()
   {
      return widget;
   }
}