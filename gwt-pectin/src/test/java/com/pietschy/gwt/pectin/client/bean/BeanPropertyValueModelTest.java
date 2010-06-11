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


import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import com.pietschy.gwt.pectin.reflect.test.TestBean;
import org.mockito.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.junit.Assert.assertNull;
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
   private ValueHolder<TestBean> source;
   private BeanPropertyAccessor accessor;
   private BeanPropertyValueModel<String> model;
   private TestBean sourceBean;
   private static final String PROPERTY_NAME = "string";
   private ValueHolder<Boolean> autoCommit;

   @BeforeMethod
   protected void setUp() throws Exception
   {
      source = new ValueHolder<TestBean>();
      accessor = mock(BeanPropertyAccessor.class);
      autoCommit = new ValueHolder<Boolean>(false);
      model = new BeanPropertyValueModel<String>(source,
                                                 new PropertyKey<String>(String.class, PROPERTY_NAME),
                                                 accessor,
                                                 autoCommit);
      sourceBean = new TestBean();
      source.setValue(sourceBean);
   }


   @Test
   public void immutableWhenSourceIsNull()
   {
      // the property is mutable
      when(accessor.isMutable(PROPERTY_NAME)).thenReturn(true);
      // but the source is null
      source.setValue(null);
      assertFalse(model.isMutable());
      // once the source is non-null we should be mutable again.
      source.setValue(new TestBean());
      assertTrue(model.isMutable());
   }

   @Test
   public void immutableWhenPropertyIsReadOnly()
   {
      // the property is mutable
      when(accessor.isMutable(PROPERTY_NAME)).thenReturn(false);
      // but the source is null
      source.setValue(null);
      assertFalse(model.isMutable());
      // always immutable, even when source isn't null
      source.setValue(new TestBean());
      assertFalse(model.isMutable());
   }

   @Test(expectedExceptions = ReadOnlyPropertyException.class)
   public void writeToSourceWithImmutablePropertyBarfs()
   {
      when(accessor.isMutable(PROPERTY_NAME)).thenReturn(false);
      model.writeToSource(true);
   }

   @Test(expectedExceptions = SourceBeanIsNullException.class)
   public void writeToSourceWithNullSourceBarfs()
   {
      when(accessor.isMutable(PROPERTY_NAME)).thenReturn(true);
      source.setValue(null);
      model.writeToSource(true);
   }


   @Test(expectedExceptions = ReadOnlyPropertyException.class)
   public void setValueWithImmutablePropertySourceBarfs()
   {
      when(accessor.isMutable(PROPERTY_NAME)).thenReturn(false);

      model.setValue("thisShouldBarf");
   }

   @Test(expectedExceptions = SourceBeanIsNullException.class)
   public void setValueWithNullSourceBarfs()
   {
      when(accessor.isMutable(PROPERTY_NAME)).thenReturn(true);
      source.setValue(null);
      model.setValue("thisShouldBarf");
   }

   @Test
   public void valueChangesAreReflectedByDirtyModel()
   {
      assertNull(model.getValue());
      when(accessor.readProperty(sourceBean, PROPERTY_NAME)).thenReturn("abc");
      when(accessor.isMutable(PROPERTY_NAME)).thenReturn(true);
      model.readFromSource();

      assertFalse(model.getDirtyModel().getValue());

      model.setValue("def");
      assertTrue(model.getDirtyModel().getValue());

      // dirty should recover
      model.setValue("abc");
      assertFalse(model.getDirtyModel().getValue());
   }

   @Test
   public void readFromSource()
   {
      when(accessor.isMutable(PROPERTY_NAME)).thenReturn(true);
      when(accessor.readProperty(sourceBean, PROPERTY_NAME)).thenReturn("abc", "xyz");
      model.readFromSource();

      assertEquals(model.getValue(), "abc");
      assertFalse(model.getDirtyModel().getValue());

      model.setValue("def");
      assertEquals(model.getValue(), "def");
      assertTrue(model.getDirtyModel().getValue());

      // we should get the second mock value and dirty should recover
      model.readFromSource();
      assertEquals(model.getValue(), "xyz");
      assertFalse(model.getDirtyModel().getValue());

      verify(accessor, never()).writeProperty(any(), any(String.class), any());
   }

   @Test
   public void checkpoint()
   {
      when(accessor.isMutable(PROPERTY_NAME)).thenReturn(true);
      when(accessor.readProperty(sourceBean, PROPERTY_NAME)).thenReturn("abc");
      model.readFromSource();

      assertEquals(model.getValue(), "abc");
      assertFalse(model.getDirtyModel().getValue());

      model.setValue("def");
      assertEquals(model.getValue(), "def");
      assertTrue(model.getDirtyModel().getValue());

      // dirty should recover
      model.checkpoint();
      assertEquals(model.getValue(), "def");
      assertFalse(model.getDirtyModel().getValue());
   }

   @Test
   public void revert()
   {
      when(accessor.isMutable(PROPERTY_NAME)).thenReturn(true);
      when(accessor.readProperty(sourceBean, PROPERTY_NAME)).thenReturn("abc");
      model.readFromSource();

      assertEquals(model.getValue(), "abc");
      assertFalse(model.getDirtyModel().getValue());

      model.setValue("def");
      assertEquals(model.getValue(), "def");
      assertTrue(model.getDirtyModel().getValue());

      // dirty should recover
      model.revert();
      assertEquals(model.getValue(), "abc");
      assertFalse(model.getDirtyModel().getValue());
   }

   @Test
   public void writeValueWithAndWithoutOutCheckpoint()
   {
      when(accessor.isMutable(PROPERTY_NAME)).thenReturn(true);
      when(accessor.readProperty(sourceBean, PROPERTY_NAME)).thenReturn("abc");
      model.readFromSource();

      model.setValue("def");
      assertTrue(model.getDirtyModel().getValue());


      model.writeToSource(false);
      // still dirty
      assertTrue(model.getDirtyModel().getValue());
      // and value was written out to the bean...
      verify(accessor).writeProperty(isA(TestBean.class), eq(PROPERTY_NAME), eq("def"));

      model.setValue("hij");
      model.writeToSource(true);
      // still dirty
      assertFalse(model.getDirtyModel().getValue());
      // and value was written out to the bean...
      verify(accessor).writeProperty(isA(TestBean.class), eq(PROPERTY_NAME), eq("hij"));
   }


   @Test
   public void autoCommit()
   {
      autoCommit.setValue(true);

      when(accessor.isMutable(PROPERTY_NAME)).thenReturn(true);
      when(accessor.readProperty(sourceBean, PROPERTY_NAME)).thenReturn("abc");
      model.readFromSource();

      ValueChangeHandler<Boolean> dirtyHandler = mock(ValueChangeHandler.class);

      model.getDirtyModel().addValueChangeHandler(dirtyHandler);

      assertEquals(model.getValue(), "abc");

      model.setValue("def");

      verify(dirtyHandler, never()).onValueChange(isA(ValueChangeEvent.class));
      verify(accessor).writeProperty(isA(TestBean.class), eq(PROPERTY_NAME), eq("def"));
   }

   @Test
   public void readFromSourceWithAutoCommit()
   {
      autoCommit.setValue(true);

      when(accessor.isMutable(PROPERTY_NAME)).thenReturn(true);
      when(accessor.readProperty(sourceBean, PROPERTY_NAME)).thenReturn("abc");

      model.readFromSource();
      verify(accessor, never()).writeProperty(isA(TestBean.class), isA(String.class), Matchers.<Object>any());
   }

   @Test
   public void checkpointWithAutoCommit()
   {
      autoCommit.setValue(true);

      when(accessor.isMutable(PROPERTY_NAME)).thenReturn(true);
      when(accessor.readProperty(sourceBean, PROPERTY_NAME)).thenReturn("abc");
      model.readFromSource();
      verify(accessor, never()).writeProperty(isA(TestBean.class), isA(String.class), Matchers.<Object>any());

      // the checkpoint should remember this value.
      model.checkpoint();

      // this should write the new value to the accessor
      model.setValue("def");
      verify(accessor, times(1)).writeProperty(isA(TestBean.class), isA(String.class), eq("def"));

      //  should write the original value back to the bean.
      model.revert();
      verify(accessor, times(1)).writeProperty(isA(TestBean.class), isA(String.class), eq("abc"));
   }
}