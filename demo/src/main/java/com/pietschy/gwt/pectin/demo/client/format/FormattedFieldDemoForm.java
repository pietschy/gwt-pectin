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

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.pietschy.gwt.pectin.client.binding.WidgetBinder;
import com.pietschy.gwt.pectin.client.components.EnhancedTextBox;
import com.pietschy.gwt.pectin.client.components.TextSplitter;
import com.pietschy.gwt.pectin.demo.client.misc.VerySimpleForm;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Aug 14, 2009
 * Time: 12:28:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormattedFieldDemoForm
extends VerySimpleForm
{
   // we'll use an EnhancedTextBox as it fires value change events as
   // you type, much more exciting for the demo (c:
   private TextBox age = new EnhancedTextBox();
   private TextBox ageInDogYears = new EnhancedTextBox();

   // The TextSplitter turns any TextBoxBase into a HasValue<Collection<String>> that
   // can the be bound to a formatted list.
   private TextSplitter luckyNumbers = new TextSplitter(new EnhancedTextBox());
   private Label luckyNumberLabel = new Label();

   private WidgetBinder binder = new WidgetBinder();

   public FormattedFieldDemoForm(final FormattedFieldDemoModel model)
   {

      binder.bind(model.age).to(age);
      binder.bind(model.ageInDogYears).to(ageInDogYears);

      // bind formattedListField to a text box (wrapped by out ListTextBox to
      // make it impement HasValue<Collection<String>>)
      binder.bind(model.luckyNumbers).to(luckyNumbers);

      // Now bind the list to a Label for simple display.  This format only
      // has to deal with integer values.
      binder.bind(model.luckyNumbers).toLabel(luckyNumberLabel, new LuckyNumberLabelFormat());

      age.setVisibleLength(5);
      ageInDogYears.setVisibleLength(5);
      
      addRow("Age", age);
      addRow("Age (in dog years)", ageInDogYears);

      addGap();

      addNote("The following binds a list of integers to the value of a comma separated text box.");
      addNote("Type your lucky numbers separated by a comma.");
      addRow("Lucky numbers", luckyNumbers);

      addGap();

      addRow("", luckyNumberLabel);


   }

}