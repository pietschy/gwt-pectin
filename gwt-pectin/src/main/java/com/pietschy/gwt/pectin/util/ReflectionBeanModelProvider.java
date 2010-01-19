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
import java.util.HashMap;

/**
 * This class provides an implementation of {@link com.pietschy.gwt.pectin.client.bean.BeanModelProvider}
 * that can be used in JVM based tests.  This class <b>can not</b> be used withing client code as it uses reflection.
 */
class ReflectionBeanModelProvider<B> extends BeanModelProvider<B>
{
   private Class<B> clazz;

   HashMap<Class<?>, Class<?>> primitives = new HashMap<Class<?>, Class<?>>();

   public static <B> ReflectionBeanModelProvider<B> newInstance(Class<B> clazz)
   {
      return new ReflectionBeanModelProvider<B>(clazz);
   }

   public ReflectionBeanModelProvider(Class<B> clazz)
   {
      this.clazz = clazz;

      primitives.put(boolean.class, Boolean.class);
      primitives.put(char.class, Character.class);
      primitives.put(byte.class, Byte.class);
      primitives.put(short.class, Short.class);
      primitives.put(int.class, Integer.class);
      primitives.put(long.class, Long.class);
      primitives.put(float.class, Float.class);
      primitives.put(double.class, Double.class);

   }

   protected Class getPropertyType(String property) throws UnknownPropertyException
   {
      Class<?> type = getBeanPropertyType(property);

      return type.isPrimitive() ? primitives.get(type): type;

   }

   private Class<?> getBeanPropertyType(String property)
   {
      return getGetter(property).getReturnType();
   }

   protected Object readValue(String property) throws UnknownPropertyException
   {
      Method getter = getGetter(property);

      if (getBean() == null)
      {
         return null;
      }

      try
      {
         return getter.invoke(getBean());
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
      Method setter = getSetter(property);

      if (getBean() == null)
      {
         throw new IllegalStateException("Bean is null");
      }

      try
      {
         setter.invoke(getBean(), value);
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
         return clazz.getMethod("set" + capitalise(property), getBeanPropertyType(property));
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
