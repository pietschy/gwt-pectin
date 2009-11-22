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
import com.pietschy.gwt.pectin.client.condition.OrFunction;
import com.pietschy.gwt.pectin.client.list.ListModel;
import com.pietschy.gwt.pectin.client.value.MutableValueModel;
import com.pietschy.gwt.pectin.client.value.ReducingValueModel;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import com.pietschy.gwt.pectin.client.value.ValueModel;

import java.util.HashMap;


/**
 * BeanModelProvider is a factory for creating {@link ValueModel}s and {@link ListModel}s from
 * Java Bean properties.  This class is created using <code>GWT.create(...)</code>.
 * <p/>
 * An example:
 * <pre>
 * // create an abstract subclass with your bean type.
 * public static abstract class MyBeanModelProvider extends BeanModelProvider&lt;MyBean&gt;{}
 * <p/>
 * // and use GWT.create(..) to get a new instance.
 * MyBeanModelProvider <b>provider</b> = GWT.create(MyBeanModelProvider.class);
 * <p/>
 * // then use it in your form
 * FieldModel<String> myField = fieldOfType(String.class).boundTo(<b>provider</b>, "myProperty");
 * <p/>
 * // and load the bean you want to edit.
 * <b>provider</b>.setBean(new MyBean());
 * <p/>
 * // or you can use another value model as the bean source.
 * ValueModel<MyBean> myBeanSource = ...;
 * <b>provider</b>.setBeanSource(myBeanSource);
 * <p/>
 * </pre>
 */
public abstract class BeanModelProvider<B>
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

   private ReducingValueModel<Boolean, Boolean> dirtyModel = new ReducingValueModel<Boolean, Boolean>(new OrFunction());
   private boolean autoCommit = false;

   protected BeanModelProvider()
   {
      setBeanSource(new ValueHolder<B>());
   }

   /**
    * Writes the values of all models the the current bean.
    */
   public void commit()
   {
      // performace could be improved here if the dirty model went deaf for a bit.
      for (BeanPropertyValueModel<?> model : valueModels.values())
      {
         model.commit();
      }

      for (BeanPropertyListModel<?> model : listModels.values())
      {
         model.commit();
      }
   }

   /**
    * Gets the bean in use by the provider.
    *
    * @return the bean in use by the provider.
    */
   public B getBean()
   {
      return beanSource.getValue();
   }

   /**
    * Gets the dirty model for this provider.
    * @return the dirty model for this provider.
    */
   public ValueModel<Boolean> getDirtyModel()
   {
      return dirtyModel;
   }

   /**
    * Gets a {@link ListModel} based on the specified property name and value type.  Property types
    * of the generic interface types {@link java.util.Collection}, {@link java.util.List}, {@link java.util.Set}, {@link java.util.SortedSet} are supported
    * out of the box.  Additional types can be supported by registering a suitable {@link CollectionConverter}.
    * <p/>
    * Multiple calls to this method will return the same model.
    *
    * @param propertyName the name of the property.
    * @param valueType    the type contained by the collection
    * @return the {@link ListModel} for the specified property.  Multiple calls to this method will return the same
    *         model.
    * @throws UnknownPropertyException if the bean doesn't define the specified property.
    * @throws UnsupportedCollectionTypeException
    *                                  if a suitable {@link CollectionConverter} hasn't been registered
    *                                  for the bean property collection type.
    * @see #registerCollectionConverter(Class, CollectionConverter)
    */
   @SuppressWarnings("unchecked")
   public <T> BeanPropertyListModel<T> getListModel(String propertyName, Class<T> valueType) throws UnknownPropertyException, UnsupportedCollectionTypeException
   {
      Key<T> key = new Key<T>(valueType, propertyName);
      BeanPropertyListModel<T> listModel = (BeanPropertyListModel<T>) listModels.get(key);

      if (listModel == null)
      {

         Class collectionType = getPropertyType(propertyName);

         CollectionConverter converter = collectionConverters.getConverter(collectionType);

         if (converter == null)
         {
            throw new UnsupportedCollectionTypeException(collectionType);
         }

         listModel = new BeanPropertyListModel<T>(this, propertyName, converter);
         listModels.put(key, listModel);
         dirtyModel.addSourceModel(listModel.getDirtyModel());
      }

      return listModel;
   }

   /**
    * Gets a {@link ValueModel} for the specified bean property and the specified type.  Multiple calls to this method
    * will return the same model.
    *
    * @param propertyName the name of the property.
    * @param modelType    the type of the property.
    * @return a {@link ValueModel} for the specified bean property.  Multiple calls to this method
    *         will return the same model.
    * @throws UnknownPropertyException       if the property isn't defined by the bean.
    * @throws IncorrectPropertyTypeException if the type of the property doesn't match the model type.
    */
   @SuppressWarnings("unchecked")
   public <T> BeanPropertyValueModel<T> getValueModel(String propertyName, Class<T> modelType) throws UnknownPropertyException, IncorrectPropertyTypeException
   {
      Class beanPropertyType = getPropertyType(propertyName);

      if (!modelType.equals(beanPropertyType))
      {
         throw new IncorrectPropertyTypeException(modelType, beanPropertyType);
      }

      Key<T> key = new Key<T>(modelType, propertyName);
      BeanPropertyValueModel<T> valueModel = (BeanPropertyValueModel<T>) valueModels.get(key);

      if (valueModel == null)
      {
         valueModel = new BeanPropertyValueModel<T>(this, propertyName);
         valueModels.put(key, valueModel);
         dirtyModel.addSourceModel(valueModel.getDirtyModel());
      }

      return valueModel;
   }

   /**
    * Registers a new converter for converting between collection based bean properties and the ListModel.
    * This allows uses to support collection types other than the generic interface types on their beans.
    *
    * @param collectionClass the colleciton type of the bean property.
    * @param converter       the converter to use for bean properties of the specified collection type.
    */
   public <T> void registerCollectionConverter(Class<T> collectionClass, CollectionConverter<T> converter)
   {
      collectionConverters.register(collectionClass, converter);
   }

   /**
    * Reverts all the models in this provider to the values contained in the bean.
    */
   public void revert()
   {
      // performace could be improved here if the dirty model went deaf for a bit.
      for (BeanPropertyValueModel<?> model : valueModels.values())
      {
         model.revert();
      }

      for (BeanPropertyListModel<?> model : listModels.values())
      {
         model.revert();
      }
   }

   /**
    * Sets the bean to back all the models created by this provider.  All value models will update after this
    * method has been called.
    *
    * @param bean the bean
    */
   public void setBean(B bean)
   {
      beanSource.setValue(bean);
   }

   /**
    * Sets the {@link ValueModel} to be used as the source of this provider.  All changes to the source
    * model will be tracked.
    *
    * @param source the {@link ValueModel} containing the source bean.
    */
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

   public boolean isAutoCommit()
   {
      return autoCommit;
   }

   public void setAutoCommit(boolean autoCommit)
   {
      this.autoCommit = autoCommit;
   }


   protected abstract Class getPropertyType(String property) throws UnknownPropertyException;

   protected abstract Object readValue(String property) throws UnknownPropertyException;

   protected abstract void writeValue(String property, Object value) throws UnknownPropertyException;


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