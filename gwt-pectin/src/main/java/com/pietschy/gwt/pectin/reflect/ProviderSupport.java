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

package com.pietschy.gwt.pectin.reflect;

import com.pietschy.gwt.pectin.client.bean.BeanIntrospector;
import com.pietschy.gwt.pectin.client.bean.NotCollectionPropertyException;
import com.pietschy.gwt.pectin.client.bean.UnknownPropertyException;

import java.util.HashMap;

/**
 * This class provides an implementation of {@link com.pietschy.gwt.pectin.client.bean.BeanModelProvider}
 * that can be used in JVM based tests.  This class <b>can not</b> be used withing client code as it uses reflection.
 */
class ProviderSupport<B> implements BeanIntrospector
{
   private Class<B> beanClass;
   private HashMap<String, ReflectionBeanPropertyAccessor> accessors = new HashMap<String, ReflectionBeanPropertyAccessor>();

   public ProviderSupport(Class<B> beanClass)
   {
      this.beanClass = beanClass;
   }

   public Class getPropertyType(String propertyPath) throws UnknownPropertyException
   {
      String propertyName = toPath(propertyPath).getPropertyName();
      return getBeanAccessorForPropertyPath(propertyPath).getPropertyType(propertyName);
   }

   private PropertyPath toPath(String propertyPath)
   {
      return new PropertyPath(propertyPath);
   }


   public Class getElementType(String propertyPath) throws UnknownPropertyException, NotCollectionPropertyException
   {
      return getBeanAccessorForPropertyPath(propertyPath).getElementType(toPath(propertyPath).getPropertyName());
   }

   public ReflectionBeanPropertyAccessor getBeanAccessorForPropertyPath(String propertyPath)
   {
      return getAccessorForPath(toPath(propertyPath));
   }

   private ReflectionBeanPropertyAccessor getAccessorForPath(PropertyPath path)
   {
      ReflectionBeanPropertyAccessor accessor = accessors.get(path.getFullPath());
      if (accessor == null)
      {
         accessor = createAccessorForPath(path);

         // check that the property actually exists so we full fill the missing property exception
         // requirements..
         accessor.getPropertyType(path.getPropertyName());

         accessors.put(path.getFullPath(), accessor);
      }
      return accessor;
   }

   private ReflectionBeanPropertyAccessor createAccessorForPath(PropertyPath path)
   {
      if (path.isTopLevel())
      {
         return new ReflectionBeanPropertyAccessor(beanClass);
      }
      else
      {
         // e.g. path is `a.b.c`
         // To get the access for c we need to in effect get the information for property 'b', this means
         // we need the accessor for `a` from which we can determine the type of `b` from which we can
         // create an accessor for property `c`.
         PropertyPath parentPath = toPath(path.getParentPath());
         ReflectionBeanPropertyAccessor parentAccessor = getBeanAccessorForPropertyPath(parentPath.getFullPath());
         return new ReflectionBeanPropertyAccessor(parentAccessor.getPropertyType(parentPath.getPropertyName()));
      }
   }

   private class PropertyPath
   {
      private String propertyName;
      private String parentPath;
      private String fullPath;

      private PropertyPath(String path)
      {
         fullPath = path;
         int dotIndex = path.lastIndexOf('.');
         if (dotIndex < 0)
         {
            propertyName = path;
            parentPath = null;
         }
         else
         {
            propertyName = path.substring(dotIndex + 1);
            parentPath = path.substring(0, dotIndex);
         }
      }

      public String getPropertyName()
      {
         return propertyName;
      }

      public String getParentPath()
      {
         return parentPath;
      }

      public boolean isTopLevel()
      {
         return parentPath == null;
      }

      public String getFullPath()
      {
         return fullPath;
      }
   }
}