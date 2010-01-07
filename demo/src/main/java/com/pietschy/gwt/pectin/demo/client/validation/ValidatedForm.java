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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.pietschy.gwt.pectin.client.FieldModelBase;
import com.pietschy.gwt.pectin.client.FormModel;
import com.pietschy.gwt.pectin.client.ListFieldModelBase;
import com.pietschy.gwt.pectin.client.binding.WidgetBinder;
import com.pietschy.gwt.pectin.client.components.ComboBox;
import com.pietschy.gwt.pectin.client.components.EnhancedTextBox;
import com.pietschy.gwt.pectin.client.components.NullSafeCheckBox;
import com.pietschy.gwt.pectin.client.metadata.binding.MetadataBinder;
import com.pietschy.gwt.pectin.client.validation.binding.ValidationBinder;
import com.pietschy.gwt.pectin.client.validation.component.ValidationDisplayLabel;
import com.pietschy.gwt.pectin.client.validation.component.ValidationDisplayPanel;
import com.pietschy.gwt.pectin.demo.client.domain.Gender;
import com.pietschy.gwt.pectin.demo.client.domain.Wine;
import com.pietschy.gwt.pectin.demo.client.misc.GenderRenderer;
import com.pietschy.gwt.pectin.demo.client.misc.NickNameEditor;
import com.pietschy.gwt.pectin.demo.client.misc.VerySimpleForm;

/**
 *
 */
public class ValidatedForm extends VerySimpleForm
{
   private EnhancedTextBox givenName = new EnhancedTextBox();
   private EnhancedTextBox surname = new EnhancedTextBox();
   private EnhancedTextBox age = new EnhancedTextBox();
   private NickNameEditor nickName = new NickNameEditor();

   private CheckBox hasNickName = new CheckBox("I have a nick name");
   private ComboBox<Gender> genderCombo = new ComboBox<Gender>(Gender.values());

   // checkboxes bound to beans can get nulls so we use a null safe version..
   private CheckBox wineLoverCheckBox = new NullSafeCheckBox("I like wine");
   private CheckBox cabSavCheckBox = new CheckBox("Cab Sav");
   private CheckBox merlotCheckBox = new CheckBox("Merlot");
   private CheckBox shirazCheckBox = new CheckBox("Shiraz");


   private Button validateButton = new Button("Validate");

   WidgetBinder binder = new WidgetBinder();
   ValidationBinder validation = new ValidationBinder();
   MetadataBinder metadata = new MetadataBinder();


   public ValidatedForm(final ValidatedFormModel model)
   {
      genderCombo.setRenderer(new GenderRenderer());

      binder.bind(model.givenName).to(givenName);
      binder.bind(model.surname).to(surname);
      binder.bind(model.age).to(age);
      binder.bind(model.gender).to(genderCombo);
      binder.bind(model.hasNickName).to(hasNickName);
      binder.bind(model.nickName).to(nickName);
      binder.bind(model.wineLover).to(wineLoverCheckBox);
      binder.bind(model.favoriteWines).containingValue(Wine.CAB_SAV).to(cabSavCheckBox);
      binder.bind(model.favoriteWines).containingValue(Wine.MERLOT).to(merlotCheckBox);
      binder.bind(model.favoriteWines).containingValue(Wine.SHIRAZ).to(shirazCheckBox);

      addRow("Given name", givenName, createValidationLabel(model.givenName));
      addRow("Surname", surname, createValidationLabel(model.surname));
      addRow("Age", age, createValidationLabel(model.age));
      addRow("Gender", genderCombo, createValidationLabel(model.gender));
      addGap();

      addNote("This demonstrates a conditional validation.  The validation is only run if the checkbox is selected.");
      addRow("", hasNickName);
      addRow("Nick name", nickName, createValidationLabel(model.nickName));
      addGap();

      addNote("This is conditional validation on a ListFieldModel<T> that shows info and warning messages.");
      addRow("", wineLoverCheckBox);
      addRow("Favorite wines", cabSavCheckBox, merlotCheckBox, shirazCheckBox);
      addRow("", createValidationLabel(model.favoriteWines));

      addNote("We'll also display the validation message for the whole form below.  Click the Validate button to see them.");
      addRow("", createValidationPanel(model));

      addGap();
      addRow("", validateButton);


      validateButton.addClickHandler(new ClickHandler()
      {
         public void onClick(ClickEvent event)
         {
            model.validate();
         }
      });

   }

   private ValidationDisplayLabel createValidationLabel(FieldModelBase<?> field)
   {
      ValidationDisplayLabel label = new ValidationDisplayLabel();
      validation.bindValidationOf(field).to(label);
      return label;
   }

   private ValidationDisplayLabel createValidationLabel(ListFieldModelBase<?> field)
   {
      ValidationDisplayLabel label = new ValidationDisplayLabel();
      validation.bindValidationOf(field).to(label);
      return label;
   }

   private ValidationDisplayPanel createValidationPanel(FormModel form)
   {
      ValidationDisplayPanel panel = new ValidationDisplayPanel();
      validation.bindValidationOf(form).to(panel);
      return panel;
   }

}