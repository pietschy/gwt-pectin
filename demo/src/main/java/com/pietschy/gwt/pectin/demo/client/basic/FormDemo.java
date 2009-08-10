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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jun 19, 2008
 * Time: 1:42:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormDemo extends Composite
{
   private PersonFormModel model = new PersonFormModel();
   private Button saveButton = new Button("Validate");

   public FormDemo()
   {
      saveButton.addClickHandler(new ClickHandler()
      {
         public void onClick(ClickEvent event)
         {
            model.validate();
         }
      });
      
      Person person = new Person();
      model.setPerson(person);
      
      HorizontalPanel hp = new HorizontalPanel();

      BasicPersonForm simpleForm = new BasicPersonForm(model);
      EnhancedPersonForm enhancedForm = new EnhancedPersonForm(model);
      
      simpleForm.setWidth("300px");
      enhancedForm.setWidth("350px");
      
      hp.add(simpleForm);
      hp.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
      hp.add(enhancedForm);

      VerticalPanel vp = new VerticalPanel();
      
      vp.add(hp);
      vp.add(new HTML("&nbsp;"));
      vp.add(saveButton);

      initWidget(vp);
   }   
}