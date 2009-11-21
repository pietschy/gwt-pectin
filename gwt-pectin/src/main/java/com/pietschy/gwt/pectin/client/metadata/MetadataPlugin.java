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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * 
 */
public class MetadataPlugin
{

   public static <T> MetadataBindingBuilder<T> enable(Field field, Field... others)
   {
      return enable(toCollection(field, others));
   }

   public static <T> MetadataBindingBuilder<T> enable(Collection<Field<?>> fields)
   {
      return new MetadataBindingBuilder<T>(fields, new MetadataBindingBuilder.Action()
      {
         public void apply(Metadata metadata, boolean value)
         {
            metadata.setEnabled(value);
         }
      });
   }

   public static <T> MetadataBindingBuilder<T> disable(Field field, Field... others)
   {
      return disable(toCollection(field, others));
   }

   public static <T> MetadataBindingBuilder<T> disable(Collection<Field<?>> fields)
   {
      return new MetadataBindingBuilder<T>(fields, new MetadataBindingBuilder.Action()
      {
         public void apply(Metadata metadata, boolean value)
         {
            metadata.setEnabled(!value);
         }
      });
   }

   public static <T> MetadataBindingBuilder<T> show(Field field, Field... others)
   {
      return show(toCollection(field, others));
   }

   public static <T> MetadataBindingBuilder<T> show(Collection<Field<?>> fields)
   {
      return new MetadataBindingBuilder<T>(fields, new MetadataBindingBuilder.Action()
      {
         public void apply(Metadata metadata, boolean value)
         {
            metadata.setVisible(value);
         }
      });
   }

   public static <T> MetadataBindingBuilder<T> hide(Field field, Field... others)
   {
      return hide(toCollection(field, others));
   }

   public static <T> MetadataBindingBuilder<T> hide(Collection<Field<?>> fields)
   {
      return new MetadataBindingBuilder<T>(fields, new MetadataBindingBuilder.Action()
      {
         public void apply(Metadata metadata, boolean value)
         {
            metadata.setVisible(!value);
         }
      });
   }

   private static <T> Collection<Field<?>> toCollection(Field<?> field, Field<?>... others)
   {
      ArrayList<Field<?>> fields = new ArrayList<Field<?>>();
      fields.add(field);
      fields.addAll(Arrays.asList(others));
      return fields;
   }

   public static WatermarkBuilder watermark(FieldModel<String> field)
   {
      return new WatermarkBuilder(getMetadata(field));
   }

   public static WatermarkBuilder watermark(FormattedFieldModel<?> field)
   {
      return new WatermarkBuilder(getMetadata(field));
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

   /**
    * This method ensures that the metadata binding callbacks are installed.  The callbacks ensure that the Metadata
    * plugin is activated every time {@link com.pietschy.gwt.pectin.client.binding.WidgetBinder} is used.
    * <p>
    * This method is only needed if
    * you haven't called any of the {@link #enable(com.pietschy.gwt.pectin.client.Field, com.pietschy.gwt.pectin.client.Field[])} style methods
    * or the {@link #getMetadata(com.pietschy.gwt.pectin.client.Field)} or the
    * {@link #getMetadataManager(com.pietschy.gwt.pectin.client.FormModel)} method.
    * <p>
    * Typically you'd use this if you want the metadata plugin automatically configure widgets based only on
    * the state of {@link com.pietschy.gwt.pectin.client.Field#isMutableSource()}.
    *
    * @param form the form.
    */
   public static void ensureMetadataBindingsInstalled(FormModel form)
   {
      getMetadataManager(form);
   }
   
}
