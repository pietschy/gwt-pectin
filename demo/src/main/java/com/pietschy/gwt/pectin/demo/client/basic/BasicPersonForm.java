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
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.DOM;
import com.pietschy.gwt.pectin.client.validation.ValidationBinder;
import com.pietschy.gwt.pectin.client.binding.WidgetBinder;
import com.pietschy.gwt.pectin.client.metadata.binding.MetadataBinder;
import com.pietschy.gwt.pectin.client.components.ComboBox;
import com.pietschy.gwt.pectin.client.components.NullSafeCheckBox;

/**
 * 
 */
public class BasicPersonForm extends Composite
{
   private TextBox givenName = new TextBox();
   private TextBox surname = new TextBox();
   private TextBox age = new TextBox();
   private TextBox nickName = new TextBox();
   private CheckBox hasNickname = new CheckBox("I have a nick name");
   private TextBox loginName = new TextBox();
   private String buttonGroupId = DOM.createUniqueId();
   private RadioButton maleRadio = new RadioButton(buttonGroupId, "Male");
   private RadioButton femaleRadio = new RadioButton(buttonGroupId, "Female");
   private ComboBox<Gender> genderCombo = new ComboBox<Gender>(Gender.values());
   private CheckBox wineLoverCheckBox = new NullSafeCheckBox("I like wine");
   private CheckBox cabSavCheckBox = new CheckBox("Cab Sav");
   private CheckBox merlotCheckBox = new CheckBox("Merlot");
   private CheckBox shirazCheckBox = new CheckBox("Shiraz");
   private Label favoriteWinesLabel = new Label("Favorites");
   private WineListEditor wineListEditor = new WineListEditor();
   
   WidgetBinder widgets = new WidgetBinder();
   MetadataBinder metadata = new MetadataBinder(); 
   ValidationBinder validation = new ValidationBinder();


   public BasicPersonForm(PersonFormModel model)
   {
      genderCombo.setRenderer(new GenderRenderer());
      
      widgets.bind(model.givenName).to(givenName);
      widgets.bind(model.surname).to(surname);
      widgets.bind(model.age).to(age);
      
      widgets.bind(model.nickName).to(nickName);
      widgets.bind(model.hasNickName).to(hasNickname);
      widgets.bind(model.loginName).toLabel(loginName);

      widgets.bind(model.gender).withValue(Gender.MALE).to(maleRadio);
      widgets.bind(model.gender).withValue(Gender.FEMALE).to(femaleRadio);
      widgets.bind(model.gender).to(genderCombo);

      widgets.bind(model.wineLover).to(wineLoverCheckBox);
      widgets.bind(model.favoriteWines).containingValue(Wine.CAB_SAV).to(cabSavCheckBox);
      widgets.bind(model.favoriteWines).containingValue(Wine.MERLOT).to(merlotCheckBox);
      widgets.bind(model.favoriteWines).containingValue(Wine.SHIRAZ).to(shirazCheckBox);

      widgets.bind(model.favoriteWines).to(wineListEditor);
      metadata.bindVisibilityOf(model.favoriteWines).to(favoriteWinesLabel);

      FlexTable table = new FlexTable();

      int row = 0;
      table.setText(row, 0, "Given Name");
      table.setWidget(row, 1, givenName);

      row++;
      table.setText(row, 0, "Surname");
      table.setWidget(row, 1, surname);
      
      row++;
      table.setText(row, 0, "Login Name");
      table.setWidget(row, 1, loginName);
      
      row++;
      table.setWidget(row, 1, hasNickname);
      row++;
      table.setText(row, 0, "Nickname");
      table.setWidget(row, 1, nickName);
      table.getFlexCellFormatter().setColSpan(row, 1, 2);
      
      row++;
      table.setWidget(row, 1, new HTML("&nbsp;"));

      row++;
      table.setText(row, 0, "Gender");
      table.setWidget(row, 1, hp(maleRadio, femaleRadio));
      
      row++;
      table.setText(row, 0, "Age");
      table.setWidget(row, 1, age);
      
      row++;
      table.setWidget(row, 1, new HTML("&nbsp;"));

      row++;
      table.setWidget(row, 1, wineLoverCheckBox);

      row++;
      table.setWidget(row, 0, favoriteWinesLabel);
      table.getFlexCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_TOP);
      table.setWidget(row, 1, hp(cabSavCheckBox, merlotCheckBox, shirazCheckBox));
      table.getFlexCellFormatter().setColSpan(row, 1, 2);
      
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