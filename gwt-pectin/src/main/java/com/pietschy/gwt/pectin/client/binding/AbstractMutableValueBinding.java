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

import com.pietschy.gwt.pectin.client.value.MutableValueModel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 4:53:36 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractMutableValueBinding<T> extends AbstractValueBinding<T>
{

   public AbstractMutableValueBinding(MutableValueModel<T> model)
   {
      super(model);
   }
   
   protected void updateModel(final T value)
   {
      whileIgnoringModelChanges(new Runnable()
      {
         public void run()
         {
            getModel().setValue(value);
         }
      });
   }

   @Override
   protected MutableValueModel<T> getModel()
   {
      return (MutableValueModel<T>) super.getModel();
   }
}
