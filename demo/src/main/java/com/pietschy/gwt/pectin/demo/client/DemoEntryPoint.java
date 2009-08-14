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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.pietschy.gwt.pectin.demo.client.basic.BasicDemo;
import com.pietschy.gwt.pectin.demo.client.metadata.MetadataDemo;
import com.pietschy.gwt.pectin.demo.client.validation.ValidationDemo;
import com.pietschy.gwt.pectin.demo.client.style.StyleDemo;


/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Feb 7, 2009
 * Time: 1:12:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class DemoEntryPoint
   implements EntryPoint
{
   private TabPanel tabs;
   protected RootPanel rootPanel;

   public void onModuleLoad()
   {
      rootPanel = RootPanel.get("demo-target");
      
      FlowPanel panel = new FlowPanel();

      tabs = new TabPanel();
      tabs.add(new BasicDemo(), "Basic");
      tabs.add(new MetadataDemo(), "Metadata Plugin");
      tabs.add(new ValidationDemo(), "Validation Plugin");
      tabs.add(new StyleDemo(), "Style Bindings");
      tabs.selectTab(0);

      panel.add(tabs);

      
      rootPanel.add(panel);

      Window.enableScrolling(true);

      Window.addResizeHandler(new ResizeHandler()
      {
         public void onResize(ResizeEvent event)
         {
            updateSize();
         }
      });

      updateSize();
   }

   private void updateSize()
   {
      tabs.setSize("" + rootPanel.getOffsetWidth() + "px", "auto");
   }


}
