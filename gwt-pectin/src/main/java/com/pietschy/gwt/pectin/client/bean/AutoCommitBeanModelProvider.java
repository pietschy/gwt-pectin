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
import com.pietschy.gwt.pectin.client.list.ListModelChangedEvent;
import com.pietschy.gwt.pectin.client.list.ListModelChangedHandler;
import com.pietschy.gwt.pectin.client.value.*;


/**
 * BeanModelProvider is a factory for creating {@link com.pietschy.gwt.pectin.client.value.ValueModel}s and {@link com.pietschy.gwt.pectin.client.list.ListModel}s from
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
public abstract class AutoCommitBeanModelProvider<B> extends AbstractBeanModelProvider<B> implements ValueSource<B>, MutableValue<B>
{

   private DelegatingValueModel<B> beanSource = new DelegatingValueModel<B>(new ValueHolder<B>());

   private HandlerRegistration beanChangeRegistration;

   private ValueChangeHandler<B> beanChangeHandler = new ValueChangeHandler<B>()
   {
      public void onValueChange(ValueChangeEvent<B> event)
      {
         readFrom(event.getValue());
      }
   };

   private boolean disableCommit = false;


   protected AutoCommitBeanModelProvider()
   {
      beanSource.addValueChangeHandler(beanChangeHandler);
   }

   @Override
   protected <T> BeanPropertyValueModel<B, T> createValueModel(String propertyName)
   {
      // create models that are never dirty
      final BeanPropertyValueModel<B, T> model = new BeanPropertyValueModel<B, T>(this, propertyName)
      {
         @Override
         boolean computeDirty()
         {
            return false;
         }
      };

      // initialise the model if the bean has already been configured
      if (getBean() != null)
      {
         model.readFrom(getBean());
      }

      // and track changes for committing.
      model.addValueChangeHandler(new ValueChangeHandler<T>()
      {
         public void onValueChange(ValueChangeEvent<T> event)
         {
            doCommit(model);
         }
      });
      return model;
   }

   @Override
   protected <T> BeanPropertyListModel<B, T> createListValueModel(String propertyName, CollectionConverter converter)
   {
      // create models that are never dirty
      final BeanPropertyListModel<B, T> model = new BeanPropertyListModel<B, T>(this, propertyName, converter)
      {
         @Override
         protected boolean computeDirty()
         {
            return false;
         }
      };

      // initialise the model if the bean has already been configured
      if (getBean() != null)
      {
         model.readFrom(getBean());
      }

      // and track changes for committing.
      model.addListModelChangedHandler(new ListModelChangedHandler<T>()
      {
         public void onListDataChanged(ListModelChangedEvent<T> event)
         {
            doCommit(model);
         }
      });


      return model;
   }


   void doCommit(BeanPropertyValueModel<B, ?> valueModel)
   {
      // we only commit if we've been changed outside of a call to readFrom(Bean)
      if (!disableCommit)
      {
         valueModel.copyTo(getBean(), true);
      }
   }

   void doCommit(BeanPropertyListModel<B, ?> listModel)
   {
      // we only commit if we've been changed outside of a call to readFrom(Bean)
      if (!disableCommit)
      {
         listModel.copyTo(getBean(), true);
      }
   }

   /**
    * Sets the bean to back all the models created by this provider.  All value models will update after this
    * method has been called.
    * <p/>
    * <b>Please note<b/> if {@link #setBeanSource(com.pietschy.gwt.pectin.client.value.ValueModel)} has been called
    * with a model that isn't an instance of {@link com.pietschy.gwt.pectin.client.value.MutableValueModel}
    * then this method will fail.
    *
    * @param bean the bean
    */
   public void setBean(B bean)
   {
      beanSource.setValue(bean);
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
    * Sets the {@link ValueModel} to be used as the source of this provider.  All changes to the source
    * model will be tracked.
    * <p/>
    * <b>Please note<b/> that if the source is not an instance of {@link com.pietschy.gwt.pectin.client.value.MutableValueModel}
    * then subsequent calls to {@link #setBean(Object)} will fail.
    *
    * @param source the {@link com.pietschy.gwt.pectin.client.value.ValueModel} containing the source bean.
    */
   public void setBeanSource(ValueModel<B> source)
   {
      beanSource.setDelegate(source);
   }


   public B getValue()
   {
      return getBean();
   }

   public void setValue(B value)
   {
      setBean(value);
   }

   @Override
   protected void readFrom(B bean)
   {
      // storing the old value makes us re-entrant
      // just in case.
      boolean old = disableCommit;
      try
      {
         disableCommit = true;
         super.readFrom(bean);
      }
      finally
      {
         disableCommit = old;
      }
   }
}