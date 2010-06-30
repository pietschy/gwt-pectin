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

import com.pietschy.gwt.pectin.client.bean.AbstractBeanModelProvider;
import com.pietschy.gwt.pectin.client.bean.Path;
import com.pietschy.gwt.pectin.client.bean.PropertyDescriptor;

import java.util.HashMap;

/**
 * This class provides an implementation of {@link com.pietschy.gwt.pectin.client.bean.BeanModelProvider}
 * that can be used in JVM based tests.  This class <b>can not</b> be used withing client code as it uses reflection.
 */
public class ReflectionBeanModelProvider<B> extends AbstractBeanModelProvider<B>
{
   private BeanDescriptor rootDescriptor;
   private HashMap<String, BeanDescriptor> beanAccessors = new HashMap<String, BeanDescriptor>();

   public ReflectionBeanModelProvider(Class<B> clazz)
   {
      rootDescriptor = new BeanDescriptor(clazz);
   }

   @Override
   public PropertyDescriptor getPropertyDescriptor(String fullPath)
   {
      ComputedPath path = new ComputedPath(fullPath);

      return getBeanDescriptorFor(path).getPropertyDescriptor(path);
   }

   private BeanDescriptor getBeanDescriptorFor(Path path)
   {
      if (path.isTopLevel())
      {
         return rootDescriptor;
      }
      else
      {
         // try and get the cached version.
         BeanDescriptor descriptor = beanAccessors.get(path.getParentPath());

         if (descriptor == null)
         {
            ComputedPath parentPath = new ComputedPath(path.getParentPath());
            BeanDescriptor parentDescriptor = getBeanDescriptorFor(parentPath);

            Class parentBeanType = parentDescriptor.getPropertyType(parentPath);

            ensureLegalNestedType(parentBeanType);

            descriptor = new BeanDescriptor(parentBeanType);
         }

         return descriptor;
      }
   }

   private void ensureLegalNestedType(Class parentBeanType)
   {
      // todo: implement
   }
}
