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

import com.pietschy.gwt.pectin.client.bean.AutoCommitBeanModelProvider;
import com.pietschy.gwt.pectin.client.bean.BeanModelProvider;

/**
 * Utilities for testing.
 */
public class ReflectionProviders
{
   /**
    * Creates a bean model provider that can be used in JRE based tests. i.e. without
    * calling GWT.create(...).
    * <p/>
    * The provider returned by this class <b>can not be used in client code</b>.
    *
    * @param clazz the class wrapped by the provider.
    * @return a reflection based provider for JVM tests.
    */
   public static <T> BeanModelProvider<T> providerFor(Class<T> clazz)
   {
      return new ReflectionBeanModelProvider<T>(clazz);
   }

   /**
    * Creates a bean model provider that can be used in JRE based tests. i.e. without
    * calling GWT.create(...).
    * <p/>
    * The provider returned by this class <b>can not be used in client code</b>.
    *
    * @param clazz the class wrapped by the provider.
    * @return a reflection based provider for JVM tests.
    */
   public static <T> AutoCommitBeanModelProvider<T> autoCommitProviderFor(Class<T> clazz)
   {
      return new ReflectionAutoCommitBeanModelProvider<T>(clazz);
   }

}
