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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.pietschy.gwt.pectin.client.binding.WidgetBinder;
import com.pietschy.gwt.pectin.client.components.EnhancedTextBox;
import com.pietschy.gwt.pectin.client.style.StyleBinder;
import com.pietschy.gwt.pectin.client.validation.binding.ValidationBinder;
import com.pietschy.gwt.pectin.client.metadata.binding.MetadataBinder;
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
   private EnhancedTextBox title = new EnhancedTextBox();
   
   private HTML nameLabel = new HTML("Name");
   private EnhancedTextBox name = new EnhancedTextBox();

   private CheckBox hasNickName = new CheckBox("I have a nick name");
   private HTML nickNameLabel = new HTML("Nick Name");
   private NickNameEditor nickName = new NickNameEditor();

   private Button validateButton = new Button("Validate");
   
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

      // Now change the title style when the magic name is entered.
      style.bind(model.title).withValue("lord vadar").toStyle("LordVadar").on(title);
      
      // Our name label gets styles 'validation-error', 'validation-warning'
      // and 'validation-info' based on the validation state of the model.
      validation.bindValidationOf(model.name).toStyleOf(nameLabel);
      
      // Here our nick name label has it's style configured from the metadata
      // of the nickName field.  Could have also used the withValue technique
      // above in this case as well.
      metadata.bindDisabled(model.nickName).toStyle("disabled").on(nickNameLabel);
      
      
      addNote("Type in \"lord vadar\" to see what happens");
      addRow("Title", title);
      addGap();
      
      addNote("Type something in, click validate and watch the label");
      addRow(nameLabel, name);
      addRow("", validateButton);
      addGap();

      addNote("Click the check box and watch the label");
      addRow("", hasNickName);
      addRow(nickNameLabel, nickName);

      validateButton.addClickHandler(new ClickHandler()
      {
         public void onClick(ClickEvent event)
         {
            model.validate();
         }
      });
      
   }
}
