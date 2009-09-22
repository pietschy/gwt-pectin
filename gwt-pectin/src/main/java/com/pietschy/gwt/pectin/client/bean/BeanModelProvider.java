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

package com.pietschy.gwt.pectin.client.bean;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.pietschy.gwt.pectin.client.ListModelProvider;
import com.pietschy.gwt.pectin.client.ValueModelProvider;
import com.pietschy.gwt.pectin.client.value.MutableValueModel;
import com.pietschy.gwt.pectin.client.value.ValueHolder;

import java.util.HashMap;


/**
 * BeanModelProvider is a facgtory for creating {@link ValueModel}s and 
 * {@link ListModel}s from Java Beans.
 */
public abstract class BeanModelProvider<B extends BeanPropertySource>
implements ValueModelProvider, ListModelProvider
{

   private HashMap<Key<?>, BeanPropertyValueModel<?>> valueModels = new HashMap<Key<?>, BeanPropertyValueModel<?>>();
   private HashMap<Key<?>, BeanPropertyListModel<?>> listModels = new HashMap<Key<?>, BeanPropertyListModel<?>>();

   private CollectionConverters collectionConverters = new CollectionConverters();

   private ValueChangeHandler<B> beanChangeHandler = new ValueChangeHandler<B>()
   {
      public void onValueChange(ValueChangeEvent<B> event)
      {
         revert();
      }
   };

   private MutableValueModel<B> beanSource;
   private HandlerRegistration beanChangeRegistration;

   protected BeanModelProvider()
   {
      setBeanSource(new ValueHolder<B>());
   }

   /**
    * Writes the values of all models the the current bean.
    */
   public void commit()
   {
      for (BeanPropertyValueModel<?> model : valueModels.values())
      {
         model.commit();
      }

      for (BeanPropertyListModel<?> model : listModels.values())
      {
         model.commit();
      }
   }

   public B getBean()
   {
      return beanSource.getValue();
   }

   @SuppressWarnings("unchecked")
   public <T> BeanPropertyListModel<T> getListModel(String propertyName, Class<T> valueType)
   {
      Key<T> key = new Key<T>(valueType, propertyName);
      BeanPropertyListModel<T> listModel = (BeanPropertyListModel<T>) listModels.get(key);

      if (listModel == null)
      {

         Class collectionType = getPropertyType(propertyName);

         CollectionConverter converter = collectionConverters.getConverter(collectionType);

         if (converter == null)
         {
            throw new IllegalStateException("No collection converter registered for type:" + collectionType +
                                            " Either register a converter or ensure your bean uses only the interface types " +
                                            " Collection, List, Set and SortedSet");
         }

         listModel = new BeanPropertyListModel<T>(this, propertyName, converter);
         listModels.put(key, listModel);
      }

      return (BeanPropertyListModel<T>) listModel;
   }

   @SuppressWarnings("unchecked")
   public <T> BeanPropertyValueModel<T> getValueModel(String propertyName, Class<T> type)
   {

      Class propertyType = getPropertyType(propertyName);

      if (!type.equals(propertyType))
      {
         throw new IllegalStateException("Incorrect bean property type, expected: " + type + " but found: " + propertyType);
      }

      Key<T> key = new Key<T>(type, propertyName);
      BeanPropertyValueModel<T> valueModel = (BeanPropertyValueModel<T>) valueModels.get(key);

      if (valueModel == null)
      {
         valueModel = new BeanPropertyValueModel<T>(this, propertyName);
         valueModels.put(key, valueModel);
      }

      return (BeanPropertyValueModel<T>) valueModel;
   }

   public <T> void registerCollectionConverter(Class<T> collectionClass, CollectionConverter<T> converter)
   {
      collectionConverters.register(collectionClass, converter);
   }

   public void revert()
   {
      for (BeanPropertyValueModel<?> model : valueModels.values())
      {
         model.revert();
      }

      for (BeanPropertyListModel<?> model : listModels.values())
      {
         model.revert();
      }
   }

   public void setBean(B bean)
   {
      beanSource.setValue(bean);
   }

   public void setBeanSource(MutableValueModel<B> source)
   {
      if (source == null)
      {
         throw new NullPointerException("source is null");
      }

      if (beanChangeRegistration != null)
      {
         beanChangeRegistration.removeHandler();
      }

      beanSource = source;

      beanChangeRegistration = beanSource.addValueChangeHandler(beanChangeHandler);
      revert();
   }

   protected abstract Class getPropertyType(String property);

   protected abstract Object readValue(String property);

   protected abstract void writeValue(String property, Object value);

   
   private static class Key<T>
   {
      private Class<T> type;
      private String propertynName;

      private Key(Class<T> type, String propertynName)
      {
         this.type = type;
         this.propertynName = propertynName;
      }

      public boolean equals(Object o)
      {
         if (this == o)
         {
            return true;
         }
         if (o == null || getClass() != o.getClass())
         {
            return false;
         }

         Key<T> key = (Key<T>) o;

         if (propertynName != null ? !propertynName.equals(key.propertynName) : key.propertynName != null)
         {
            return false;
         }
         if (type != null ? !type.equals(key.type) : key.type != null)
         {
            return false;
         }

         return true;
      }

      public int hashCode()
      {
         int result;
         result = (type != null ? type.hashCode() : 0);
         result = 31 * result + (propertynName != null ? propertynName.hashCode() : 0);
         return result;
      }
   }
   
}