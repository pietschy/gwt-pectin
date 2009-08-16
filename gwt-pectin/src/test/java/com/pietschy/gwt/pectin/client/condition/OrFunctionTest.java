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
import org.testng.Assert;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

/**
 * OrFunction Tester.
 *
 * @author  andrew
 * @version $Revision$, $Date$
 * @created August 16, 2009
 * @since   1.0
 */
public class OrFunctionTest
{
   @BeforeMethod
   public void setUp()
   {
   }

   @Test(dataProvider = "computeData")
   public void compute(List<Boolean> values, Boolean result)
   {
      OrFunction function = new OrFunction();
      
      assertEquals(function.compute(values), result);
   }
   
   @DataProvider
   public Object[][] computeData()
   {
      return new Object[][]
         {
            {Arrays.asList((Boolean)null), false},
            {Arrays.asList(false), false},
            {Arrays.asList(true), true},
            {Arrays.asList((Boolean)null, false), false},
            {Arrays.asList((Boolean)null, true), true},
            {Arrays.asList(false, true), true},
            {Arrays.asList(true, true), true},
         };
   }

}
