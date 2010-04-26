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

package com.pietschy.gwt.pectin.client.value;


import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

/**
 * AbstractComputedValueModel Tester.
 *
 * @author andrew
 * @version $Revision$, $Date$
 * @created August 15, 2009
 * @since 1.0
 */
public class AbstractComputedValueModelTest
{
   private ValueHolder<String> source;
   private AbstractComputedValueModel<Boolean, String> subject;

   @BeforeMethod
   public void setUp()
   {
      source = new ValueHolder<String>();
      subject = new AbstractComputedValueModel<Boolean, String>(source)
      {
         protected Boolean computeValue(String value)
         {
            return "abc".equals(value);
         }
      };
   }


   @Test(dataProvider = "testData")
   public void getValue(String value, Boolean matches)
   {
      source.setValue(value);
      assertEquals(subject.getValue(), matches);
   }

   @DataProvider
   public Object[][] testData()
   {
      return new Object[][]
         {
            {null, false},
            {"", false},
            {"abc", true},
            {" abc", false},
         };
   }

   @Test
   @SuppressWarnings("unchecked")
   public void sourceChangeFiresValueChange()
   {
      ValueChangeHandler<Boolean> changeHandler = mock(ValueChangeHandler.class);
      subject.addValueChangeHandler(changeHandler);

      assertFalse(subject.getValue());

      // changing to another source value that still computes to 'false' should not fire a event
      source.setValue("a");
      verify(changeHandler, never()).onValueChange(isA(ValueChangeEvent.class));
      assertFalse(subject.getValue());

      // this should fire..
      source.setValue("abc");
      assertTrue(subject.getValue());

      verify(changeHandler, times(1)).onValueChange(argThat(new IsValueChangeEventWithValue<Boolean>(true)));

      // this should fire back to false only once...
      source.setValue("");
      assertFalse(subject.getValue());
      source.setValue("");
      assertFalse(subject.getValue());
      verify(changeHandler, times(1)).onValueChange(argThat(new IsValueChangeEventWithValue<Boolean>(false)));
   }

   @Test
   @SuppressWarnings("unchecked")
   public void sourceChangeFiresValueChangeBeforeCacheInitialisation()
   {
      ValueChangeHandler<Boolean> changeHandler = mock(ValueChangeHandler.class);
      subject.addValueChangeHandler(changeHandler);

      // the source has never been set, so we should always fire an event.
      source.setValue("a");
      verify(changeHandler).onValueChange(isA(ValueChangeEvent.class));
      assertFalse(subject.getValue());
   }

   @Test
   @SuppressWarnings("unchecked")
   public void firstGetValueDoesNotFireEvent()
   {
      // This ensures that someone calling getValue the first time doesn't
      // fire an event.  This is important if the getter is also listening for events since
      // the getValue call would cause a re-entrant event.
      ValueHolder<String> source = new ValueHolder<String>("abc");
      AbstractComputedValueModel<Boolean, String> subject = new AbstractComputedValueModel<Boolean, String>(source)
      {
         protected Boolean computeValue(String value)
         {
            return "abc".equals(value);
         }
      };
      ValueChangeHandler<Boolean> mock = mock(ValueChangeHandler.class);
      subject.addValueChangeHandler(mock);

      // the source has never been set, so we should always fire an event.
      assertTrue(subject.getValue());
      verify(mock, never()).onValueChange(isA(ValueChangeEvent.class));
   }
}
