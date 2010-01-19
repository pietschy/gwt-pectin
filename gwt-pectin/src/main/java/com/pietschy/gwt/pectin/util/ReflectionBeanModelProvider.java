/*
 * Copyright 2010 Andrew Pietsch
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

package com.pietschy.gwt.pectin.util;

import com.pietschy.gwt.pectin.client.bean.BeanModelProvider;
import com.pietschy.gwt.pectin.client.bean.UnknownPropertyException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class provides an implementation of {@link com.pietschy.gwt.pectin.client.bean.BeanModelProvider}
 * that can be used in JVM based tests.  This class <b>can not</b> be used withing client code as it uses reflection.
 */
class ReflectionBeanModelProvider<B> extends BeanModelProvider<B>
{
   private Class<B> clazz;

   public static <B> ReflectionBeanModelProvider<B> newInstance(Class<B> clazz)
   {
      return new ReflectionBeanModelProvider<B>(clazz);
   }

   public ReflectionBeanModelProvider(Class<B> clazz)
   {
      this.clazz = clazz;
   }

   protected Class getPropertyType(String property) throws UnknownPropertyException
   {
      return getGetter(property).getReturnType();
   }

   protected Object readValue(String property) throws UnknownPropertyException
   {
      if (getBean() == null)
      {
         return null;
      }

      try
      {
         return getGetter(property).invoke(getBean());
      }
      catch (IllegalAccessException e)
      {
         throw new UnknownPropertyException(property);
      }
      catch (InvocationTargetException e)
      {
         throw new UnknownPropertyException(property);
      }
   }

   protected void writeValue(String property, Object value) throws UnknownPropertyException
   {
      if (getBean() == null)
      {
         throw new IllegalStateException("Bean is null");
      }

      try
      {
         getSetter(property).invoke(getBean(), value);
      }
      catch (IllegalAccessException e)
      {
         throw new UnknownPropertyException(property);
      }
      catch (InvocationTargetException e)
      {
         throw new UnknownPropertyException(property);
      }
   }

   private Method getGetter(String property) throws UnknownPropertyException
   {
      try
      {
         return clazz.getMethod("get" + capitalise(property));
      }
      catch (NoSuchMethodException e)
      {
         try
         {
            // should really test it's return type is boolean...
            return clazz.getMethod("is" + capitalise(property));
         }
         catch (NoSuchMethodException e1)
         {
            throw new UnknownPropertyException(property);
         }
      }
   }

   private Method getSetter(String property) throws UnknownPropertyException
   {
      try
      {
         return clazz.getMethod("set" + capitalise(property), getPropertyType(property));
      }
      catch (NoSuchMethodException e)
      {
         throw new UnknownPropertyException(property);
      }
   }

   private String capitalise(String property)
   {
      return property.substring(0, 1).toUpperCase() + property.substring(1);
   }


}
