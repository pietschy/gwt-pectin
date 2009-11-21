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

package com.pietschy.gwt.pectin.client.metadata;

import com.google.gwt.user.client.ui.TextBox;
import com.pietschy.gwt.pectin.client.*;
import com.pietschy.gwt.pectin.client.binding.AbstractFieldBinding;
import com.pietschy.gwt.pectin.client.binding.AbstractFormattedBinding;
import com.pietschy.gwt.pectin.client.binding.AbstractFormattedListBinding;
import com.pietschy.gwt.pectin.client.binding.AbstractListBinding;
import com.pietschy.gwt.pectin.client.metadata.binding.AllMetadataBindingBuilder;
import com.pietschy.gwt.pectin.client.metadata.binding.WatermarkBinding;
import com.pietschy.gwt.pectin.client.value.ValueModel;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Aug 1, 2009
 * Time: 12:28:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class MetadataManager
   implements BindingCallback
{
   private HashMap<Field, Metadata> metadataMap = new HashMap<Field, Metadata>();

   public Metadata getMetadata(Field fieldModel)
   {
      Metadata metadata = metadataMap.get(fieldModel);

      if (metadata == null)
      {
         metadata = new Metadata();
         metadataMap.put(fieldModel, metadata);
      }

      return metadata;
   }

   public <T> void onWidgetBinding(AbstractFieldBinding<T> binding, FieldModel<T> model, Object target)
   {
      new AllMetadataBindingBuilder(binding, getMetadata(model)).to(target);

      // if we're a FieldModel<String> bound to a text box then apply the water mark.
      if (String.class.getName().equals(model.getValueClass().getName()) && target instanceof TextBox)
      {
         binding.registerAndInitialiseBinding(new WatermarkBinding((ValueModel<String>) model,
                                                                   getMetadata(model).getWatermarkModel(),
                                                                   (TextBox) target));
      }

   }

   public <T> void onWidgetBinding(AbstractFormattedBinding<T> binding, FormattedFieldModel<T> model, Object target)
   {
      if (target instanceof TextBox)
      {
         binding.registerAndInitialiseBinding(new WatermarkBinding(model.getTextModel(),
                                                                   getMetadata(model).getWatermarkModel(),
                                                                   (TextBox) target));
      }

      new AllMetadataBindingBuilder(binding, getMetadata(model)).to(target);
   }

   public <T> void onWidgetBinding(AbstractListBinding binding, ListFieldModel<T> model, Object target)
   {
      new AllMetadataBindingBuilder(binding, getMetadata(model)).to(target);
   }

   public <T> void onWidgetBinding(AbstractFormattedListBinding<T> binding, FormattedListFieldModel<T> model, Object target)
   {
      // todo: should really support water marks here too....
      new AllMetadataBindingBuilder(binding, getMetadata(model)).to(target);
   }
}
