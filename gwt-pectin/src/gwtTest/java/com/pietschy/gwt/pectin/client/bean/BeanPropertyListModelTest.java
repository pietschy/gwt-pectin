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
import java.util.Collection;
import java.util.HashSet;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @since <pre>09/23/2009</pre>
 * @version 1.0
 */
public class BeanPropertyListModelTest extends GWTTestCase
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

   public void testGetCollectionValue()
   {
      BeanPropertyListModel<String> lm = provider.getListModel("set", String.class);

      TestBean bean = new TestBean();
      bean.setSet(new HashSet<String>(Arrays.asList("abc","def","ghi")));
      provider.setBean(bean);

      assertContentEquals(lm.asUnmodifiableList(), "abc","def","ghi");
      assertFalse(lm.getDirtyModel().getValue());
   }

   public void testGetWithNullCollection()
   {
      BeanPropertyListModel<String> lm = provider.getListModel("set", String.class);

      TestBean bean = new TestBean();
      provider.setBean(bean);

      assertEquals(lm.size(), 0);
      assertFalse(lm.getDirtyModel().getValue());
   }

   public void testCollectionSetValue()
   {
      BeanPropertyListModel<String> lm = provider.getListModel("set", String.class);

      TestBean bean = new TestBean();
      provider.setBean(bean);
      assertFalse(lm.getDirtyModel().getValue());

      lm.add("abc");
      assertNull(bean.getSet());
      assertTrue(lm.getDirtyModel().getValue());

      provider.commit();
      assertContentEquals(bean.getSet(), "abc");
      assertFalse(lm.getDirtyModel().getValue());

      lm.add("def");
      assertContentEquals(bean.getSet(), "abc");
      assertTrue(lm.getDirtyModel().getValue());
      provider.commit();
      assertContentEquals(bean.getSet(), "abc", "def");
      assertFalse(lm.getDirtyModel().getValue());

      lm.remove("abc");
      assertContentEquals(bean.getSet(), "abc", "def");
      assertTrue(lm.getDirtyModel().getValue());
      provider.commit();
      assertContentEquals(bean.getSet(), "def");
      assertFalse(lm.getDirtyModel().getValue());

      lm.setElements(Arrays.asList("abc", "xyz"));
      assertContentEquals(bean.getSet(), "def");
      assertTrue(lm.getDirtyModel().getValue());
      provider.commit();
      assertContentEquals(bean.getSet(), "abc", "xyz");
      assertFalse(lm.getDirtyModel().getValue());

   }

   public void testSetCollectionValueWithAutoCommit()
   {
      provider.setAutoCommit(true);
      
      BeanPropertyListModel<String> lm = provider.getListModel("set", String.class);

      TestBean bean = new TestBean();
      provider.setBean(bean);

      assertNull("bean value is null", bean.getSet());
      lm.add("abc");
      assertContentEquals(bean.getSet(), "abc");
      assertFalse("not dirty after add", lm.getDirtyModel().getValue());

      lm.add("def");
      assertContentEquals(bean.getSet(), "abc", "def");
      assertFalse("not dirty after second add", lm.getDirtyModel().getValue());

      lm.remove("abc");
      assertContentEquals(bean.getSet(), "def");
      assertFalse("not dirty after remove", lm.getDirtyModel().getValue());

      lm.setElements(Arrays.asList("abc", "xyz"));
      assertContentEquals(bean.getSet(), "abc", "xyz");
      assertFalse("not dirty after setElements", lm.getDirtyModel().getValue());
   }

   public void testDirtyIngoresCollectionOrder()
   {
      BeanPropertyListModel<String> lm = provider.getListModel("list", String.class);

      TestBean bean = new TestBean();
      bean.setList(new ArrayList());
      provider.setBean(bean);

      lm.setElements(Arrays.asList("def", "abc", "qbt"));
      provider.commit();
      lm.setElements(Arrays.asList("qbt", "def", "abc"));
      assertFalse(lm.getDirtyModel().getValue());
   }


   public void assertContentEquals(Collection<?> list, Object... values)
   {
      assertContentEquals(list, Arrays.asList(values));
   }

   public void assertContentEquals(Collection<?> list, Collection<?> values)
   {
      if (list.size() != values.size())
      {
         fail("collections are different sizes: list=" + list.toString() + ", values= " + values.toString());
      }

      if (!list.containsAll(values))
      {
         fail("contents are different, list=" + list.toString() + ", values= " + values.toString());
      }
   }
}