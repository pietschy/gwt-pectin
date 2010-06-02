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

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.pietschy.gwt.pectin.client.binding.FormBinder;
import com.pietschy.gwt.pectin.client.binding.WidgetBinder;
import com.pietschy.gwt.pectin.client.components.AbstractDynamicList;
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

   private Label luckyNumberLabel = new Label();
   // The TextSplitter turns any TextBoxBase into a HasValue<Collection<String>> that
   // can the be bound to a formatted list.
   private TextSplitter luckyNumbers = new TextSplitter(new EnhancedTextBox());
   private AbstractDynamicList<String> luckyNumbersAgain = new AbstractDynamicList<String>("Add lucky number")
   {
      protected HasValue<String> createWidget()
      {
         return new EnhancedTextBox();
      }
   };
   private Label luckNumberTotal = new Label();

   private FormBinder binder = new WidgetBinder();


   public FormattedFieldDemoForm(final FormattedFieldDemoModel model)
   {

      binder.bind(model.age).to(age);
      binder.bind(model.ageInDogYears).to(ageInDogYears);

      // bind formattedListField to a text box (wrapped by out ListTextBox to
      // make it implement HasValue<Collection<String>>)
      binder.bind(model.luckyNumbers).to(luckyNumbers);

      // Now bind the list to a Label for simple display.  This format only
      // has to deal with integer values.
      binder.bind(model.luckyNumbers).toLabel(luckyNumberLabel).withFormat(new LuckyNumberLabelFormat());

      // we'll show a second binding for our lucky numbers.
      binder.bind(model.luckyNumbers).to(luckyNumbersAgain);

      binder.bind(model.sumOfLuckyNumbers).toLabel(luckNumberTotal);

      age.setVisibleLength(5);
      ageInDogYears.setVisibleLength(5);

      addNote("This shows two formatted fields, one of which is converted from the other.");
      addRow("Age", age);
      addRow("Age (in dog years)", ageInDogYears);

      addGap();
      addNote("This is a FormattedListFieldModel example bound to a comma separated text box.");
      addNote("Type your lucky numbers separated by a comma.");
      addRow("Lucky numbers", luckyNumbers, luckyNumberLabel);

      addGap();
      addNote("And here are your lucky numbers bound to a HasValue<Collection<String>>");
      addTallRow("Lucky Numbers", luckyNumbersAgain);

      addNote("This is the sum of your lucky numbers computed by a Reduce style function.");
      addRow("Total", luckNumberTotal);



   }

}