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

package com.pietschy.gwt.pectin.client.bean.test;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

/**
 * 
 */
public class TestBean
{
   private String string;
   private int primativeInt;
   private Integer objectInteger;
   
   private boolean primativeBoolean;
   private Boolean objectBoolean;
   
   private Collection collection;
   private Set set;
   private List list;
   private SortedSet sortedSet;

   public String getString()
   {
      return string;
   }

   public void setString(String string)
   {
      this.string = string;
   }


   public int getPrimativeInt()
   {
      return primativeInt;
   }

   public void setPrimativeInt(int primativeInt)
   {
      this.primativeInt = primativeInt;
   }

   public Integer getObjectInteger()
   {
      return objectInteger;
   }

   public void setObjectInteger(Integer objectInteger)
   {
      this.objectInteger = objectInteger;
   }

   public boolean isPrimativeBoolean()
   {
      return primativeBoolean;
   }

   public void setPrimativeBoolean(boolean primativeBoolean)
   {
      this.primativeBoolean = primativeBoolean;
   }

   public Boolean getObjectBoolean()
   {
      return objectBoolean;
   }

   public void setObjectBoolean(Boolean objectBoolean)
   {
      this.objectBoolean = objectBoolean;
   }

   public Collection getCollection()
   {
      return collection;
   }

   public void setCollection(Collection collection)
   {
      this.collection = collection;
   }

   public Set getSet()
   {
      return set;
   }

   public void setSet(Set set)
   {
      this.set = set;
   }

   public List getList()
   {
      return list;
   }

   public void setList(List list)
   {
      this.list = list;
   }

   public SortedSet getSortedSet()
   {
      return sortedSet;
   }

   public void setSortedSet(SortedSet sortedSet)
   {
      this.sortedSet = sortedSet;
   }
}
