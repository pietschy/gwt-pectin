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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;


/**
 * BeanModelProvider Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>09/23/2009</pre>
 */
public class BeanPropertyValueModelTest
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
   public void readAPrimitiveInt()
   {
      BeanPropertyValueModel<TestBean, Integer> vm = new BeanPropertyValueModel<TestBean, Integer>(propertyAdapter, "primitiveInt");
      assertNull(vm.getValue());
      when(propertyAdapter.readProperty(bean, "primitiveInt")).thenReturn(5);
      vm.readFrom(bean);
      verify(propertyAdapter, times(1)).readProperty(isA(TestBean.class), eq("primitiveInt"));
      assertEquals(vm.getValue(), new Integer(5));
      assertFalse(vm.getDirtyModel().getValue());
   }

   @Test
   public void valueChangesAreReflectedByDirtyModel()
   {

      String propertyName = "primitiveInt";
      when(propertyAdapter.isMutable(propertyName)).thenReturn(true);

      BeanPropertyValueModel<TestBean, Integer> vm = new BeanPropertyValueModel<TestBean, Integer>(propertyAdapter, propertyName);
      assertNull(vm.getValue());
      when(propertyAdapter.readProperty(bean, propertyName)).thenReturn(5);
      vm.readFrom(bean);
      verify(propertyAdapter, times(1)).readProperty(isA(TestBean.class), eq(propertyName));
      assertEquals(vm.getValue(), new Integer(5));
      assertFalse(vm.getDirtyModel().getValue());

      vm.setValue(6);
      assertEquals(vm.getValue(), new Integer(6));
      assertTrue(vm.getDirtyModel().getValue());

      // dirty should recover
      vm.setValue(5);
      assertEquals(vm.getValue(), new Integer(5));
      assertFalse(vm.getDirtyModel().getValue());
   }



   @Test
   public void resetDirtyModel()
   {

      String propertyName = "primitiveInt";
      when(propertyAdapter.isMutable(propertyName)).thenReturn(true);
      BeanPropertyValueModel<TestBean, Integer> vm = new BeanPropertyValueModel<TestBean, Integer>(propertyAdapter, propertyName);
      when(propertyAdapter.readProperty(bean, propertyName)).thenReturn(5);
      vm.readFrom(bean);

      vm.setValue(6);
      assertEquals(vm.getValue(), new Integer(6));
      assertTrue(vm.getDirtyModel().getValue());

      // dirty should recover
      vm.checkpoint();
      assertEquals(vm.getValue(), new Integer(6));
      assertFalse(vm.getDirtyModel().getValue());
   }

   @Test
   public void computeDirty()
   {
      BeanPropertyValueModel<TestBean, Object> vm = new BeanPropertyValueModel<TestBean, Object>(propertyAdapter, "primitiveInt");
      when(propertyAdapter.readProperty(bean, "primitiveInt")).thenReturn(5);
      vm.readFrom(bean);

      assertFalse(vm.computeDirty());
   }

   @Test
   public void copyPrimitiveInt()
   {
      String propertyName = "primitiveInt";
      when(propertyAdapter.isMutable(propertyName)).thenReturn(true);
      BeanPropertyValueModel<TestBean, Integer> vm = new BeanPropertyValueModel<TestBean, Integer>(propertyAdapter, propertyName);

      when(propertyAdapter.readProperty(bean, propertyName)).thenReturn(5);
      vm.readFrom(bean);
      assertEquals(vm.getValue(), new Integer(5));
      assertFalse(vm.getDirtyModel().getValue());

      vm.setValue(8);
      assertEquals(vm.getValue(), new Integer(8));
      assertTrue(vm.getDirtyModel().getValue());

      vm.copyTo(bean, false);
      verify(propertyAdapter, times(1)).writeProperty(isA(TestBean.class), eq(propertyName), eq(8));
      assertTrue(vm.getDirtyModel().getValue());
   }

   @Test
   public void copyPrimitiveIntAndResetDirty()
   {
      String propertyName = "primitiveInt";
      when(propertyAdapter.isMutable(propertyName)).thenReturn(true);
      BeanPropertyValueModel<TestBean, Integer> vm = new BeanPropertyValueModel<TestBean, Integer>(propertyAdapter, propertyName);
      assertNull(vm.getValue());

      when(propertyAdapter.readProperty(bean, propertyName)).thenReturn(5);
      vm.readFrom(bean);
      assertEquals(vm.getValue(), new Integer(5));
      assertFalse(vm.getDirtyModel().getValue());

      vm.setValue(8);
      assertEquals(vm.getValue(), new Integer(8));
      assertTrue(vm.getDirtyModel().getValue());

      vm.copyTo(bean, true);
      verify(propertyAdapter, times(1)).writeProperty(isA(TestBean.class), eq(propertyName), eq(8));
      assertFalse(vm.getDirtyModel().getValue());
   }


   @Test(expectedExceptions = IllegalStateException.class)
   public void copyImmutableObjectBarfs()
   {
      when(propertyAdapter.isMutable("readOnlyObject")).thenReturn(false);

      BeanPropertyValueModel<TestBean, Object> vm = new BeanPropertyValueModel<TestBean, Object>(propertyAdapter, "readOnlyObject");

      vm.setValue(new Object());
   }

   @Test
   public void normalPropertyIsMutable()
   {
      when(propertyAdapter.isMutable("primitiveInt")).thenReturn(true);
      assertTrue(new BeanPropertyValueModel<TestBean, Integer>(propertyAdapter, "primitiveInt").isMutable());
   }

   @Test
   public void readOnlyPropertyIsNotMutable()
   {
      when(propertyAdapter.isMutable("readOnlyObject")).thenReturn(false);
      assertFalse(new BeanPropertyValueModel<TestBean, Integer>(propertyAdapter, "primitiveInt").isMutable());
   }

//   public void testSetValue()
//   {
//      BeanPropertyValueModel<TestBean, String> vm = propertyAdapter.getValueModel("string", String.class);
//
//      propertyAdapter.setBean(bean);
//
//      String value = "abc";
//      assertNull(bean.getString());
//      assertFalse(vm.getDirtyModel().getValue());
//
//      vm.setValue(value);
//      assertTrue(vm.getDirtyModel().getValue());
//      assertNull(bean.getString());
//      propertyAdapter.commit();
//      assertEquals(bean.getString(), value);
//      assertFalse(vm.getDirtyModel().getValue());
//
//   }
//
//   public void testPrimitiveIntGetValue()
//   {
//      BeanPropertyValueModel<TestBean, Integer> vm = propertyAdapter.getValueModel("primitiveInt", Integer.class);
//      assertNull(vm.getValue());
//
//      bean.setPrimitiveInt(5);
//      propertyAdapter.setBean(bean);
//      assertFalse(vm.getDirtyModel().getValue());
//      assertEquals(vm.getValue(), new Integer(5));
//   }
//
//   public void testPrimitiveInSetValue()
//   {
//      BeanPropertyValueModel<TestBean, Integer> vm = propertyAdapter.getValueModel("primitiveInt", Integer.class);
//
//      bean.setPrimitiveInt(5);
//      propertyAdapter.setBean(bean);
//
//
//      assertEquals(vm.getValue(), new Integer(5));
//      assertFalse(vm.getDirtyModel().getValue());
//
//      vm.setValue(6);
//      assertTrue(vm.getDirtyModel().getValue());
//      propertyAdapter.commit();
//      assertEquals(bean.getPrimitiveInt(), 6);
//      assertFalse(vm.getDirtyModel().getValue());
//
//   }

   
}