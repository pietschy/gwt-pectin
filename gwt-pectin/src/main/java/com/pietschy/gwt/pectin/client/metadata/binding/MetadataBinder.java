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

package com.pietschy.gwt.pectin.client.metadata.binding;

import com.pietschy.gwt.pectin.client.Field;
import com.pietschy.gwt.pectin.client.metadata.binding.AllMetadataBindingBuilder;
import com.pietschy.gwt.pectin.client.metadata.binding.VisibilityBindingBuilder;
import com.pietschy.gwt.pectin.client.metadata.binding.EnabledBindingBuilder;
import com.pietschy.gwt.pectin.client.metadata.MetadataPlugin;
import com.pietschy.gwt.pectin.client.value.ValueModel;
import com.pietschy.gwt.pectin.client.binding.AbstractBinder;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 13, 2009
 * Time: 12:44:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class MetadataBinder
extends AbstractBinder
{
   
   public AllMetadataBindingBuilder bindMetadataOf(Field field)
   {
      return new AllMetadataBindingBuilder(this, MetadataPlugin.getMetadata(field));
   }
   
   public VisibilityBindingBuilder bindVisibilityOf(Field field)
   {
      return new VisibilityBindingBuilder(this, MetadataPlugin.getMetadata(field).getVisibleModel());
   }
   
   public EnabledBindingBuilder bindEnabledOf(Field field)
   {
      return new EnabledBindingBuilder(this, MetadataPlugin.getMetadata(field).getEnabledModel());
   }
   
   public VisibilityBindingBuilder bindValueOf(ValueModel<Boolean> model)
   {
      return new VisibilityBindingBuilder(this, model);
   }

}