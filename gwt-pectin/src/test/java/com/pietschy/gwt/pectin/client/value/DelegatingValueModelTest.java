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


import org.testng.annotations.*;
import org.testng.Assert;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

/**
 * DelegatingValueModel Tester.
 *
 * @author  andrew
 * @version $Revision$, $Date$
 * @created August 16, 2009
 * @since   1.0
 */
public class DelegatingValueModelTest
{
   private DelegatingValueModel<String> subject;

   @BeforeMethod
   public void setUp()
   {
      subject = new DelegatingValueModel<String>();
   }

   @Test
   @SuppressWarnings("unchecked")
   public void setDelegate()
   {
      ValueHolder<String> delegateOne = new ValueHolder<String>();
      ValueHolder<String> delegateTwo = new ValueHolder<String>();
      ValueChangeHandler<String> mock = mock(ValueChangeHandler.class);
      
      subject.addValueChangeHandler(mock);
      
      delegateOne.setValue("abc");
      delegateTwo.setValue("123");
      
      subject.setDelegate(delegateOne);
      subject.setDelegate(delegateTwo);
      
      delegateOne.setValue("This shouldn't be seen");
      
      verify(mock).onValueChange(argThat(new IsValueChangeEventWithValue<String>("abc")));
      verify(mock).onValueChange(argThat(new IsValueChangeEventWithValue<String>("123")));
      verify(mock, never()).onValueChange(argThat(new IsValueChangeEventWithValue<String>("This shouldn't be seen")));
   }

    @Test
   @SuppressWarnings("unchecked")
   public void sourceChangeFiresValueChange()
   {
      ValueHolder<String> delegate = new ValueHolder<String>();
      ValueChangeHandler<String> changeHandler = mock(ValueChangeHandler.class);

      subject.addValueChangeHandler(changeHandler);
      subject.setDelegate(delegate);
      delegate.setValue("");
      delegate.setValue("abc");

      verify(changeHandler).onValueChange(argThat(new IsValueChangeEventWithValue<String>(null)));
      verify(changeHandler).onValueChange(argThat(new IsValueChangeEventWithValue<String>("")));
      verify(changeHandler).onValueChange(argThat(new IsValueChangeEventWithValue<String>("abc")));
   }
   
   @Test
   public void getValueWithDelegate()
   {
      ValueHolder<String> delegate = new ValueHolder<String>("abc");
      
      subject.setDelegate(delegate);
      
      assertEquals(subject.getValue(), "abc");
   }

   @Test
   public void getValueNoDelegate()
   {
      assertEquals(subject.getValue(), null);
   }
   
   @Test
   public void getValueNoDelegateWithDefaultValue()
   {
      DelegatingValueModel<String> subject = new DelegatingValueModel<String>("abc");
      assertEquals(subject.getValue(), "abc");
      subject.setDelegate(new ValueHolder<String>("123"));
      assertEquals(subject.getValue(), "123");
   }
}
