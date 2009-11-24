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

package com.pietschy.gwt.pectin.client.components;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.user.client.ui.TextBox;
import com.pietschy.gwt.pectin.client.validation.ValidationResult;
import com.pietschy.gwt.pectin.client.validation.component.ValidationDisplay;
import com.pietschy.gwt.pectin.client.validation.component.ValidationStyles;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 11, 2009
 * Time: 3:06:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class EnhancedTextBox
extends TextBox implements ValidationDisplay
{
   private boolean fireValueChangeOnEdit = false;
   private ValidationStyles validationStyles = ValidationStyles.defaultInstance();
   
   public EnhancedTextBox()
   {
      init();
   }

   public EnhancedTextBox(Element element)
   {
      super(element);
      init();
   }

   private void init()
   {
      addKeyUpHandler(new KeyUpHandler()
      {
         public void onKeyUp(KeyUpEvent event)
         {
            EnhancedTextBox.this.onKeyUp(event);
         }

      });
      setFireValueChangeOnEdit(true);
   }

   protected void onKeyUp(KeyUpEvent event)
   {
      if (isFireValueChangeOnEdit())
      {
         ValueChangeEvent.fire(this, getText());
      }
   }

   
   public boolean isFireValueChangeOnEdit()
   {
      return fireValueChangeOnEdit;
   }

   public void setFireValueChangeOnEdit(boolean fireValueChangeOnEdit)
   {
      this.fireValueChangeOnEdit = fireValueChangeOnEdit;
   }

   public void setValidationResult(ValidationResult result)
   {
       validationStyles.applyStyle(this, result);
   }
}
