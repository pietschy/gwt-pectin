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

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.pietschy.gwt.pectin.client.binding.WidgetBinder;
import com.pietschy.gwt.pectin.client.components.EnhancedTextBox;
import static com.pietschy.gwt.pectin.client.condition.Conditions.valueOf;
import static com.pietschy.gwt.pectin.client.metadata.MetadataPlugin.metadataOf;
import com.pietschy.gwt.pectin.client.metadata.binding.MetadataBinder;
import com.pietschy.gwt.pectin.client.style.StyleBinder;
import com.pietschy.gwt.pectin.client.validation.binding.ValidationBinder;
import com.pietschy.gwt.pectin.demo.client.misc.NickNameEditor;
import com.pietschy.gwt.pectin.demo.client.misc.VerySimpleForm;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Aug 14, 2009
 * Time: 12:28:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class StyleForm
extends VerySimpleForm
{
   // we'll use an EnhancedTextBox as it fires value change events as
   // you type, much more exciting for the demo (c:
   private EnhancedTextBox title = new EnhancedTextBox();
   
   private HTML nameLabel = new HTML("Name");
   private EnhancedTextBox name = new EnhancedTextBox();

   private CheckBox hasNickName = new CheckBox("I have a nick name");
   private HTML nickNameLabel = new HTML("Nick Name");
   private NickNameEditor nickName = new NickNameEditor();

   private WidgetBinder binder = new WidgetBinder();
   private StyleBinder style = new StyleBinder();
   private ValidationBinder validation = new ValidationBinder();
   private MetadataBinder metadata = new MetadataBinder();


   public StyleForm(final StyleFormModel model)
   {
      
      // bind our regular widgets for a start..
      binder.bind(model.title).to(title);
      binder.bind(model.name).to(name);
      binder.bind(model.hasNickName).to(hasNickName);
      binder.bind(model.nickName).to(nickName);

      // Now change the title style when the magic value is entered.  I'd normally
      // use this approach for booleans, but it works with any type.
      style.style(title).with("LordVadar").when(valueOf(model.title).is("lord vadar"));

      // Here our nick name label has it's style configured from the metadata
      // of the nickName field.  Could have also used the withValue approach
      // above in this case as well.
      style.style(nickNameLabel).with("disabled").when(metadataOf(model.nickName).isDisabled());

      // Our name label gets styles 'validation-error', 'validation-warning'
      // and 'validation-info' based on the validation state of the model.
      validation.bindValidationOf(model.name).toStyleOf(nameLabel);

      addNote("Type in \"lord vadar\" to see what happens");
      addRow("Title", title);
      addGap();
      
      addNote("Clear this field and watch the label");
      addRow(nameLabel, name);
      addGap();

      addNote("Click the check box and watch the label");
      addRow("", hasNickName);
      addRow(nickNameLabel, nickName);
     
   }
}
