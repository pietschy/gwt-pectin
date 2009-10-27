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

import com.google.gwt.user.client.ui.*;
import com.pietschy.gwt.pectin.client.metadata.HasVisible;

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
   
   public Row addRow(String label, Widget widget)
   {
      int rowIndex = table.getRowCount();
      table.setText(rowIndex, 0, label);
      styleLabel(rowIndex);
      table.setWidget(rowIndex, 1, widget);
      return new Row(rowIndex);
   }

   public Row addRow(Widget label, Widget widget)
   {
      int rowIndex = table.getRowCount();
      table.setWidget(rowIndex, 0, label);
      styleLabel(rowIndex);
      table.setWidget(rowIndex, 1, widget);
      return new Row(rowIndex);
   }

   protected Row addRow(String label, Widget widget, String hint)
   {
      return addRow(label, widget, stylesHint(new Label(hint)));
   }

   public Row addRow(String label, Widget widget, Widget... others)
   {
      return addRow(label, hp(widget, others));
   }

   public Row addRow(Widget label, Widget widget, Widget... others)
   {
      return addRow(label, hp(widget, others));
   }

   private void styleLabel(int row)
   {
      cellFormatter.addStyleName(row, 0, "VerySimpleForm-Label");
   }

   private Widget stylesHint(Widget widget)
   {
      widget.addStyleName("VerySimpleForm-Hint");
      return widget;
   }

   protected Row addNote(String text)
   {
      int rowIndex = table.getRowCount();
      table.setText(rowIndex, 1, text);
      cellFormatter.setStylePrimaryName(rowIndex, 1, "VerySimpleForm-Note");
      return new Row(rowIndex);
   }


   public Row addGap()
   {
      int rowIndex = table.getRowCount();
      table.setWidget(rowIndex, 0, new HTML("&nbsp;"));
      cellFormatter.setColSpan(rowIndex, 0, 2);
      cellFormatter.setStylePrimaryName(rowIndex, 0, "VerySimpleForm-GapRow");
      return new Row(rowIndex);
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

   public class Row implements HasVisible
   {
      private int rowIndex;

      public Row(int rowIndex)
      {
         this.rowIndex = rowIndex;
      }

      public void setVisible(boolean visible)
      {
         table.getRowFormatter().setVisible(rowIndex, visible);
      }

      public boolean isVisible()
      {
         return table.getRowFormatter().isVisible(rowIndex);
      }
   }

}
