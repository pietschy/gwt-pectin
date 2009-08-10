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

package com.pietschy.gwt.pectin.client.metadata;

import com.pietschy.gwt.pectin.client.value.ValueHolder;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 16, 2009
 * Time: 3:02:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Metadata
{
   private ValueHolder<Boolean> enabledModel = new ValueHolder<Boolean>(true);
   private ValueHolder<Boolean> visibleModel = new ValueHolder<Boolean>(true);

   public Metadata()
   {
   }

   public boolean isEnabled()
   {
      return enabledModel.getValue();
   }

   public void setEnabled(boolean enabled)
   {
      enabledModel.setValue(enabled);
   }

   public ValueHolder<Boolean> getEnabledModel()
   {
      return enabledModel;
   }

   public boolean isVisible()
   {
      return visibleModel.getValue();
   }

   public void setVisible(boolean visible)
   {
      visibleModel.setValue(visible);
   }

   public ValueHolder<Boolean> getVisibleModel()
   {
      return visibleModel;
   }
}
