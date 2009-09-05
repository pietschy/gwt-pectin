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

package com.pietschy.gwt.pectin.demo.client.validation;

import com.pietschy.gwt.pectin.demo.client.misc.AbstractDemo;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jun 19, 2008
 * Time: 1:42:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValidationDemo extends AbstractDemo
{
   private ValidatedFormModel model = new ValidatedFormModel();
   private ValidatedForm form = new ValidatedForm(model);

   public ValidationDemo()
   {
//      model.setPerson(new Person());
      
      addBlurbParagraph("This demo shows the use of the ValidationPlugin.  The nick name " +
                        "and wine list only validate when their corresponding checkboxes " +
                        "are selected.");
      addBlurbParagraph("Most of the components have been extended to implement ValidationDisplay and " +
                        "automatically update thier style based on the validation state using the " +
                        "StyleApplicator class.");

      addLinkToModel(model);
      addLinkToView(form);
      
      setMainContent(form);
   }


}