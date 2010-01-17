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


import com.google.gwt.event.logical.shared.ValueChangeHandler;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * ValueHolder Tester.
 *
 * @author  andrew
 * @version $Revision$, $Date$
 * @created August 16, 2009
 * @since   1.0
 */
public class ValueHolderTest
{
   private ValueHolder<String> subject;
   
   @BeforeMethod
   public void setUp()
   {
      subject = new ValueHolder<String>();
   }

   @Test
   public void setValue()
   {
      Assert.assertEquals(subject.getValue(), null);

      String newValue = "blah";
      
      subject.setValue(newValue);
      Assert.assertEquals(subject.getValue(), newValue);
   }

   
   @Test
   @SuppressWarnings("unchecked")
   public void firesChangeEventForDifferentValues()
   {
      ValueChangeHandler<String> changeHandler = mock(ValueChangeHandler.class);

      subject.addValueChangeHandler(changeHandler);
      
      subject.setValue("abc");
      subject.setValue("123");

      verify(changeHandler).onValueChange(argThat(new IsValueChangeEventWithValue<String>("abc")));
      verify(changeHandler).onValueChange(argThat(new IsValueChangeEventWithValue<String>("123")));
   }

   @Test
   @SuppressWarnings("unchecked")
   public void firesChangeEventForDifferentValuesWhenNotAlwaysFiresEvents()
   {
      ValueChangeHandler<String> changeHandler = mock(ValueChangeHandler.class);
      subject.setFireEventsEvenWhenValuesEqual(false);

      subject.addValueChangeHandler(changeHandler);

      subject.setValue("abc");
      subject.setValue("123");

      verify(changeHandler).onValueChange(argThat(new IsValueChangeEventWithValue<String>("abc")));
      verify(changeHandler).onValueChange(argThat(new IsValueChangeEventWithValue<String>("123")));
   }
   
   @Test
   @SuppressWarnings("unchecked")
   public void firesChangeEventForSameValueWhenAlwasyFiresEvents()
   {
      ValueChangeHandler<String> changeHandler = mock(ValueChangeHandler.class);

      subject.addValueChangeHandler(changeHandler);
      
      subject.setValue("abc");
      subject.setValue("abc");

      verify(changeHandler, times(2)).onValueChange(argThat(new IsValueChangeEventWithValue<String>("abc")));
   }

   @Test
   @SuppressWarnings("unchecked")
   public void doesntFireChangeEventForSameValueWhenNotAlwasyFiresEvents()
   {
      ValueChangeHandler<String> changeHandler = mock(ValueChangeHandler.class);
      subject.setFireEventsEvenWhenValuesEqual(false);

      subject.setValue("abc");

      subject.addValueChangeHandler(changeHandler);
      subject.setValue("abc");

      verify(changeHandler, times(0)).onValueChange(argThat(new IsValueChangeEventWithValue<String>("abc")));
   }

}
