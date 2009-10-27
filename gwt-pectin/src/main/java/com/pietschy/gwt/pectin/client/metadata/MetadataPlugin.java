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

import com.pietschy.gwt.pectin.client.Field;
import com.pietschy.gwt.pectin.client.FieldModel;
import com.pietschy.gwt.pectin.client.FormModel;
import com.pietschy.gwt.pectin.client.FormattedFieldModel;
import com.pietschy.gwt.pectin.client.metadata.binding.MetadataBindingBuilder;

/**
 * 
 */
public class MetadataPlugin
{
   public static <T> MetadataBindingBuilder<T> enable(Field field)
   {
      return prepareBindingBuilder(field, new MetadataBindingBuilder.Action()
      {
         public void apply(Metadata metadata, boolean value)
         {
            metadata.setEnabled(value);
         }
      });
   }

   public static <T> MetadataBindingBuilder<T> disable(Field field)
   {
      return prepareBindingBuilder(field, new MetadataBindingBuilder.Action()
      {
         public void apply(Metadata metadata, boolean value)
         {
            metadata.setEnabled(!value);
         }
      });
   }

   public static <T> MetadataBindingBuilder<T> show(Field field)
   {
      return prepareBindingBuilder(field, new MetadataBindingBuilder.Action()
      {
         public void apply(Metadata metadata, boolean value)
         {
            metadata.setVisible(value);
         }
      });
   }

   public static <T> MetadataBindingBuilder<T> hide(Field field)
   {
      return prepareBindingBuilder(field, new MetadataBindingBuilder.Action()
      {
         public void apply(Metadata metadata, boolean value)
         {
            metadata.setVisible(!value);
         }
      });
   }

   public static <T> MetadataBindingBuilder<T>
   prepareBindingBuilder(Field field, MetadataBindingBuilder.Action action)
   {
      return new MetadataBindingBuilder<T>(getMetadata(field), action);
   }

   public static WatermarkBindingBuilder watermark(FieldModel<String> field)
   {
      return new WatermarkBindingBuilder(getMetadata(field));
   }

   public static WatermarkBindingBuilder watermark(FormattedFieldModel<?> field)
   {
      return new WatermarkBindingBuilder(getMetadata(field)); 
   }

   public static MetadataConditionBuidler metadataOf(Field field)
   {
      return new MetadataConditionBuidler(getMetadata(field));
   }
   
   public static Metadata getMetadata(Field field)
   {
      return getMetadataManager(field.getFormModel()).getMetadata(field);
   }

   public static MetadataManager getMetadataManager(FormModel form)
   {
      MetadataManager manager = (MetadataManager) form.getProperty(MetadataManager.class);
      if (manager == null)
      {
         manager = new MetadataManager();
         form.putProperty(MetadataManager.class, manager);
         form.addBindingCallback(manager);
      }

      return manager;
   }
   
}
