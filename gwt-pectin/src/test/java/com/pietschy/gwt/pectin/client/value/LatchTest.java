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
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * ValueHolder Tester.
 *
 * @author  andrew
 * @version $Revision$, $Date$
 * @created August 16, 2009
 * @since   1.0
 */
public class LatchTest
{
   private ValueHolder<Boolean> source;
   private Latch latch;
   private ValueChangeHandler<Boolean> changeHandler;

   @BeforeMethod
   public void setUp()
   {
      changeHandler = mock(ValueChangeHandler.class);
   }

   @Test
   public void latchOnTrue()
   {
      source = new ValueHolder<Boolean>(null);
      latch = Latch.onTrue(source);
      latch.addValueChangeHandler(changeHandler);

      Assert.assertEquals(latch.getValue(), Boolean.FALSE);

      source.setValue(false);

      Assert.assertEquals(latch.getValue(), Boolean.FALSE);

      source.setValue(null);

      Assert.assertEquals(latch.getValue(), Boolean.FALSE);

      source.setValue(true);

      Assert.assertEquals(latch.getValue(), Boolean.TRUE);
      verify(changeHandler).onValueChange(argThat(new IsValueChangeEventWithValue<Boolean>(true)));

      source.setValue(false);

      Assert.assertEquals(latch.getValue(), Boolean.TRUE);

      latch.reset();

      Assert.assertEquals(latch.getValue(), Boolean.FALSE);

      verify(changeHandler).onValueChange(argThat(new IsValueChangeEventWithValue<Boolean>(false)));

   }

   @Test
   public void latchOnFalse()
   {
      source = new ValueHolder<Boolean>(null);
      latch = Latch.onFalse(source);
      latch.addValueChangeHandler(changeHandler);

      Assert.assertEquals(latch.getValue(), Boolean.TRUE);

      source.setValue(true);

      Assert.assertEquals(latch.getValue(), Boolean.TRUE);

      source.setValue(null);

      Assert.assertEquals(latch.getValue(), Boolean.TRUE);

      source.setValue(false);

      Assert.assertEquals(latch.getValue(), Boolean.FALSE);
      verify(changeHandler).onValueChange(argThat(new IsValueChangeEventWithValue<Boolean>(false)));

      source.setValue(true);

      Assert.assertEquals(latch.getValue(), Boolean.FALSE);

      latch.reset();

      Assert.assertEquals(latch.getValue(), Boolean.TRUE);
      verify(changeHandler).onValueChange(argThat(new IsValueChangeEventWithValue<Boolean>(true)));
   }



}