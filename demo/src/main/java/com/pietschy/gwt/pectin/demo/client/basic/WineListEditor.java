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

import com.pietschy.gwt.pectin.client.components.AbstractDynamicList;
import com.pietschy.gwt.pectin.client.components.ComboBox;
import com.pietschy.gwt.pectin.client.list.ArrayListModel;
import com.pietschy.gwt.pectin.client.validation.component.IndexedValidationDisplay;
import com.pietschy.gwt.pectin.client.validation.component.StyleApplicator;
import com.pietschy.gwt.pectin.client.validation.IndexedValidationResult;
import com.google.gwt.user.client.ui.HasValue;

import java.util.Arrays;


/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 10, 2009
 * Time: 5:23:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class WineListEditor
extends AbstractDynamicList<Wine> 
implements IndexedValidationDisplay
{
   
   private ArrayListModel<Wine> values;
   private ComboBox.Renderer<Wine> renderer = new ComboBox.Renderer<Wine>()
   {
      public String toDisplayString(Wine value)
      {
         return value.getDisplayString();
      }
   };
   
   private StyleApplicator styleApplicator = StyleApplicator.defaultInstance();

   public WineListEditor()
   {
      super("Add Wine");
      this.values = new ArrayListModel<Wine>();
      values.setElements(Arrays.asList(Wine.values()));
   }

   protected HasValue<Wine> createWidget()
   {
      ComboBox<Wine> combo = new ComboBox<Wine>(values);
      combo.setRenderer(renderer);
      return combo;  
   }

   public void setValidationResult(IndexedValidationResult indexedResult)
   {
      for (int index = 0; index < getRowCount(); index++)
      {
         if (indexedResult.getErrorIndicies().contains(index))
         {
            styleApplicator.applyStyles(getChildWidget(index), indexedResult.get(index));
         }
         else
         {
            styleApplicator.clearStyles(getChildWidget(index));
         }
      }
   }

}
