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

package com.pietschy.gwt.pectin.demo.client.basic;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.FlowPanel;
import com.pietschy.gwt.pectin.client.validation.component.ValidationMessageDisplay;
import com.pietschy.gwt.pectin.client.validation.ValidationBinder;
import com.pietschy.gwt.pectin.client.binding.WidgetBinder;
import com.pietschy.gwt.pectin.client.metadata.binding.MetadataBinder;
import com.pietschy.gwt.pectin.client.components.ComboBox;
import com.pietschy.gwt.pectin.client.components.EnhancedTextBox;
import com.pietschy.gwt.pectin.client.components.NullSafeCheckBox;

/**
 * 
 */
public class EnhancedPersonForm extends Composite
{
   private EnhancedTextBox givenName = new EnhancedTextBox();
   private ValidationMessageDisplay givenNameValidationMessage = new ValidationMessageDisplay();
   
   private EnhancedTextBox surname = new EnhancedTextBox();
   private ValidationMessageDisplay surnameValidationMessage = new ValidationMessageDisplay();
   
   private EnhancedTextBox age = new EnhancedTextBox();
   private ValidationMessageDisplay ageValidationMessage = new ValidationMessageDisplay();
   
   private NickNameEditor nickName = new NickNameEditor();
   private ValidationMessageDisplay nickNameValidationMessage = new ValidationMessageDisplay();
   private CheckBox hasNickName = new CheckBox("I have a nick name");
   private TextBox loginName = new TextBox();
   private ComboBox<Gender> genderCombo = new ComboBox<Gender>(Gender.values());
   private Label favoriteWinesLabel = new Label("Favorites");
   private CheckBox wineLoverCheckBox = new NullSafeCheckBox("I like wine");
   private WineListEditor wineListEditor = new WineListEditor();
   
   WidgetBinder widgets = new WidgetBinder();
   MetadataBinder metadata = new MetadataBinder(); 
   ValidationBinder validation = new ValidationBinder();


   public EnhancedPersonForm(PersonFormModel model)
   {
      genderCombo.setRenderer(new GenderRenderer());
      loginName.setEnabled(false);
      
      widgets.bind(model.givenName).to(givenName);
      widgets.bind(model.surname).to(surname);
      widgets.bind(model.age).to(age);

      widgets.bind(model.hasNickName).to(hasNickName);
      widgets.bind(model.nickName).to(nickName);
      widgets.bind(model.loginName).toLabel(loginName);

      validation.displayValidationOf(model.givenName).using(givenNameValidationMessage);
      validation.displayValidationOf(model.surname).using(surnameValidationMessage);
      validation.displayValidationOf(model.age).using(ageValidationMessage);
      validation.displayValidationOf(model.nickName).using(nickNameValidationMessage);
      
      widgets.bind(model.gender).to(genderCombo);

      widgets.bind(model.wineLover).to(wineLoverCheckBox);
      widgets.bind(model.favoriteWines).to(wineListEditor);
      
      metadata.bindVisibilityOf(model.favoriteWines).to(favoriteWinesLabel);

      FlexTable table = new FlexTable();

      int row = 0;
      table.setText(row, 0, "Given Name");
      table.setWidget(row, 1, givenName);
      table.setWidget(row, 2, givenNameValidationMessage);

      row++;
      table.setText(row, 0, "Surname");
      table.setWidget(row, 1, surname);
      table.setWidget(row, 2, surnameValidationMessage);
      
      row++;
      table.setText(row, 0, "Login Name");
      table.setWidget(row, 1, loginName);
      
      row++;
      table.setWidget(row, 1, hasNickName);
      row++;
      table.setText(row, 0, "Nickname");
      table.setWidget(row, 1, nickName);
      table.setWidget(row, 2, nickNameValidationMessage);
      
      
      row++;
      table.setWidget(row, 1, new HTML("&nbsp;"));

      row++;
      table.setText(row, 0, "Gender");
      table.setWidget(row, 1, genderCombo);
      
      row++;
      table.setText(row, 0, "Age");
      table.setWidget(row, 1, age);
      table.setWidget(row, 2, ageValidationMessage);
 
      row++;
      table.setWidget(row, 1, new HTML("&nbsp;"));

      row++;
      table.setWidget(row, 1, wineLoverCheckBox);

      row++;
      table.setWidget(row, 0, favoriteWinesLabel);
      table.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_TOP);
      table.setWidget(row, 1, wineListEditor);
      
      FlowPanel p = new FlowPanel();
      p.add(table);
      initWidget(p);
   }

   protected Widget hp(Widget first, Widget... others)
   {
      if (others.length == 0)
      {
         return first;
      }
      
      HorizontalPanel p = new HorizontalPanel();
      p.add(first);
      for (Widget other : others)
      {
         p.add(new HTML("&nbsp;"));
         p.add(other);
      }
      p.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
      return p;
   }

}