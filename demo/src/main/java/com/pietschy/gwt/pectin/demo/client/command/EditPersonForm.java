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

package com.pietschy.gwt.pectin.demo.client.command;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.*;
import com.pietschy.gwt.pectin.client.FieldModelBase;
import com.pietschy.gwt.pectin.client.ListFieldModelBase;
import com.pietschy.gwt.pectin.client.binding.FormBinder;
import com.pietschy.gwt.pectin.client.binding.WidgetBinder;
import com.pietschy.gwt.pectin.client.channel.Channel;
import com.pietschy.gwt.pectin.client.channel.Destination;
import com.pietschy.gwt.pectin.client.command.AsyncUiCommand;
import com.pietschy.gwt.pectin.client.command.DelegatingUiCommand;
import com.pietschy.gwt.pectin.client.command.UiCommand;
import com.pietschy.gwt.pectin.client.components.AbstractDynamicList;
import com.pietschy.gwt.pectin.client.components.EnhancedTextBox;
import com.pietschy.gwt.pectin.client.validation.binding.ValidationBinder;
import com.pietschy.gwt.pectin.client.validation.component.ValidationDisplayLabel;
import com.pietschy.gwt.pectin.demo.client.domain.Gender;
import com.pietschy.gwt.pectin.demo.client.domain.Wine;
import com.pietschy.gwt.pectin.demo.client.misc.VerySimpleForm;

/**
 *
 */
public class EditPersonForm extends VerySimpleForm
{
   protected EditPersonModel model;

   // we'll use an EnhancedTextBox as it fires value change events as
   // you type, much more exciting for the demo (c:
   private TextBox givenName = new EnhancedTextBox();
   private TextBox surname = new EnhancedTextBox();

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

   private Button saveButton = new Button("Save");
   private Button cancelButton = new Button("Cancel");

   private NotificationDisplay notificationDisplay = new NotificationDisplay();

   private FormBinder binder = new WidgetBinder();
   private ValidationBinder validation = new ValidationBinder();


   public EditPersonForm(EditPersonModel model, Channel<String> notifications, AsyncUiCommand<?, ?> save, UiCommand cancel)
   {
      this.model = model;

      // bind our widgets to our model.  In normal practice I'd combine the
      // binding, widget creation and form layout into some nice reusable methods.
      binder.bind(model.givenName).to(givenName);
      binder.bind(model.surname).to(surname);

      // now lets bind a value using radio buttons
      binder.bind(model.gender).withValue(Gender.MALE).to(maleRadio);
      binder.bind(model.gender).withValue(Gender.FEMALE).to(femaleRadio);

      // and a list model to a collection of check boxes
      binder.bind(model.favoriteWines).containingValue(Wine.CAB_SAV).to(cabSavCheckBox);
      binder.bind(model.favoriteWines).containingValue(Wine.MERLOT).to(merlotCheckBox);
      binder.bind(model.favoriteWines).containingValue(Wine.SHIRAZ).to(shirazCheckBox);

      // and a list model to a HasValue<Collection<T>>
      binder.bind(model.favoriteCheeses).to(favoriteCheeses);

      // we're using a UiCommand for our save behaviour
      DelegatingUiCommand ds = new DelegatingUiCommand(save);
      binder.bind(ds).to(saveButton);
      binder.bind(cancel).to(cancelButton);

      // and when it works we'll show a little status message.. normally you'd send this to
      // a NotificationArea that would display if with nice colors and make it fade after
      // a few seconds.
      binder.bind(notifications).to(notificationDisplay);

      addRow("", notificationDisplay);
      addRow("Given Name", givenName, createValidationLabel(model.givenName));
      addRow("Surname", surname, createValidationLabel(model.surname));
      addRow("Gender", maleRadio, femaleRadio, createValidationLabel(model.gender));
      addRow("Favorite Wines", cabSavCheckBox, merlotCheckBox, shirazCheckBox);
      addTallRow("Favorite Cheeses", favoriteCheeses);
      addRow("",createValidationLabel(model.favoriteCheeses));
      addGap();
      addNote("Our buttons are bound to our activities");
      addRow("", saveButton, cancelButton);      

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

   private static class NotificationDisplay extends Label implements Destination<String>
   {
      public void receive(String value)
      {
         setText(value);
      }
   }
}