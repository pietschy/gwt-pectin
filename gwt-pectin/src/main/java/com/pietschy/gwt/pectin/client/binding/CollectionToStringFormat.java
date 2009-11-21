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

package com.pietschy.gwt.pectin.client.binding;

import com.pietschy.gwt.pectin.client.format.ListDisplayFormat;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
* User: andrew
* Date: Aug 17, 2009
* Time: 1:18:27 PM
* To change this template use File | Settings | File Templates.
*/
public class CollectionToStringFormat<T> implements ListDisplayFormat<T>
{
   public static final String DEFAULT_JOIN_STRING = ", ";
   private String joinString;

   public CollectionToStringFormat()
   {
      this(DEFAULT_JOIN_STRING);
   }

   public CollectionToStringFormat(String joinString)
   {
      if (joinString == null)
      {
         throw new NullPointerException("joinString is null");
      }

      this.joinString = joinString;
   }

   public String format(Collection<T> values)
   {
      StringBuilder buf = null;
      for (Object value : values)
      {
         if (buf == null)
         {
            buf = new StringBuilder();
         }
         else
         {
            buf.append(joinString);
         }
         buf.append(value != null ? value.toString() : "");
      }

      return buf != null ? buf.toString() : "";
   }
}