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

import com.pietschy.gwt.pectin.client.function.Reduce;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Jul 20, 2009
 * Time: 11:49:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class AndFunction
implements Reduce<Boolean, Boolean>
{
   public Boolean compute(List<? extends Boolean> source)
   {
      for (Boolean value : source)
      {
         if (value == null || !value)
         {
            return false;
         }
      }
      
      return true;
   }
}
