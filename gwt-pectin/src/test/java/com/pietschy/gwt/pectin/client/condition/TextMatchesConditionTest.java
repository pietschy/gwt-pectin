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

package com.pietschy.gwt.pectin.client.condition;


import org.testng.annotations.*;
import static org.testng.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.mockito.Mockito;
import org.mockito.ArgumentMatcher;
import static org.mockito.Matchers.argThat;
import com.pietschy.gwt.pectin.client.value.ValueHolder;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;

/**
 * TextMatchesCondition Tester.
 *
 * @author  andrew
 * @version $Revision$, $Date$
 * @created August 15, 2009
 * @since   1.0
 */
public class TextMatchesConditionTest
{
   private ValueHolder<String> source;
   private TextMatchesCondition condition;

   @BeforeMethod
   public void setUp()
   {
      source = new ValueHolder<String>();
      condition = new TextMatchesCondition(source, "^abc$");
   }

   @Test(dataProvider = "testData")
   public void computeValue(String value, Boolean matches)
   {
      assertEquals(condition.computeValue(value), matches);
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
}
