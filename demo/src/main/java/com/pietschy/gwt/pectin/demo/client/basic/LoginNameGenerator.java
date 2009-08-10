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

package com.pietschy.gwt.pectin.demo.client.basic;

import com.pietschy.gwt.pectin.client.value.Function;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
* User: andrew
* Date: Jul 13, 2009
* Time: 5:35:31 PM
* To change this template use File | Settings | File Templates.
*/
class LoginNameGenerator implements Function<String, String>
{
   public String compute(List<String> source)
   {
      String login = "";
      for (String value : source)
      {
         if (value != null && value.length() > 0)
         {
            if (login.length() > 0 && !login.endsWith("_"))
            {
               login += "_";
            }
            login += value.trim().replaceAll("\\s+", "_").toLowerCase();
         }
      }

      return login;
   }
}
