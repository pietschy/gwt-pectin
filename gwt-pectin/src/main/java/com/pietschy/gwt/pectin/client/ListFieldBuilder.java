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

package com.pietschy.gwt.pectin.client;

import com.pietschy.gwt.pectin.client.list.ArrayListModel;
import com.pietschy.gwt.pectin.client.ListModelProvider;
import com.pietschy.gwt.pectin.client.list.ListModel;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 1, 2009
 * Time: 12:20:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class ListFieldBuilder<T>
{
   private FormModel formModel;
   private Class<T> type;

   protected ListFieldBuilder(FormModel formModel, Class<T> type)
   {
      this.formModel = formModel;
      this.type = type;
   }
   
   public ListFieldModel<T> create()
   {
      return formModel.createListModel(new ArrayListModel<T>());
   }

   public ListFieldModel<T> boundTo(ListModel<T> source)
   {
      return formModel.createListModel(source);
   }
   
   public ListFieldModel<T> boundTo(ListModelProvider provider, String propertyName)
   {
      return formModel.createListModel(provider.getListModel(propertyName, type));
   }
}