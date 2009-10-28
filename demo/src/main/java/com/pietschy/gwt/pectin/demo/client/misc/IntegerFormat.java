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

package com.pietschy.gwt.pectin.demo.client.misc;

import com.pietschy.gwt.pectin.client.format.Format;
import com.pietschy.gwt.pectin.client.format.FormatException;

/**
 * Created by IntelliJ IDEA.
 * User: andrew
 * Date: Aug 3, 2009
 * Time: 4:33:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class IntegerFormat implements Format<Integer>
{
   public String format(Integer value)
   {
      return value != null ? Integer.toString(value) : null;
   }

   public Integer parse(String text) throws FormatException
   {
      try
      {
         if (text != null && text.trim().length() > 0)
         {
            int integer = Integer.parseInt(text.trim());

            validateInteger(integer);

            return integer;
         }
         else
         {
            return null;
         }
      }
      catch (NumberFormatException e)
      {
         throw new FormatException("Not a valid number", e);
      }
   }

   protected void validateInteger(int age) throws FormatException
   {
      // subclasses can hook in here
   }
}