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


import com.pietschy.gwt.pectin.util.TestBean;
import org.mockito.ArgumentMatcher;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

import static com.pietschy.gwt.pectin.util.AssertUtil.assertContentEquals;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>09/23/2009</pre>
 */
public class BeanPropertyListModelTest
{

   private BeanPropertyAdapter<TestBean> propertyAdapter;
   private TestBean bean;

   @BeforeMethod
   protected void setUp() throws Exception
   {
      bean = mock(TestBean.class);
      propertyAdapter = (BeanPropertyAdapter<TestBean>) mock(BeanPropertyAdapter.class);
   }


   @Test
   public void readFrom()
   {
      BeanPropertyListModel<TestBean, String> lm = new BeanPropertyListModel<TestBean, String>(propertyAdapter,
                                                                                               "set",
                                                                                               CollectionConverters.SET_CONVERTER);


      when(propertyAdapter.readProperty(bean, "set"))
         .thenReturn(new HashSet<String>(Arrays.asList("abc", "def", "ghi")));
      lm.readFrom(bean);

      assertContentEquals(lm.asUnmodifiableList(), "abc", "def", "ghi");
      assertFalse(lm.getDirtyModel().getValue());
   }

   @Test
   public void copyToButWithoutResettingDirty()
   {
      String propertyName = "set";
      when(propertyAdapter.isMutable(propertyName)).thenReturn(true);

      BeanPropertyListModel<TestBean, String> lm = new BeanPropertyListModel<TestBean, String>(propertyAdapter,
                                                                                               propertyName,
                                                                                               CollectionConverters.SET_CONVERTER);
      assertEquals(lm.size(), 0);

      final List<String> values = Arrays.asList("abc", "def");
      lm.setElements(values);

      lm.copyTo(bean, false);

      assertTrue(lm.getDirtyModel().getValue());

      verify(propertyAdapter, times(1)).writeProperty(eq(bean), eq("set"), argThat(new ArgumentMatcher<Set>()
      {
         @Override
         public boolean matches(Object o)
         {
            return o instanceof Set &&
                   ((Set) o).size() == values.size() &&
                   ((Set) o).containsAll(values);

         }
      }));

   }

   @Test
   public void copyToAndResetDirty()
   {
      String propertyName = "set";
      when(propertyAdapter.isMutable(propertyName)).thenReturn(true);

      BeanPropertyListModel<TestBean, String> lm = new BeanPropertyListModel<TestBean, String>(propertyAdapter,
                                                                                               propertyName,
                                                                                               CollectionConverters.SET_CONVERTER);
      assertEquals(lm.size(), 0);

      final List<String> values = Arrays.asList("abc", "def");
      lm.setElements(values);

      TestBean bean = new TestBean();

      lm.copyTo(bean, true);

      assertFalse(lm.getDirtyModel().getValue());

      verify(propertyAdapter, times(1)).writeProperty(eq(bean), eq(propertyName), argThat(new ArgumentMatcher<Set>()
      {
         @Override
         public boolean matches(Object o)
         {
            return o instanceof Set &&
                   ((Set) o).size() == values.size() &&
                   ((Set) o).containsAll(values);

         }
      }));

   }

   @Test
   public void resetDirty()
   {
      String propertyName = "set";
      when(propertyAdapter.isMutable(propertyName)).thenReturn(true);
      BeanPropertyListModel<TestBean, String> lm = new BeanPropertyListModel<TestBean, String>(propertyAdapter,
                                                                                               propertyName,
                                                                                               CollectionConverters.SET_CONVERTER);


      when(propertyAdapter.readProperty(bean, propertyName))
         .thenReturn(new HashSet<String>(Arrays.asList("abc", "def", "ghi")));
      lm.readFrom(bean);

      assertContentEquals(lm.asUnmodifiableList(), "abc", "def", "ghi");
      assertFalse(lm.getDirtyModel().getValue());

      lm.add("blah");
      assertTrue(lm.getDirtyModel().getValue());

      lm.checkpoint();
      assertFalse(lm.getDirtyModel().getValue());
      assertContentEquals(lm.asUnmodifiableList(), "abc", "def", "ghi", "blah");
   }

   @Test
   public void getWithNullCollection()
   {
      BeanPropertyListModel<TestBean, String> lm = new BeanPropertyListModel<TestBean, String>(propertyAdapter,
                                                                                "set",
                                                                                CollectionConverters.SET_CONVERTER);

      when(propertyAdapter.readProperty(bean, "set")).thenReturn(null);
      lm.readFrom(bean);

      assertEquals(lm.size(), 0);
      assertFalse(lm.getDirtyModel().getValue());
   }


   @Test
   public void dirtyChecksCollectionOrder()
   {

      String propertyName = "list";
      when(propertyAdapter.isMutable(propertyName)).thenReturn(true);

      BeanPropertyListModel<TestBean, String> lm = new BeanPropertyListModel<TestBean, String>(propertyAdapter,
                                                                                propertyName,
                                                                                CollectionConverters.LIST_CONVERTER);

      lm.setElements(Arrays.asList("def", "abc", "qbt"));
      // make sure our dirty state is based on the above values
      lm.checkpoint();
      lm.setElements(Arrays.asList("qbt", "def", "abc"));

      assertTrue(lm.getDirtyModel().getValue());
   }

   @Test
   public void dirtyIsNotFooledByDuplicateEntries()
   {
      String propertyName = "list";
      when(propertyAdapter.isMutable(propertyName)).thenReturn(true);

      BeanPropertyListModel<TestBean, String> lm = new BeanPropertyListModel<TestBean, String>(propertyAdapter,
                                                                                               propertyName,
                                                                                               CollectionConverters.LIST_CONVERTER);

      lm.setElements(Arrays.asList("abc", "abc", "def"));
      // make sure our dirty state is based on the above values
      lm.checkpoint();
      lm.setElements(Arrays.asList("abc", "def", "abc"));

      assertTrue(lm.getDirtyModel().getValue());
   }


   @Test
   public void copyImmutableObjectBarfs()
   {
      when(propertyAdapter.isMutable("readOnlyCollection")).thenReturn(false);

      BeanPropertyListModel<TestBean, Object> vm = new BeanPropertyListModel<TestBean, Object>(propertyAdapter,
                                                                                               "readOnlyCollection",
                                                                                               CollectionConverters.COLLECTION_CONVERTER);

      try
      {
         vm.setElements(new ArrayList<Object>());
         fail("Expected IllegalStateException");
      }
      catch (IllegalStateException e)
      {
      }

      try
      {
         vm.add(new Object());
         fail("Expected IllegalStateException");
      }
      catch (IllegalStateException e)
      {
      }

      try
      {
         vm.remove(new Object());
         fail("Expected IllegalStateException");
      }
      catch (IllegalStateException e)
      {
      }
   }

   @Test
   public void normalPropertyIsMutable()
   {
      when(propertyAdapter.isMutable("collection")).thenReturn(true);
      assertTrue(new BeanPropertyListModel<TestBean, Integer>(propertyAdapter,
                                                              "collection",
                                                              CollectionConverters.COLLECTION_CONVERTER).isMutable());
   }

   @Test
   public void readOnlyPropertyIsNotMutable()
   {
      when(propertyAdapter.isMutable("readOnlyCollection")).thenReturn(false);
      assertFalse(new BeanPropertyListModel<TestBean, Integer>(propertyAdapter,
                                                               "readOnlyCollection",
                                                               CollectionConverters.COLLECTION_CONVERTER).isMutable());
   }

}