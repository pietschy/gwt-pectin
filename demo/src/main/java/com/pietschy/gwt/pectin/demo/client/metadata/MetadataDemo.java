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

package com.pietschy.gwt.pectin.demo.client.metadata;

import com.pietschy.gwt.pectin.demo.client.domain.Person;
import com.pietschy.gwt.pectin.demo.client.misc.AbstractDemo;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jun 19, 2008
 * Time: 1:42:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class MetadataDemo extends AbstractDemo
{
   private MetadataFormModel model = new MetadataFormModel();
   private MetadataForm form = new MetadataForm(model);

   public MetadataDemo()
   {
      model.setPerson(new Person());
      
      addBlurbParagraph("This demo shows the use of the MetadataPlugin that allows us to control the " +
                        "enabledness and visibility and watermarks for various components.");
      
      addLinkToModel(model);
      addLinkToView(form);
      
      setMainContent(form);
   }


}