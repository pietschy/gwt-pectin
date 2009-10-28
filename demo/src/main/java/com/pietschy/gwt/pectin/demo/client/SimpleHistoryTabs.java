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

package com.pietschy.gwt.pectin.demo.client;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Oct 28, 2009
 * Time: 12:59:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleHistoryTabs extends Composite
{
   private TabPanel tabs = new TabPanel();
   private Map<Integer, String> historyTokens = new HashMap<Integer, String>();
   private Map<String, Integer> historyLookup = new HashMap<String, Integer>();
   private TabSelectionHandler tabSelectionHandler = new TabSelectionHandler();

   public SimpleHistoryTabs()
   {
      initWidget(tabs);

      tabs.addSelectionHandler(tabSelectionHandler);

      History.addValueChangeHandler(new ValueChangeHandler<String>()
      {
         public void onValueChange(ValueChangeEvent<String> event)
         {
            selectTabFromToken(event.getValue());
         }
      });
   }

   public void add(Widget widget, String tabText, String historyToken)
   {
      tabs.add(widget, tabText);
      int index = tabs.getWidgetIndex(widget);
      historyLookup.put(historyToken, index);
      historyTokens.put(index, historyToken);
   }

   public void initialiseSelectionFromHistory()
   {
      selectTabFromToken(History.getToken());
   }

   public void selectTab(int index)
   {
      tabs.selectTab(index);
   }

   private void selectTabFromToken(String token)
   {
      try
      {
         tabSelectionHandler.setIgnoreEvents(true);
         Integer index = historyLookup.get(token);
         selectTab(index != null ? index : 0);
      }
      finally
      {
         tabSelectionHandler.setIgnoreEvents(false);
      }
   }


   private class TabSelectionHandler implements SelectionHandler<Integer>
   {
      private boolean ignoreEvents = false;

      public void onSelection(SelectionEvent<Integer> event)
      {
         if (!isIgnoreEvents())
         {
            Integer index = event.getSelectedItem();
            History.newItem(historyTokens.get(index), false);
         }
      }

      public boolean isIgnoreEvents()
      {
         return ignoreEvents;
      }

      public void setIgnoreEvents(boolean ignoreEvents)
      {
         this.ignoreEvents = ignoreEvents;
      }
   }
}
