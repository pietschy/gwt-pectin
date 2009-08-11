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

package com.pietschy.gwt.pectin.demo.client.misc;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HTML;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 18, 2009
 * Time: 12:14:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class VerySimpleForm
extends Composite
{
   private FlexTable table = new FlexTable();
   protected FlexTable.FlexCellFormatter cellFormatter = table.getFlexCellFormatter();

   public VerySimpleForm()
   {
      initWidget(table);
   }
   
   public void addRow(String label, Widget widget)
   {
      int row = table.getRowCount();
      table.setText(row, 0, label);
      styleLabel(row);
      table.setWidget(row, 1, widget);
   }

   public void addRow(Widget label, Widget widget)
   {
      int row = table.getRowCount();
      table.setWidget(row, 0, label);
      styleLabel(row);
      table.setWidget(row, 1, widget);
   }
   
   public void addRow(String label, Widget widget, Widget... others)
   {
      addRow(label, hp(widget, others));
   }

   public void addRow(Widget label, Widget widget, Widget... others)
   {
      addRow(label, hp(widget, others));
   }

   private void styleLabel(int row)
   {
      cellFormatter.addStyleName(row, 0, "VerySimpleForm-Label");
   }

   public void addGap()
   {
      int row = table.getRowCount();
      table.setWidget(row, 0, new HTML("&nbsp;"));
      cellFormatter.setColSpan(row, 0, 2);
      cellFormatter.setStylePrimaryName(row, 0, "VerySimpleForm-GapRow");
   }
   
   
   protected Widget hp(Widget first, Widget... others)
   {
      if (others.length == 0)
      {
         return first;
      }
      
      HorizontalPanel p = new HorizontalPanel();
      p.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
      p.add(first);
      for (Widget other : others)
      {
         p.add(new HTML("&nbsp;&nbsp;"));
         p.add(other);
      }
      return p;
   }
}
