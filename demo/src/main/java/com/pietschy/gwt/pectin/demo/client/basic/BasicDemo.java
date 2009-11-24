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

import com.google.gwt.user.client.ui.VerticalPanel;
import com.pietschy.gwt.pectin.demo.client.domain.Gender;
import com.pietschy.gwt.pectin.demo.client.domain.Person;
import com.pietschy.gwt.pectin.demo.client.misc.AbstractDemo;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jun 19, 2008
 * Time: 1:42:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasicDemo extends AbstractDemo
{
   private BasicFormModel model = new BasicFormModel();
   private BasicForm form = new BasicForm(model);



   public BasicDemo()
   {
      // throw a person together...
      Person person = new Person();
      person.setGivenName("Joe");
      person.setSurname("Blogs");
      person.setAge(37);
      person.setGender(Gender.MALE);

      // set the model.
      model.setPerson(person);

      // build a panel the form and add a save button
      VerticalPanel panel = new VerticalPanel();
      panel.add(form);
      
      addBlurbParagraph("This demo shows a simple form that's bound to an underlying bean. " +
                        "It also contains a computed field (letters in name) and a list field bound to " +
                        "checkboxes (favorite wines).");
      
      addBlurbParagraph("The save button is bound to the dirty state of the model.  Changing any value " +
                        "will enable the button, reverting the value will disable it.  Clicking save " +
                        "commits the changes and concequently disables the button.");

      
      addLinkToModel(model);
      addLinkToView(form);
      
      setMainContent(panel);
   }



}