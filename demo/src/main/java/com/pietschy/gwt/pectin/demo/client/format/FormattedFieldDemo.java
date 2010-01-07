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

package com.pietschy.gwt.pectin.demo.client.format;

import com.pietschy.gwt.pectin.demo.client.misc.AbstractDemo;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Aug 14, 2009
 * Time: 12:26:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormattedFieldDemo
extends AbstractDemo
{
   private FormattedFieldDemoModel model = new FormattedFieldDemoModel();
   private FormattedFieldDemoForm form = new FormattedFieldDemoForm(model);

   public FormattedFieldDemo()
   {
      addBlurbParagraph("Formatted field models let you bind non-string models to string/text based widgets.  " +
                        "Using a format you can bind a FormattedFieldModel&lt;T&gt; to a HasValue&lt;String&gt; " +
                        "widget and a FormattedListFieldModel&lt;T&gt; to HasValue&lt;Collection&lt;String&gt;&gt;.");

      addBlurbParagraph("Plugins can also access both the string values " +
                        "and the formatted values.  This allows the validation plugin to support validators that " +
                        "run on text values that would otherwise be rejected by the format.");

      setMainContent(form);

      addLinkToModel(model);
      addLinkToView(form);
   }
}