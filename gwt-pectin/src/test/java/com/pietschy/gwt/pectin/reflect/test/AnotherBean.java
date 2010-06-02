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

package com.pietschy.gwt.pectin.reflect.test;

import java.util.List;

/**
 * We extends AbstractTestBean so we ensure we pick up super class properties.
 */
public class AnotherBean
{
   private String string;
   private List<String> stringList;

   public String getString()
   {
      return string;
   }

   public void setString(String string)
   {
      this.string = string;
   }

   public List<String> getStringList()
   {
      return stringList;
   }

   public void setStringList(List<String> stringList)
   {
      this.stringList = stringList;
   }
}