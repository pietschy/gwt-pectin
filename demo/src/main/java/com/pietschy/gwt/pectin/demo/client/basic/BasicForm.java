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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.*;
import com.pietschy.gwt.pectin.client.components.AbstractDynamicList;
import com.pietschy.gwt.pectin.client.components.EnhancedTextBox;
import com.pietschy.gwt.pectin.client.form.binding.FormBinder;
import com.pietschy.gwt.pectin.demo.client.domain.Gender;
import com.pietschy.gwt.pectin.demo.client.domain.Wine;
import com.pietschy.gwt.pectin.demo.client.misc.VerySimpleForm;

/**
 * 
 */
public class BasicForm extends VerySimpleForm
{
   protected BasicFormModel model;

   // we'll use an EnhancedTextBox as it fires value change events as
   // you type, much more exciting for the demo (c:
   private TextBox givenName = new EnhancedTextBox();
   private TextBox surname = new EnhancedTextBox();
   private Label fullName = new Label();
   private Label lettersInName = new Label();
   
   private String buttonGroupId = DOM.createUniqueId();
   private RadioButton maleRadio = new RadioButton(buttonGroupId, "Male");
   private RadioButton femaleRadio = new RadioButton(buttonGroupId, "Female");

   private CheckBox cabSavCheckBox = new CheckBox("Cab Sav");
   private CheckBox merlotCheckBox = new CheckBox("Merlot");
   private CheckBox shirazCheckBox = new CheckBox("Shiraz");

   private AbstractDynamicList<String> favoriteCheeses = new AbstractDynamicList<String>("Add Cheese")
   {
      protected HasValue<String> createWidget()
      {
         return new EnhancedTextBox();
      }
   };

   private TextBox addressOne = new EnhancedTextBox();
   private TextBox addressTwo = new EnhancedTextBox();
   private TextBox suburb = new EnhancedTextBox();
   private TextBox postCode = new EnhancedTextBox();

   private Button saveButton = new Button("Save");
   private Button revertButton = new Button("Revert");

   private FormBinder binder = new FormBinder();



   public BasicForm(BasicFormModel model)
   {
      this.model = model;

      // bind our widgets to our model.  In normal practice I'd combine the
      // binding, widget creation and form layout into some nice reusable methods.
      binder.bind(model.givenName).to(givenName);
      binder.bind(model.surname).to(surname);

      // here we're binding field to a static display (HasText).  We can also use
      // a DisplayFormat here if we need to.  In this case the default ToStringFormat
      // will be used.
      binder.bind(model.fullName).toTextOf(fullName);
      binder.bind(model.lettersInName).toTextOf(lettersInName);

      // now lets bind a value using radio buttons
      binder.bind(model.gender).withValue(Gender.MALE).to(maleRadio);
      binder.bind(model.gender).withValue(Gender.FEMALE).to(femaleRadio);

      // and a list model to a collection of check boxes
      binder.bind(model.favoriteWines).containingValue(Wine.CAB_SAV).to(cabSavCheckBox);
      binder.bind(model.favoriteWines).containingValue(Wine.MERLOT).to(merlotCheckBox);
      binder.bind(model.favoriteWines).containingValue(Wine.SHIRAZ).to(shirazCheckBox);

      // and a list model to a HasValue<Collection<T>>
      binder.bind(model.favoriteCheeses).to(favoriteCheeses);
      
      binder.bind(model.addressOne).to(addressOne);
      binder.bind(model.addressTwo).to(addressTwo);
      binder.bind(model.suburb).to(suburb);
      binder.bind(model.postCode).to(postCode);

      // checkout the activities demo for a command style pattern for buttons.
      saveButton.addClickHandler(model.saveHandler);
      revertButton.addClickHandler(model.revertHandler);

      // should really make enable/disable use var-args.. 
      binder.enable(saveButton).when(model.dirty);
      binder.enable(revertButton).when(model.dirty);

      // now layout the form
      addRow("Given Name", givenName);
      addRow("Surname", surname);

      addGap();
      addNote("The following two fields are computed from the name.");
      addRow("Full name", fullName);
      addRow("Letters in name", lettersInName);

      addGap();
      addNote("The following properties are bound to nested bean.");
      addRow("Address One", addressOne, "address.addressOne");
      addRow("Address Two", addressTwo, "address.addressTwo");
      addRow("Suburb", suburb, "address.suburb");
      addRow("Post code", postCode, "address.postCode");

      addGap();
      addNote("This binds a FieldModel<T> to a collection of HasValue<Boolean>");
      addRow("Gender", maleRadio, femaleRadio);
      addGap();
      addNote("This binds a ListFieldModel<T> to a collection of HasValue<Boolean>");
      addRow("Favorite Wines", cabSavCheckBox, merlotCheckBox, shirazCheckBox);
      addGap();
      addNote("This binds a ListFieldModel<T> to a HasValue<Collection<T>> widget.");
      addTallRow("Favorite Cheeses", favoriteCheeses);
      addGap();
      addNote("The save & revert buttons are disabled if the form is in sync with the bean.");
      addRow("", saveButton, revertButton);
   }


}