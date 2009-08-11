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

package com.pietschy.gwt.pectin.demo.client.misc;

import com.pietschy.gwt.pectin.client.components.AbstractComboBoxWithOther;
import com.pietschy.gwt.pectin.client.validation.ValidationResult;
import com.pietschy.gwt.pectin.client.validation.component.StyleApplicator;
import com.pietschy.gwt.pectin.client.validation.component.ValidationDisplay;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.UIObject;

/**
 * Created by IntelliJ IDEA.
* User: andrew
* Date: Aug 7, 2009
* Time: 10:43:36 AM
* To change this template use File | Settings | File Templates.
*/
public class NickNameEditor extends AbstractComboBoxWithOther<String> 
{
   public NickNameEditor()
   {
      super("Bazza", "Shazza", "Davo", "Damo", "Wayno");
   }

   protected HasValue<String> createOtherEditor()
   {
      return new TextBox();
   }
}
