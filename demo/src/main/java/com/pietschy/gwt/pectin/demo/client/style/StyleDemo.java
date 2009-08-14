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

package com.pietschy.gwt.pectin.demo.client.style;

import com.pietschy.gwt.pectin.demo.client.misc.AbstractDemo;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Aug 14, 2009
 * Time: 12:26:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class StyleDemo
extends AbstractDemo
{
   private StyleFormModel model = new StyleFormModel();
   private StyleForm form = new StyleForm(model);
   
   public StyleDemo()
   {
      addBlurbParagraph("Pectin also supports binding various conditions to the style of an " +
                        "arbitrary widget. This allows values, metadata and validation state to " +
                        "trigger style changes on elements like field labels or containers.");
      
      setMainContent(form);
      
      addLinkToModel(model);
      addLinkToView(form);
   }
}
