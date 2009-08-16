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
import org.mockito.ArgumentMatcher;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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

      source.setValue("");
      source.setValue("abc");

      verify(changeHandler).onValueChange(argThat(new IsValueChangeEventWithValue<Boolean>(false)));
      verify(changeHandler).onValueChange(argThat(new IsValueChangeEventWithValue<Boolean>(true)));
   }
}
