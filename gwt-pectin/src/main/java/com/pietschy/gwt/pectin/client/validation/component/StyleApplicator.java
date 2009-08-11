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

package com.pietschy.gwt.pectin.client.validation.component;

import com.pietschy.gwt.pectin.client.validation.Severity;
import com.pietschy.gwt.pectin.client.validation.ValidationResult;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

import java.util.TreeMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
* User: andrew
* Date: Jul 13, 2009
* Time: 11:39:45 AM
* To change this template use File | Settings | File Templates.
*/
public class StyleApplicator
{
   private static final StyleApplicator DEFAULT_INSTANCE = new StyleApplicator();
   
   public static final String ERROR_STYLE = "validation-error";
   public static final String WARNING_STYLE = "validation-warning";
   public static final String INFO_STYLE = "validation-info";
   
   private TreeMap<Severity, String> styleNames = new TreeMap<Severity, String>();

   public StyleApplicator()
   {
      initialiseStyles();
   }

   protected void initialiseStyles()
   {
      registerStyleName(Severity.ERROR, ERROR_STYLE);
      registerStyleName(Severity.WARNING, WARNING_STYLE);
      registerStyleName(Severity.INFO, INFO_STYLE);
   }

   public void registerStyleName(Severity severity, String styleName)
   {
      styleNames.put(severity, styleName);
   }

   public void clearStyleNames()
   {
      styleNames.clear();
   }

   public void applyStyles(UIObject widget, ValidationResult result)
   {
      clearStyles(widget);

      for (Map.Entry<Severity, String> entry : styleNames.entrySet())
      {
         if (result.contains(entry.getKey()))
         {
            widget.addStyleName(entry.getValue());
            break;
         }
      }
   }

   public void clearStyles(UIObject widget)
   {
      for (String styleName : styleNames.values())
      {
         widget.removeStyleName(styleName);
      }
   }

   public static StyleApplicator defaultInstance()
   {
      return DEFAULT_INSTANCE;
   }
}
