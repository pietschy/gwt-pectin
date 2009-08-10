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

import com.google.gwt.user.client.ui.ListBox;

/**
 * This ListBox overcomes the bug where setSelecteeIndex(-1) fails if the textbox isn't 
 * attached (only on IE).  It overcomes this by including a special NULL item in the
 * list. 
 */
public class ListBoxWorkAroundBug2689
extends ListBox
{
   private static final String NULL_VALUE = "--Bug2689ListBox--NULL";

   public ListBoxWorkAroundBug2689()
   {
      init();
   }

   public ListBoxWorkAroundBug2689(boolean multipleSelect)
   {
      super(multipleSelect);
      init();
   }

   private void init()
   {
      // this is to ensure null always select the empty string so we don't ever 
      // have to set the selected index to -1 due to the bug on IE.
      //    http://code.google.com/p/google-web-toolkit/issues/detail?id=2689&can=4
      super.addItem("", NULL_VALUE);
      super.setSelectedIndex(0);
   }

   @Override
   public void addItem(String text)
   {
      verifyTextIsntEmpty(text);
      super.addItem(text);
   }

   @Override
   public void addItem(String text, String value)
   {
      verifyTextIsntEmpty(text);
      super.addItem(text, value);
   }

   @Override
   public void clear()
   {
      super.clear();
      init();
   }

   private void
   verifyTextIsntEmpty(String text)
   {
      if (text == "")
      {
         throw new IllegalArgumentException("Can't add empty string, reserved for null");
      }
   }

   public String getSelectedValue()
   {
      int index = getSelectedIndex();
      // we return null if the index is -1 OR 0, since the 0th element
      // is our empty string used to represent null.
      return index > 0 ? getValue(index) : null;
   }
   
   public void 
   setSelectedValue(String value)
   {
      if (value == null)
      {
         // our 0'th element represents null.
         super.setSelectedIndex(0);
      }
      else
      {
         super.setSelectedIndex(indexOf(value));
      }
   }


   @Override
   public void setSelectedIndex(int index)
   {
      throw new IllegalStateException("use setSelectedValue(String) instead");
   }

   protected int
   indexOf(String item)
   {
      for (int i = 0; i < getItemCount(); i++)
      {
         if (getValue(i).equals(item))
         {
            return i;
         }
      }

      // zero is the empty string.. value.
      return 0;
   }
}
