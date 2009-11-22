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


import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.pietschy.gwt.pectin.client.bean.test.TestBean;
import com.pietschy.gwt.pectin.client.bean.test.TestBeanModelProvider;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @since <pre>09/23/2009</pre>
 * @version 1.0
 */
public class BeanPropertyValueModelTest extends GWTTestCase
{

   private TestBeanModelProvider provider;

   @Override
   protected void gwtSetUp() throws Exception
   {
      provider = GWT.create(TestBeanModelProvider.class);
   }

   public String getModuleName()
   {
      return "com.pietschy.gwt.pectin.Pectin";
   }

   public void testGetValue()
   {
      BeanPropertyValueModel<String> vm = provider.getValueModel("string", String.class);
      assertNull(vm.getValue());
      TestBean bean = new TestBean();
      String name = "abc";
      bean.setString(name);
      provider.setBean(bean);
      assertFalse(vm.getDirtyModel().getValue());
      assertEquals(vm.getValue(), name);
   }

   public void testSetValue()
   {
      BeanPropertyValueModel<String> vm = provider.getValueModel("string", String.class);

      TestBean bean = new TestBean();
      provider.setBean(bean);

      String value = "abc";
      assertNull(bean.getString());
      assertFalse(vm.getDirtyModel().getValue());

      vm.setValue(value);
      assertTrue(vm.getDirtyModel().getValue());
      assertNull(bean.getString());
      provider.commit();
      assertEquals(bean.getString(), value);
      assertFalse(vm.getDirtyModel().getValue());

   }

   public void testSetValueWithAutoCommit()
   {
      provider.setAutoCommit(true);
      BeanPropertyValueModel<String> vm = provider.getValueModel("string", String.class);

      TestBean bean = new TestBean();
      provider.setBean(bean);

      String value = "abc";

      assertNull(bean.getString());
      assertFalse(vm.getDirtyModel().getValue());

      vm.setValue(value);
      assertEquals(bean.getString(), value);
      assertFalse(vm.getDirtyModel().getValue());
   }

}