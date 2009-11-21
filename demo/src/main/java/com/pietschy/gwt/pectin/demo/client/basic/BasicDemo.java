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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
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

   private Button saveButton = new Button("Save");

   public BasicDemo()
   {
      saveButton.addClickHandler(new SaveHandler());

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
      panel.add(new HTML("&nbsp;")); // *cough*
      panel.add(saveButton);

      addBlurbParagraph("This demo shows a simple form that's bound to an underlying bean. " +
                        "It also contains a computed field (letters in name) and a list field bound to " +
                        "checkboxes (favorite wines).");
      
      addBlurbParagraph("Clicking save commits the changes to the bean but doesn't show you " +
                        "anything.  Checkout the other demos if you want more excitement.");

      
      addLinkToModel(model);
      addLinkToView(form);
      
      setMainContent(panel);
   }


   private class SaveHandler implements ClickHandler
   {
      public void onClick(ClickEvent event)
      {
         model.commit();
      }
   }
}