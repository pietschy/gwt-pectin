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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
      BeanPropertyListModel<TestBean, String> lm = new BeanPropertyListModel<TestBean, String>(propertyAdapter,
                                                                                               "set",
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
      BeanPropertyListModel<TestBean, String> lm = new BeanPropertyListModel<TestBean, String>(propertyAdapter,
                                                                                               "set",
                                                                                               CollectionConverters.SET_CONVERTER);
      assertEquals(lm.size(), 0);

      final List<String> values = Arrays.asList("abc", "def");
      lm.setElements(values);

      TestBean bean = new TestBean();

      lm.copyTo(bean, true);

      assertFalse(lm.getDirtyModel().getValue());

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
   public void resetDirty()
   {
      BeanPropertyListModel<TestBean, String> lm = new BeanPropertyListModel<TestBean, String>(propertyAdapter,
                                                                                               "set",
                                                                                               CollectionConverters.SET_CONVERTER);


      when(propertyAdapter.readProperty(bean, "set"))
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

//
//   public void testCollectionSetValue()
//   {
//      BeanPropertyListModel<TestBean, String> lm = propertyAdapter.getListModel("set", String.class);
//
//      propertyAdapter.setBean(bean);
//      assertFalse(lm.getDirtyModel().getValue());
//
//      lm.add("abc");
//      assertNull(bean.getSet());
//      assertTrue(lm.getDirtyModel().getValue());
//
//      propertyAdapter.commit();
//      assertContentEquals(bean.getSet(), "abc");
//      assertFalse(lm.getDirtyModel().getValue());
//
//      lm.add("def");
//      assertContentEquals(bean.getSet(), "abc");
//      assertTrue(lm.getDirtyModel().getValue());
//      propertyAdapter.commit();
//      assertContentEquals(bean.getSet(), "abc", "def");
//      assertFalse(lm.getDirtyModel().getValue());
//
//      lm.remove("abc");
//      assertContentEquals(bean.getSet(), "abc", "def");
//      assertTrue(lm.getDirtyModel().getValue());
//      propertyAdapter.commit();
//      assertContentEquals(bean.getSet(), "def");
//      assertFalse(lm.getDirtyModel().getValue());
//
//      lm.setElements(Arrays.asList("abc", "xyz"));
//      assertContentEquals(bean.getSet(), "def");
//      assertTrue(lm.getDirtyModel().getValue());
//      propertyAdapter.commit();
//      assertContentEquals(bean.getSet(), "abc", "xyz");
//      assertFalse(lm.getDirtyModel().getValue());
//
//   }
//
////   public void testSetCollectionValueWithAutoCommit()
////   {
////      provider.setAutoCommit(true);
////
////      BeanPropertyListModel<TestBean, String> lm = provider.getListModel("set", String.class);
////
////      provider.setBean(bean);
////
////      assertNull("bean value is null", bean.getSet());
////      lm.add("abc");
////      assertContentEquals(bean.getSet(), "abc");
////      assertFalse("not dirty after add", lm.getDirtyModel().getValue());
////
////      lm.add("def");
////      assertContentEquals(bean.getSet(), "abc", "def");
////      assertFalse("not dirty after second add", lm.getDirtyModel().getValue());
////
////      lm.remove("abc");
////      assertContentEquals(bean.getSet(), "def");
////      assertFalse("not dirty after remove", lm.getDirtyModel().getValue());
////
////      lm.setElements(Arrays.asList("abc", "xyz"));
////      assertContentEquals(bean.getSet(), "abc", "xyz");
////      assertFalse("not dirty after setElements", lm.getDirtyModel().getValue());
////   }
//

   @Test
   public void dirtyIgnoresCollectionOrder()
   {
      BeanPropertyListModel<TestBean, String> lm = new BeanPropertyListModel<TestBean, String>(propertyAdapter,
                                                                                               "list",
                                                                                               CollectionConverters.LIST_CONVERTER);

      lm.setElements(Arrays.asList("def", "abc", "qbt"));
      // make sure our dirty state is based on the above values
      lm.checkpoint();
      lm.setElements(Arrays.asList("qbt", "def", "abc"));
      
      assertFalse(lm.getDirtyModel().getValue());
   }


}