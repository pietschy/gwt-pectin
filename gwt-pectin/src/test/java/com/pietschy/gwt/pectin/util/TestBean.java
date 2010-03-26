/*
 * Copyright 2010 Andrew Pietsch
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

package com.pietschy.gwt.pectin.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

/**
 *
 */
public class TestBean extends AbstractTestBean
{
   private Integer objectInteger;

   private Boolean objectBoolean;

   private Collection<String> collection;
   private List<String> list;
   private SortedSet<String> sortedSet;

   private Collection untypedCollection;

   private Object readOnlyObject = "read only object";
   private int readOnlyPrimitive = 42;
   private Collection readOnlyUntypedCollection = Arrays.asList("abc", "def", "ghi");;


   public Integer getObjectInteger()
   {
      return objectInteger;
   }

   public void setObjectInteger(Integer objectInteger)
   {
      this.objectInteger = objectInteger;
   }

   public Boolean getObjectBoolean()
   {
      return objectBoolean;
   }

   public void setObjectBoolean(Boolean objectBoolean)
   {
      this.objectBoolean = objectBoolean;
   }

   public Collection<String> getCollection()
   {
      return collection;
   }

   public void setCollection(Collection<String> collection)
   {
      this.collection = collection;
   }

   public List<String> getList()
   {
      return list;
   }

   public void setList(List<String> list)
   {
      this.list = list;
   }

   public SortedSet<String> getSortedSet()
   {
      return sortedSet;
   }

   public void setSortedSet(SortedSet<String> sortedSet)
   {
      this.sortedSet = sortedSet;
   }

   public Collection getUntypedCollection()
   {
      return untypedCollection;
   }

   public void setUntypedCollection(Collection untypedCollection)
   {
      this.untypedCollection = untypedCollection;
   }

   public Object getReadOnlyObject()
   {
      return readOnlyObject;
   }

   public int getReadOnlyPrimitive()
   {
      return readOnlyPrimitive;
   }

   public Collection getReadOnlyUntypedCollection()
   {
      return readOnlyUntypedCollection;
   }
}