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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
      assertEquals(vm.getValue(), name);
   }

   public void testSetValue()
   {
      BeanPropertyValueModel<String> vm = provider.getValueModel("string", String.class);

      TestBean bean = new TestBean();
      provider.setBean(bean);

      String value = "abc";
      assertNull(bean.getString());
      vm.setValue(value);

      assertNull(bean.getString());
      provider.commit();
      assertEquals(bean.getString(), value);

   }

   public void testSetValueWithAutoCommit()
   {
      provider.setAutoCommit(true);
      BeanPropertyValueModel<String> vm = provider.getValueModel("string", String.class);

      TestBean bean = new TestBean();
      provider.setBean(bean);

      String value = "abc";

      assertNull(bean.getString());
      vm.setValue(value);
      assertEquals(bean.getString(), value);
   }

   public void testGetCollectionValue()
   {

      BeanPropertyListModel<String> lm = provider.getListModel("list", String.class);

      TestBean bean = new TestBean();
      List<String> arrayList = Arrays.asList("abc","def","ghi");
      bean.setList(arrayList);
      provider.setBean(bean);

      assertContentEquals(lm.asUnmodifiableList(), arrayList);
   }

   public void testCollectionSetValue()
   {
      BeanPropertyListModel<String> lm = provider.getListModel("list", String.class);

      TestBean bean = new TestBean();
      bean.setList(new ArrayList());
      provider.setBean(bean);

      lm.add("abc");
      assertEquals(bean.getList().size(), 0);

      provider.commit();
      assertContentEquals(bean.getList(), "abc");

      lm.add("def");
      assertContentEquals(bean.getList(), "abc");
      provider.commit();
      assertContentEquals(bean.getList(), "abc", "def");

      lm.remove("abc");
      assertContentEquals(bean.getList(), "abc", "def");
      provider.commit();
      assertContentEquals(bean.getList(), "def");
      
      lm.setElements(Arrays.asList("abc", "xyz"));
      assertContentEquals(bean.getList(), "def");
      provider.commit();
      assertContentEquals(bean.getList(), "abc", "xyz");

   }

   public void testSetCollectionValueWithAutoCommit()
   {
      provider.setAutoCommit(true);
      BeanPropertyListModel<String> lm = provider.getListModel("list", String.class);

      TestBean bean = new TestBean();
      bean.setList(new ArrayList());
      provider.setBean(bean);

      assertEquals(bean.getList().size(), 0);
      lm.add("abc");
      assertContentEquals(bean.getList(), "abc");

      lm.add("def");
      assertContentEquals(bean.getList(), "abc", "def");

      lm.remove("abc");
      assertContentEquals(bean.getList(), "def");

      lm.setElements(Arrays.asList("abc", "xyz"));
      assertContentEquals(bean.getList(), "abc", "xyz");
   }
   

   public void assertContentEquals(List<?> list, Object... values)
   {
      assertContentEquals(list, Arrays.asList(values));
   }

   public void assertContentEquals(List<?> list, List<?> values)
   {
      if (list.size() != values.size())
      {
         fail("lists are different sizes: list=" + list.toString() + ", values= " + values.toString());
      }

      for (int i = 0; i < list.size(); i++)
      {
         Object oa = list.get(i);
         Object ob = values.get(i);


         if (!areEqual(oa, ob))
         {
            fail("value at index=" +i+ " are different, list=" + list.toString() + ", values= " + values.toString());
         }
      }
   }

   private boolean areEqual(Object oa, Object ob)
   {
      return oa == null ? ob == null : oa.equals(ob);
   }


}