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

import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.DOM;
import com.pietschy.gwt.pectin.client.binding.WidgetBinder;
import com.pietschy.gwt.pectin.demo.client.domain.Gender;
import com.pietschy.gwt.pectin.demo.client.domain.Wine;
import com.pietschy.gwt.pectin.demo.client.misc.VerySimpleForm;

/**
 * 
 */
public class BasicForm extends VerySimpleForm
{
   private TextBox givenName = new TextBox();
   private TextBox surname = new TextBox();
   private TextBox age = new TextBox();
   private TextBox loginName = new TextBox();
   
   private String buttonGroupId = DOM.createUniqueId();
   private RadioButton maleRadio = new RadioButton(buttonGroupId, "Male");
   private RadioButton femaleRadio = new RadioButton(buttonGroupId, "Female");

   private CheckBox cabSavCheckBox = new CheckBox("Cab Sav");
   private CheckBox merlotCheckBox = new CheckBox("Merlot");
   private CheckBox shirazCheckBox = new CheckBox("Shiraz");
   
   WidgetBinder widgets = new WidgetBinder();


   public BasicForm(BasicFormModel model)
   {
      // see the metadata demo to see how this can be done in the model.
      loginName.setEnabled(false);
      age.setVisibleLength(5);
      
      widgets.bind(model.givenName).to(givenName);
      widgets.bind(model.surname).to(surname);
      widgets.bind(model.age).to(age);
      widgets.bind(model.loginName).toLabel(loginName);

      widgets.bind(model.gender).withValue(Gender.MALE).to(maleRadio);
      widgets.bind(model.gender).withValue(Gender.FEMALE).to(femaleRadio);

      widgets.bind(model.favoriteWines).containingValue(Wine.CAB_SAV).to(cabSavCheckBox);
      widgets.bind(model.favoriteWines).containingValue(Wine.MERLOT).to(merlotCheckBox);
      widgets.bind(model.favoriteWines).containingValue(Wine.SHIRAZ).to(shirazCheckBox);

      addRow("Given Name", givenName);
      addRow("Surname", surname);
      addRow("Login Name", loginName);
      addGap();
      addRow("Gender", maleRadio, femaleRadio);
      addRow("Age", age);
      addGap();
      addRow("Favorite Wines", cabSavCheckBox, merlotCheckBox, shirazCheckBox);
   }
}